package com.instanceofcake.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getHeader("Custom-Name");
		resp.setStatus(200);
		resp.getWriter().print(name!=null ? "Hello, "+ name : " "+" Hello World!");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(201);
		resp.getWriter().print("Hello World Again!");
	}
}
