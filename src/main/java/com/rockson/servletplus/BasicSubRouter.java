package com.rockson.servletplus;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;

public class BasicSubRouter extends SubRouter {

	HashMap<String, Handler> routers = new HashMap<String, Handler>();
	LinkedList<ParamRouter> paramRouters = new LinkedList<BasicSubRouter.ParamRouter>();

	protected String routerKey(String method, String subPath) {
		return method + "#" + subPath;
	}

	public BasicSubRouter(Servlet servlet) {
		super(servlet);
	}

	public boolean isParamPath(String path) {
		return -1 != path.indexOf('{') && -1 != path.indexOf('}');
	}

	@Override
	public boolean route(Request req, Response res) throws IOException, ServletException {
		String method = req.getMethod();
		String subPath = getSubPath(req);
		System.out.println("route - " + method + " " + subPath);
		Handler handler = routers.get(routerKey(method, subPath));
		if (null != handler) {
			handler.handle(req, res);
			return true;
		}
		ParamRouter paramRouter = getParamRouter(method, subPath, req);
		if (null != paramRouter) {
			paramRouter.getHandler().handle(req, res);
			return true;
		}
		return false;
	}

	public ParamRouter getParamRouter(String method, String path, Request req) {
		Map<String, String> pathParams;
		for (ParamRouter router : this.paramRouters) {
			pathParams = router.matches(method, path);
			if (null != pathParams) {
				req.setPathParams(pathParams);
				return router;
			}
		}
		return null;
	}

	@Override
	public void get(String path, Handler handler) {
		verb("GET", path, handler);
	}

	@Override
	public void post(String path, Handler handler) {
		verb("POST", path, handler);
	}

	@Override
	public void delete(String path, Handler handler) {
		verb("DELETE", path, handler);

	}

	@Override
	public void put(String path, Handler handler) {
		verb("PUT", path, handler);

	}

	@Override
	public void head(String path, Handler handler) {
		verb("HEAD", path, handler);
	}

	@Override
	public void options(String path, Handler handler) {
		verb("OPTIONS", path, handler);
	}

	@Override
	public void trace(String path, Handler handler) {
		verb("TRACE", path, handler);
	}

	@Override
	public void verb(String method, String path, Handler handler) {
		if (isParamPath(path)) {
			paramRouters.add(new ParamRouter(method.toUpperCase(), path, handler));
		} else {
			routers.put(routerKey(method.toUpperCase(), path), handler);
		}

	}

	public static class ParamRouter {
		private String method;
		private Handler handler;
		private ParamPath paramPath;

		public Map<String, String> matches(String method, String path) {
			if (null != this.method && !this.method.equalsIgnoreCase(method)) {
				return null;
			}
			return paramPath.matchPathReg(path);
		}

		public ParamRouter() {
		}

		public ParamRouter(String method, String path, Handler handler) {
			this.method = method;
			this.paramPath = new ParamPath(path);
			this.handler = handler;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public Handler getHandler() {
			return handler;
		}

		public void setHandler(Handler handler) {
			this.handler = handler;
		}

	}

}
