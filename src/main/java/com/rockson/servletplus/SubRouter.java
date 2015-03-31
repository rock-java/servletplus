package com.rockson.servletplus;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

public abstract class SubRouter {
	protected Servlet servlet;

	public SubRouter(Servlet servlet) {
		this.servlet = servlet;
	}
	
	public String getBasePath(){
		WebServlet webServlet = this.servlet.getClass().getAnnotation(WebServlet.class);
		if(webServlet.value().length<=0){
			return null;
		}
		String path = webServlet.value()[0];
		if(!path.endsWith("/*")){
			return null;
		}else{
			return path.substring(0 , path.length()-2);
		}
		
	}
	public String getSubPath(HttpServletRequest req){
		String base = getBasePath();
		int contentPathLen = req.getContextPath().length();
		if(null == base) {
			return null;
		}else{
			return req.getRequestURI().substring(contentPathLen+base.length());
		}
	}
	
	public abstract void get(String path , Handler handler);

	public abstract boolean route(Request request, Response response) throws IOException, ServletException;
	
	
}
