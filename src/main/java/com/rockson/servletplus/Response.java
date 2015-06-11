package com.rockson.servletplus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Response extends HttpServletResponseWrapper {
	protected ServletContext servletContext;
	protected Request req;
	public Response(HttpServletResponse response,Request req,ServletContext servletContext) {
		super(response);
		this.req = req;
		this.servletContext = servletContext;
	}

	public void json(Object object) throws IOException {
		getWriter().write(JSON.toJSONString(object, SerializerFeature.WriteMapNullValue , SerializerFeature.BrowserCompatible));
	}

	protected void copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
		byte[] buff = new byte[bufferSize <= 0 ? 4 * 1024 : bufferSize];
		int len;
		while (-1 != (len = in.read(buff))) {
			out.write(buff, 0, len);
		}
	}
	
	public void render(String jsp) throws IOException, ServletException {
		req.getRequestDispatcher(jsp).forward(req, this);
	}

	public void send(String str) throws IOException {
		if (null == str) {
			return;
		}
		getWriter().write(str);
	}
	protected String makeEtag(File file) {
		return "W/\""+file.lastModified()+"\"";
	}

	public void sendFile(String str) throws IOException {
		sendFile(str, false);
	}
	public void sendFile(String str,boolean useCache) throws IOException {
		String path = servletContext.getRealPath(str);
		File file = new File(path);
		if(useCache) {
			String etag = req.getHeader("If-None-Match");
			if(null!= etag && makeEtag(file).equals(etag)) {
				setStatus(304);
				return;
			}
			addHeader("ETag", makeEtag(file));
			String modifiedSince = req.getHeader("If-Modified-Since");
			try {
				if(null!=modifiedSince && Math.floor(TimeUtils.parseGMT(modifiedSince).getTime()/1000)>=Math.floor(file.lastModified()/1000)){
					setStatus(304);
					return;
				}
			} catch (ParseException e) {
				//go on
			}
			addHeader("Last-Modified", TimeUtils.formatGMT(new Date(file.lastModified())));
		}
		addHeader("Content-Length", "" + file.length());
		addHeader("Content-Type", Files.probeContentType(file.toPath()));
		InputStream inputStream= new FileInputStream(file);
		sendFile(inputStream);
		inputStream.close();
	}

	public void sendFile(InputStream in) throws IOException {
		copy(in, getOutputStream(), 0);
	}

	public void sendFile(Path path) throws IOException {
		reset();
		File file = path.toFile();
		addHeader("Content-Length", "" + file.length());
		addHeader("Content-Type", Files.probeContentType(path));
		Files.copy(path, getOutputStream());
	}

	public void download(Path path) throws IOException {
		download(path, null);
	}

	public void download(Path path, String name) throws IOException {
		File file = path.toFile();
		try(FileInputStream in = new FileInputStream(file)){
			download(in, null == name ? file.getName() : name, file.length(),  Files.probeContentType(path));
		}
	}
	
	public void download(InputStream inputStream , String fileName, long length ,String contentType) throws IOException {
		reset();
		addHeader("Content-Disposition", "attachment; filename=" + fileName);
		addHeader("Content-Length", "" + length);
		addHeader("Content-Type", contentType);
		ServletUtils.pipe(inputStream, getOutputStream());
	}
	
}
