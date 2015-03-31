package com.rockson.servletplus;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;

public class HashRouter extends SubRouter {
	HashMap<String, Handler> routers = new HashMap<String, Handler>();
	
	protected String routerKey(String method , String subPath) {
		return method+"."+subPath;
	}

	public HashRouter(Servlet servlet) {
		super(servlet);
	}

	@Override
	public void get(String path, Handler handler) {
		routers.put(routerKey("GET", path), handler);
	}

	@Override
	public boolean route(Request req, Response res) throws IOException, ServletException {
		Handler handler = routers.get(routerKey(req.getMethod() , getSubPath(req)));
		if(null == handler){
			return false;
		}
		handler.handle(req, res);
		return true;
	}

}
