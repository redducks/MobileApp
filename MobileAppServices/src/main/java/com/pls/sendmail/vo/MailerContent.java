package com.pls.sendmail.vo;

import java.io.Serializable;

/**
 * Generic interface for all types of MailerContent
 * 
 * @author glawler
 *
 */
public interface MailerContent extends Serializable {

	/**
	 * For the convenience of the Criteria Object, the Template Class is forced to 
	 * provide this accessor method which defines the subject line for the method.
	 * The approach is to have the template classes model the message.
	 *   
	 * An XSLT template and a template class together are a concrete message. 
	 * @return the subject line for the message
	 */
	public String getSubjectLine();


	
}
