package com.rockson.servletplus;

import java.io.IOException;

import javax.servlet.ServletException;

@FunctionalInterface
public interface Handler {
	void handle(Request req , Response res) throws IOException,ServletException;
}
