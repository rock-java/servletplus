package com.rockson.servletplus;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

public abstract class Router {
	protected Servlet servlet;

	public Router(Servlet servlet) {
		this.servlet = servlet;
	}

	public String getBasePath() {
		WebServlet webServlet = this.servlet.getClass().getAnnotation(WebServlet.class);
		String path = null;
		
		if (webServlet.value().length > 0 ) {
			path = webServlet.value()[0];
		}
		if(webServlet.urlPatterns().length>0){
			path = webServlet.urlPatterns()[0];
		}
		if(null == path) {
			return null;
		}
		if (!path.endsWith("/*")) {
			return "";
		} else {
			return path.substring(0, path.length() - 2);
		}

	}

	public String getSubPath(HttpServletRequest req) {
		String base = getBasePath();
		int contentPathLen = req.getContextPath().length();
		if (null == base) {
			return null;
		} else {
			String subPath = req.getRequestURI().substring(contentPathLen + base.length());
			return 0 == subPath.length() ? "/":ServletUtils.trimPath(subPath);
		}
	}
	

	public void get(String path, Handler handler) {
		verb("GET", path, handler);
	}

	public void post(String path, Handler handler) {
		verb("POST", path, handler);
	}

	public void delete(String path, Handler handler) {
		verb("DELETE", path, handler);
	}

	public void put(String path, Handler handler) {
		verb("PUT", path, handler);
	}

	public void head(String path, Handler handler) {
		verb("HEAD", path, handler);
	}

	public void options(String path, Handler handler) {
		verb("OPTIONS", path, handler);
	}

	public void trace(String path, Handler handler) {
		verb("TRACE", path, handler);
	}
	public void all(String path, Handler handler) {
		verb(null, path, handler);
	}

	public abstract void verb(String method, String path, Handler handler);
	
	public void get(String path,Filter filter) {
		verb("GET", path, filter);
	}

	public void post(String path, Filter filter) {
		verb("POST", path, filter);
	}

	public void delete(String path, Filter filter) {
		verb("DELETE", path, filter);
	}

	public void put(String path, Filter filter) {
		verb("PUT", path, filter);
	}

	public void head(String path, Filter filter) {
		verb("HEAD", path, filter);
	}

	public void options(String path,Filter filter) {
		verb("OPTIONS", path, filter);
	}

	public void trace(String path, Filter filter) {
		verb("TRACE", path, filter);
	}
	public void all(String path, Filter filter) {
		verb(null, path, filter);
	}
	public void all(Filter filter) {
		verb(null, "^.*$", filter);
	}

	public abstract void verb(String method, String path, Filter filter);

	public abstract boolean route(Request req, Response res) throws IOException, ServletException;

}
