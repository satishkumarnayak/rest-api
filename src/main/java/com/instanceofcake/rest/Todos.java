package com.instanceofcake.rest;

import java.util.HashMap;
import java.util.Map;

public class Todos {

	public static Map<Long, Todo> todos1 = new HashMap();

	static {

		todos1.put(1L, new Todo(1L, "Evyaan isee"));
		todos1.put(2L, new Todo(2L, "Evyaan vok vok"));

	}

	public Long nextId() {
		Long nextId = todos1.keySet().size() + 1L;
		return nextId;
	}
}
