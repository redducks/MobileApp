package com.pls.sendmail.business;

import com.pls.sendmail.business.impl.MailerTaskHTML;
import com.pls.sendmail.business.impl.MailerTaskText;
import com.pls.sendmail.business.impl.MailerTaskXML;
import com.pls.sendmail.co.HTMLMailCriteria;
import com.pls.sendmail.co.MailCriteria;
import com.pls.sendmail.co.TemplateMailCriteria;
import com.pls.sendmail.co.TextMailCriteria;
import com.pls.sendmail.exceptions.MailerException;

/**
 * simple factory class to return a concrete task based on the
 * type of the criteria object
 * 
 * @author glawler
 *
 */
public class MailerTaskFactory {

	
	public static MailerTask getTaskForCriteria(MailCriteria messageObject){ 
		
		if(messageObject == null){
			throw new MailerException("Criteria object is null, cannot create Mailer task");
		}
		
		
		if(messageObject instanceof TextMailCriteria){
			return createTextTask((TextMailCriteria)messageObject);
			
		}else if(messageObject instanceof HTMLMailCriteria){
			return createHTMLTask((HTMLMailCriteria)messageObject);
		
		}else if(messageObject instanceof TemplateMailCriteria){
			return createXMLTask((TemplateMailCriteria)messageObject);
			
		}else{
			StringBuffer message = new StringBuffer("");
			message.append("No MailerTask is defined to support the supplied MailCriteria type. ");
			message.append("Unable to continue processing on message descibed as: ");
			message.append( messageObject.getMailInstanceDescription());
			throw new MailerException(message.toString());
		}
		
	}

	
	protected static MailerTaskText createTextTask(TextMailCriteria messageCriteriaObject){
		
		return new MailerTaskText(messageCriteriaObject);
	}
	
	
	
	protected static MailerTaskHTML createHTMLTask(HTMLMailCriteria messageCriteriaObject){
	
		return new MailerTaskHTML(messageCriteriaObject);
	}
	
	
	protected static MailerTaskXML createXMLTask(TemplateMailCriteria messageCriteriaObject){
		
		return new MailerTaskXML(messageCriteriaObject);
	}

	
}
