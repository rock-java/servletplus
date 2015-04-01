package com.rockson.servletplus;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet{
	private static final long serialVersionUID = 6470041805758397687L;
	protected SubRouter subRouter;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		get(new Request(req), new Response(res));
	}
	
	protected void get(Request req , Response res) throws ServletException, IOException {
		super.doGet(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		post(new Request(req), new Response(res));
	}
	
	protected void post(Request req , Response res) throws ServletException, IOException {
		super.doPost(req, res);
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		put(new Request(req), new Response(res));
	}
	
	protected void put(Request req , Response res) throws ServletException, IOException {
		super.doPut(req, res);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		delete(new Request(req), new Response(res));
	}
	
	protected void delete(Request req , Response res) throws ServletException, IOException {
		super.doGet(req, res);
	}
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		head(new Request(req), new Response(res));
	}
	
	protected void head(Request req , Response res) throws ServletException, IOException {
		super.doHead(req, res);
	}
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		get(new Request(req), new Response(res));
	}
	
	protected void options(Request req , Response res) throws ServletException, IOException {
		super.doOptions(req, res);
	}
	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		trace(new Request(req), new Response(res));
	}
	
	protected void trace(Request req , Response res) throws ServletException, IOException {
		super.doHead(req, res);
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(null!=subRouter){
			if(subRouter.route(new Request(req), new Response(res))){
				return;
			}
		}
		verb(req.getMethod(), new Request(req), new Response(res));
	}
	
	protected void verb(String method , Request req , Response res) throws ServletException, IOException {
		super.service(req, res);
	}

}
