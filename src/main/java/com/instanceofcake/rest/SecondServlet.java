package com.instanceofcake.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecondServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(200);
	//	resp.setHeader("Content-Type", "application/json");
		resp.setContentType("application/json");
		resp.getWriter().print("{\"name\":\"evyaan\"}");

	}

}
