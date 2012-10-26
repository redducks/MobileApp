package com.pls.core.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PersistenceHelper {
	private static final EntityManager entityManager;
	
	static {
		entityManager = Persistence.createEntityManagerFactory("plsproTest").createEntityManager();
	}
	
	public static EntityManager getEntityManager() {
		return entityManager;
	};
}
