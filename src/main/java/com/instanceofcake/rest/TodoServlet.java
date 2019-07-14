package com.instanceofcake.rest;

import java.io.IOException;

import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dalesbred.query.SqlQuery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instanceofcake.rest.database.DB;
import static org.dalesbred.query.SqlQuery.namedQuery;

public class TodoServlet extends HttpServlet {

	static final Gson GSON = new GsonBuilder().create();
	private String requestURI;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		Long id = Long.parseLong(requestURI.substring("/todos/".length()));

		SqlQuery query = SqlQuery.namedQuery("SELECT * FROM todo WHERE id = :id", Map.of("id", id));

		Todo todo = DB.db.findOptional(Todo.class, query).orElse(null);

		if (todo == null) {
			resp.setHeader("Content-Type", "application/text");
			resp.setStatus(401);
			resp.getWriter().println("Entity Doesnt Exists with Id-" + id);
		}
		String json = GSON.toJson(todo);
		resp.setHeader("Content-type", "application/json");
		resp.setStatus(200);
		resp.getWriter().println(json);

	}

	@Override

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		requestURI = req.getRequestURI();
		String json = Util.readInputStream(req.getInputStream());

		Todo todo = GSON.fromJson(json, Todo.class);

		SqlQuery namedQuery = SqlQuery.namedQuery("INSERT INTO Todo (name) values (:name)", todo);
		DB.db.update(namedQuery);
		resp.setHeader("Content-Type", "application/json");
		resp.setStatus(201);
		resp.getWriter().println(GSON.toJson(todo));

	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		requestURI = req.getRequestURI();
		String json = Util.readInputStream(req.getInputStream());
		Long id = Long.parseLong(requestURI.substring("/todos/".length()));
		Todo todoBody = GSON.fromJson(json, Todo.class);
		SqlQuery query = namedQuery("SELECT * FROM Todo WHERE id = :id", Map.of("id", id));
		Optional<Todo> todo = DB.db.findOptional(Todo.class, query);
		if (todo.isEmpty()) {
			resp.setHeader("Content-Type", "application/text");
			resp.setStatus(401);
			resp.getWriter().println("Entity Doesnt Exists with Id-" + id);
		}

		DB.db.withVoidTransaction(tx -> {
			SqlQuery namedQuery = SqlQuery.namedQuery("UPDATE  Todo SET name = :name WHERE id = :id",
					Map.of("name", todoBody.getName(), "id", id));
			System.out.println(namedQuery);
			DB.db.update(namedQuery);
		});

		resp.setHeader("Content-Type", "application/json");
		resp.setStatus(200);
		resp.getWriter().println(GSON.toJson(todo));

	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		Long id = Long.parseLong(requestURI.substring("/todos/".length()));

		SqlQuery query = namedQuery("SELECT * FROM Todos WHERE id = :id", Map.of("id", id));
		Optional<Todo> todo = DB.db.findOptional(Todo.class, query);
		if (todo.isEmpty()) {
			resp.setHeader("Content-Type", "application/text");
			resp.setStatus(401);
			resp.getWriter().println("Entity Doesnt Exists with Id-" + id);
		}

		SqlQuery namedQuery = SqlQuery.namedQuery("DELETE FROM Todo WHERE id = :id", Map.of("id", id));
		DB.db.update(namedQuery);

		String json = GSON.toJson(todo);
		resp.setHeader("Content-type", "application/json");
		resp.setStatus(200);
		resp.getWriter().println(json);
	}

}
