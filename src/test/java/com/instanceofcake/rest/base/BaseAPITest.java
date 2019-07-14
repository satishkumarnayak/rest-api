package com.instanceofcake.rest.base;

import static org.junit.Assert.*;

import java.util.List;

import org.dalesbred.Database;
import org.dalesbred.annotation.SQL;
import org.junit.Before;
import org.junit.Test;

import com.instanceofcake.rest.Todo;
import com.instanceofcake.rest.Util;
import com.instanceofcake.rest.database.DB;

public class BaseAPITest {

	@Before
	public void setUp() throws Exception {
		DB.db = Database.forUrlAndCredentials("jdbc:hsqldb:mem:memdb;sql.syntax_mys=true;sql.enforce_size=false", "SA","");
		String[] instructions = Util.readInputStream(BaseAPITest.class.getResourceAsStream("/setup.sql")).split(";");
		for(@SQL String instruction : instructions) {
			DB.db.update(instruction);
		}
		
	}

	@Test
	public void test() {
		List<Todo> todos = DB.db.findAll(Todo.class, "SELECT * from Todo");
		assertEquals(2, todos.size());
	}

}
