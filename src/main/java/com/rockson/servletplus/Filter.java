package com.rockson.servletplus;

import java.io.IOException;

import javax.servlet.ServletException;

@FunctionalInterface
public interface Filter {
	void filter(Request req , Response res , Next next) throws IOException, ServletException;
}
