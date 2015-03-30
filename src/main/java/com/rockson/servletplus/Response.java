package com.rockson.servletplus;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.alibaba.fastjson.JSON;

public class Response extends HttpServletResponseWrapper {

	public Response(HttpServletResponse response) {
		super(response);
	}
	
	public void json(Object object ) throws IOException {
		getWriter().write(JSON.toJSONString(object));
	}


}
