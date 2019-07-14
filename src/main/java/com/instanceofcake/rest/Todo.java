package com.instanceofcake.rest;

import java.io.Serializable;

public class Todo {

	private long id;
	private String name;

	public Todo(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Todo() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
