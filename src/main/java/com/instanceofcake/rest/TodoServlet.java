package com.instanceofcake.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transaction;

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

		// EntityManager em = DB.emf.createEntityManager();
		// Todo todo = em.find(Todo.class, id);

		
		SqlQuery query = SqlQuery.namedQuery("SELECT * FROM todo WHERE id = :id", Map.of("id", id));
		System.out.println("-------------------------"+query);

		Todo todo = DB.db.findOptional(Todo.class, query).orElse(null);
		
		if (todo == null) {
			resp.setHeader("Content-Type", "application/text");
			resp.setStatus(401);
			resp.getWriter().println("Entity Doesnt Exists with Id-" + id);
		}
		String json = GSON.toJson(todo);
		// String json = GSON.toJson(todo);
		resp.setHeader("Content-type", "application/json");
		resp.setStatus(200);
		resp.getWriter().println(json);

	}

	

	@Override

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("11111111111111111111111");
		requestURI = req.getRequestURI();
		String json = Util.toStringBody(req.getInputStream());
		// Long id = Long.parseLong(requestURI.substring("/todos/".length()));
		/*
		 * if (Todos.todos.containsKey(id)) { resp.setHeader("Content-Type",
		 * "application/json"); resp.setStatus(422);
		 * resp.getWriter().println("Id Already Exists-" + id); }
		 */

		Todo todo = GSON.fromJson(json, Todo.class);
		// EntityManager em = DB.emf.createEntityManager();

		// @NotNull
		SqlQuery namedQuery = SqlQuery.namedQuery("INSERT INTO Todo (name) values (:name)", todo);
		DB.db.update(namedQuery);
		/*
		 * EntityTransaction transaction = em.getTransaction(); try {
		 * transaction.begin(); em.persist(todo); transaction.commit(); } catch
		 * (Exception e) { transaction.rollback(); e.printStackTrace(); }
		 */

		// todo.setId(id);
		// Todos.todos.put(id, todo);

		resp.setHeader("Content-Type", "application/json");
		resp.setStatus(201);
		resp.getWriter().println(GSON.toJson(todo));

	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
System.out.println("222222222222222222");
		requestURI = req.getRequestURI();
		String json = Util.toStringBody(req.getInputStream());
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
		SqlQuery namedQuery = SqlQuery.namedQuery("UPDATE  Todo SET name = :name WHERE id = :id", Map.of("name", todoBody.getName(), "id", id));
		System.out.println(namedQuery);
		DB.db.update(namedQuery);
		});

		// EntityManager em = DB.emf.createEntityManager();
		// Todo todo = em.find(Todo.class, id);
		/*
		 * if (todo == null) { resp.setHeader("Content-Type", "application/text");
		 * resp.setStatus(422); resp.getWriter().println("Entity Doesnt Exists with Id-"
		 * + id); }
		 */
		/*
		 * todo = GSON.fromJson(json, Todo.class); todo.setId(id); //
		 * Todos.todos.put(id, todo); em.merge(todo);
		 */
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

		// EntityManager em = DB.emf.createEntityManager();
		/*
		 * Todo todo = em.find(Todo.class, id); if (todo == null) {
		 * resp.setHeader("Content-Type", "application/text"); resp.setStatus(422);
		 * resp.getWriter().println("Entity Doesnt Exists with Id-" + id); } // todo =
		 * GSON.fromJson(json, Todo.class); // Todo todo = Todos.todos.remove(id);
		 * em.remove(todo);
		 */

		SqlQuery namedQuery = SqlQuery.namedQuery("DELETE FROM Todo WHERE id = :id", Map.of("id", id));
		DB.db.update(namedQuery);

		String json = GSON.toJson(todo);
		resp.setHeader("Content-type", "application/json");
		resp.setStatus(200);
		resp.getWriter().println(json);
	}

}
