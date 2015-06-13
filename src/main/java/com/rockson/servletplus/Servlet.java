package com.rockson.servletplus;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet{
	private static final long serialVersionUID = 6470041805758397687L;
	protected Router router = new BasicRouter(this);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Request request = new Request(req);
		get(request, new Response(res , request , this.getServletContext()));
	}
	
	protected void get(Request req , Response res) throws ServletException, IOException {
		super.doGet(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Request request = new Request(req);
		post(request, new Response(res,request ,this.getServletContext()));
	}
	
	protected void post(Request req , Response res) throws ServletException, IOException {
		super.doPost(req, res);
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Request request = new Request(req);
		put(request, new Response(res,request,this.getServletContext()));
	}
	
	protected void put(Request req , Response res) throws ServletException, IOException {
		super.doPut(req, res);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Request request = new Request(req);
		delete(request, new Response(res,request,this.getServletContext()));
	}
	
	protected void delete(Request req , Response res) throws ServletException, IOException {
		super.doGet(req, res);
	}
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Request request = new Request(req);
		head(request, new Response(res,request,this.getServletContext()));
	}
	
	protected void head(Request req , Response res) throws ServletException, IOException {
		super.doHead(req, res);
	}
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Request request = new Request(req);
		get(request, new Response(res,request,this.getServletContext()));
	}
	
	protected void options(Request req , Response res) throws ServletException, IOException {
		super.doOptions(req, res);
	}
	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Request request = new Request(req);
		trace(request, new Response(res,request,this.getServletContext()));
	}
	
	protected void trace(Request req , Response res) throws ServletException, IOException {
		super.doHead(req, res);
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Request request = new Request(req);
		if(null!=router){
			if(router.route(request, new Response(res,request,this.getServletContext()))){
				return;
			}
		}
		verb(req.getMethod(), request, new Response(res,request,this.getServletContext()));
	}
	
	protected void verb(String method , Request req , Response res) throws ServletException, IOException {
		if(null!= req.getPathInfo() && req.getPathInfo().endsWith(".jsp")) {
			getServletContext().getNamedDispatcher("jsp").forward(req, res);
		}else{
			getServletContext().getNamedDispatcher("default").forward(req, res);
		}
	}

}
