package com.pls.core.dao;

import org.junit.After;
import org.junit.Before;

import com.pls.core.AbstractTest;

public abstract class AbstractDaoTest<T> extends AbstractTest<T>{
	@After
	public void tearDown() {
	    if (PersistenceHelper.getEntityManager().getTransaction().isActive()) {
	        PersistenceHelper.getEntityManager().getTransaction().rollback();
	    }
	}
	
	@Before
	public void setUp() {
	    if (!PersistenceHelper.getEntityManager().getTransaction().isActive()) {
	        PersistenceHelper.getEntityManager().getTransaction().begin();
	    }
	}
}
