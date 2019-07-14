package com.instanceofcake.rest.database;

import org.dalesbred.Database;

public class DB {

	public static Database db;

	static {
		db = Database.forUrlAndCredentials("jdbc:mysql://localhost:3306/test", "root", "admin");
	}

}
