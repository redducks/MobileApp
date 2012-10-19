package com.pls.core.service;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.pls.sendmail.business.Mailer;
import com.pls.sendmail.co.TemplateMailCriteria;
import com.pls.sendmail.vo.TemplateContent;

public abstract class BaseService {
	
	protected boolean fireRules(String ruleFile, Object... obj) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(ruleFile), ResourceType.DRL);
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		// insert objects into working memory
		for (Object o : obj) {
//			FactHandle userFH = ksession.insert(o);
			ksession.insert(o);
		}
		ksession.fireAllRules();
//		ksession.retract(userFH);
		ksession.dispose();
		return true;
	}
	
	protected void sendMail(TemplateContent content, String recipient, String sender) {
		TemplateMailCriteria mail = new TemplateMailCriteria();
		mail.addRecipient(recipient);
		mail.setMailerContent(content);
		
		if (sender != null) {
			mail.setSender(sender);
		}
		
		Mailer.instance().sendMail(mail);
	}
	
	protected void sendMail(TemplateContent content, String recipient) {
		sendMail(content, recipient, null);
	}
	
	protected void sendHTMLMail(TemplateContent content, String recipient) {
		TemplateMailCriteria mail = new TemplateMailCriteria();
		mail.addRecipient(recipient);
		mail.setMailerContent(content);
		
		Mailer.instance().sendMail(mail);
	}
	
	protected void sendTextMail(TemplateContent content, String recipient) {
		TemplateMailCriteria mail = new TemplateMailCriteria();
		mail.addRecipient(recipient);
		mail.setMailerContent(content);
		
		Mailer.instance().sendMail(mail);
	}
}

