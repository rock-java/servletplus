package com.rockson.servletplus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.alibaba.fastjson.JSON;

public class Response extends HttpServletResponseWrapper {
	protected ServletContext servletContext;
	public Response(HttpServletResponse response,ServletContext servletContext) {
		super(response);
		this.servletContext = servletContext;
	}

	public void json(Object object) throws IOException {
		getWriter().write(JSON.toJSONString(object));
	}

	protected void copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
		byte[] buff = new byte[bufferSize <= 0 ? 4 * 1024 : bufferSize];
		int len;
		while (-1 != (len = in.read(buff))) {
			out.write(buff, 0, len);
		}
	}

	public void send(String str) throws IOException {
		if (null == str) {
			return;
		}
		getWriter().write(str);
	}

	public void sendFile(String str) throws IOException {
		String path = servletContext.getRealPath(str);
		File file = new File(path);
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
		reset();
		File file = path.toFile();
		addHeader("Content-Disposition", "attachment; filename=" + (null == name ? file.getName() : name));
		addHeader("Content-Length", "" + file.length());
		addHeader("Content-Type", Files.probeContentType(path));
		
		Files.copy(path, getOutputStream());
	}
	
}
