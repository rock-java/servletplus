package com.rockson.servletplus;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ServletFilter implements Filter{
	protected FilterConfig filterConfig;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		Request req = new Request((HttpServletRequest) request);
		filter(req, new Response((HttpServletResponse) response,req ,filterConfig.getServletContext()), chain);
	}
	
	public abstract void filter(Request req ,Response res, FilterChain chain) throws IOException,	ServletException ;

	@Override
	public void destroy() {}

}
