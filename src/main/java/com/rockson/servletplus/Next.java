package com.rockson.servletplus;

import java.io.IOException;

import javax.servlet.ServletException;

@FunctionalInterface
public interface Next {
	void apply() throws IOException, ServletException;
}
