package com.pls.organization.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

import com.pls.organization.model.User;

@Stateless
public class UserService {

	@Inject
	private transient Logger logger;

	@Inject
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<User> searchUsersByUserId(String userId) {
		userId = userId.toUpperCase().replaceAll("\\*", "%").replaceAll("'", "''");
		return em.createNamedQuery("User.searchByUserId", User.class)
				.setParameter("userId", userId)
				.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<User> getAllUsers() {
		return searchUsersByUserId("a*");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String addUser(User newUser) {
		try {
			fireRules(newUser);
		} catch (Throwable t) {
			System.out.println("Exception " + t.getMessage());
			t.printStackTrace();
		}
		em.persist(newUser);
		logger.info("Added " + newUser);
		return "success";
	}
	
	private boolean fireRules(User user) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/useridempty.drl"), ResourceType.DRL);
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		// insert objects into working memory
		FactHandle userFH = ksession.insert(user);
		ksession.fireAllRules();
		ksession.retract(userFH);
		ksession.dispose();
		return true;
	}
}
