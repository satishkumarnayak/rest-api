package com.instanceofcake.rest.router;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.instanceofcake.rest.TodoServlet;

public class RouterServlet extends HttpServlet {

	private static final TodoServlet TODOSERVLET = new TodoServlet();

	private String requestURI;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		requestURI = req.getRequestURI();
		if (requestURI.startsWith("/todos/")) {
			TODOSERVLET.doGet(req, resp);
		} else {
			noHandler(resp);
		}
	}

	private void noHandler(HttpServletResponse resp) throws IOException {
		resp.setStatus(404);
		resp.getWriter().println("No Router Found");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		requestURI = req.getRequestURI();
		if (requestURI.startsWith("/todos/")) {
			TODOSERVLET.doPost(req, resp);
		} else {
			noHandler(resp);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		requestURI = req.getRequestURI();
		if (requestURI.startsWith("/todos/")) {
			TODOSERVLET.doPut(req, resp);
		} else {
			noHandler(resp);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		requestURI = req.getRequestURI();
		if (requestURI.startsWith("/todos/")) {
			TODOSERVLET.doDelete(req, resp);
		} else {
			noHandler(resp);
		}
	}
}
