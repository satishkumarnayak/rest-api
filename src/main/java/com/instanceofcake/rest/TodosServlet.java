package com.instanceofcake.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instanceofcake.rest.database.DB;


public class TodosServlet extends HttpServlet {

	static final Gson GSON = new GsonBuilder().create();
	

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Todo> todos = DB.db.findAll(Todo.class, "select * from todos");
	//	EntityManager em = DB.emf.createEntityManager();
	//	TypedQuery<Todo> createQuery = (TypedQuery<Todo>) em.createNativeQuery("select * from todos", Todo.class);
	//	List<Todo> todos = createQuery.getResultList();
		String json = GSON.toJson(todos);
		resp.setHeader("Content-type", "application/json");
		resp.setStatus(200);
		resp.getWriter().println(json);

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String json = Util.toStringBody(req.getInputStream());
		Todo todo = GSON.fromJson(json, Todo.class);
	//	Todos.todos.put(todo.getId(), todo);
		resp.setHeader("Content-type", "application/json");
		resp.setStatus(201);
		resp.getWriter().println(json);
	}

}
