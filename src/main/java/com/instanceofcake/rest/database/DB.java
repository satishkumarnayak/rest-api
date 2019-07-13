package com.instanceofcake.rest.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dalesbred.Database;

public class DB {

	public static final Database db;
//	public static final EntityManagerFactory emf;

	static {
		db = Database.forUrlAndCredentials("jdbc:mysql://localhost:3306/test", "root", "admin");
	//	emf = Persistence.createEntityManagerFactory("todos");
	}

}
