package com.rockson.servletplus;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class Request extends HttpServletRequestWrapper {
	private static final Map<String, String> errors = new LinkedHashMap<String, String>();
	protected Map<String, String> pathParams;
	
	public Request(HttpServletRequest request) {
		super(request);
	}

	public boolean isXhr() {
		return this.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest");
	}

	public ParamValidator check(String name) {
		return new ParamValidator(name, getParameter(name), errors);
	}

	public boolean hasError() {
		return errors.isEmpty();
	}

	public String getPathParam(String name) {
		return null == pathParams ? null : pathParams.get(name);
	}

	public Map<String, String> getPathParams() {
		return pathParams;
	}

	public void setPathParams(Map<String, String> pathParams) {
		this.pathParams = pathParams;
	}
	

}
