package com.rockson.servletplus;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;

public class BasicRouter extends Router {

	HashMap<String, Handler> routers = new HashMap<String, Handler>();
	LinkedList<ParamRouter> paramRouters = new LinkedList<BasicRouter.ParamRouter>();
	LinkedList<ParamFilter> paramFilters = new LinkedList<BasicRouter.ParamFilter>();
	private static final String __REQUEST_IS_END__ = "__REQUEST_IS_END__";

	protected void endRequest(Request req) {
		req.setAttribute(__REQUEST_IS_END__, true);
	}

	protected boolean isRequestEnded(Request req) {
		Object result = req.getAttribute(__REQUEST_IS_END__);
		return null == result ? false : (boolean) result;
	}

	protected String routerKey(String method, String subPath) {
		return method + "#" + subPath;
	}

	public BasicRouter(Servlet servlet) {
		super(servlet);
	}

	public void route(Request req, Response res, int i) throws IOException, ServletException {
		if (i >= paramFilters.size()) {
			if (handle(req, res)) {
				endRequest(req);
			}
			return;
		}
		String method = req.getMethod();
		String subPath = getSubPath(req);
		ParamFilter filter = paramFilters.get(i);
		Map<String, String> pathParams = (null == filter ? null : filter.matches(method, subPath));
		if (null != pathParams) {
			req.setPathParams(pathParams);
			filter.getFilter().filter(req, res, () -> {
				route(req, res, i + 1);
			});
		} else {
			route(req, res, i + 1);
		}
	}

	@Override
	public boolean route(Request req, Response res) throws IOException, ServletException {
		route(req, res, 0);
		return isRequestEnded(req);
	}

	public boolean handle(Request req, Response res) throws IOException, ServletException {
		String method = req.getMethod();
		String subPath = getSubPath(req);
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
	public void verb(String method, String path, Filter filter) {
		if(!path.startsWith("^") && !path.endsWith("$")) {
			path+=".*";
		}
		paramFilters.add(new ParamFilter(null==method?null:method.toUpperCase(), path, filter));
	}

	public boolean isParamPath(String path) {
		return 0==path.indexOf('^') || -1 != path.indexOf('{') && -1 != path.indexOf('}');
	}

	@Override
	public void verb(String method, String path, Handler handler) {
		method = (null==method?null:method.toUpperCase());
		if (isParamPath(path)) {
			paramRouters.add(new ParamRouter(method, path, handler));
		} else {
			routers.put(routerKey(method, path), handler);
		}
	}

	public static class ParamFilter {
		private String method;
		private Filter filter;
		private ParamPath paramPath;

		public Map<String, String> matches(String method, String path) {
			if (null != this.method && !this.method.equalsIgnoreCase(method)) {
				return null;
			}
			return paramPath.matchPathReg(path);
		}

		public ParamFilter(String method, String path, Filter filter) {
			this.method = method;
			this.filter = filter;
			this.paramPath = new ParamPath(path);
		}

		public Filter getFilter() {
			return filter;
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
