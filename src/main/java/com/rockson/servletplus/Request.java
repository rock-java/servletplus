package com.rockson.servletplus;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class Request extends HttpServletRequestWrapper {
	private static final Map<String, String> errors = new LinkedHashMap<String, String>();
	protected Map<String, String> pathParams;
	
//	protected HttpServlet servlet;
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

	/**
	 * The remote IP address of the request.
	 * 
	 * @param trustProxy
	 *            If the trustProxy is setting enabled, it is the upstream address;
	 * @return the ip of the remote host
	 */
	public String ip(boolean trustProxy) {
		if (!trustProxy) {
			return getRemoteAddr();
		} else {
			String[] ips = ips();
			if (null == ips || 0 == ips.length) {
				return getRemoteAddr();
			} else {
				return ips[0];
			}
		}
	}

	/**
	 * this property contains an array of IP addresses specified in the 鈥淴-Forwarded-For鈥� request header. Otherwise, it
	 * contains an empty array.
	 * 
	 * For example, if 鈥淴-Forwarded-For鈥� is 鈥渃lient, proxy1, proxy2鈥�, req.ips would be ["client", "proxy1", "proxy2"],
	 * where 鈥減roxy2鈥� is the furthest downstream.
	 * 
	 * @return ips
	 */
	public String[] ips() {
		String forward = getHeader("X-Forwarded-For");
		return null == forward ? null : forward.split(",");
	}

}
