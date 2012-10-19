package com.pls.sendmail.business.impl;

import org.apache.log4j.Logger;

import com.pls.sendmail.business.MailerTask;
import com.pls.sendmail.co.HTMLMailCriteria;
import com.pls.sendmail.co.MailCriteria;
import com.pls.sendmail.exceptions.MailerException;
import com.pls.sendmail.vo.SentMessageNotificationVO;

/**
 * Concrete mailer task.
 * Implements e-mail message where the data is in HTML format
 */
public final class MailerTaskHTML extends MailerTask {

	private static final long serialVersionUID = -8391958184203233896L;

	private final static Logger LOG = Logger.getLogger(MailerTaskHTML.class.getName());

	
	private HTMLMailCriteria criteria;

	
	protected String body;
	protected String bodyContentType;

	
	
	/**
	 * Must supply an HTMLMailCriteria reference to create this task
	 * @param messageCriteriaObject
	 */
	public MailerTaskHTML(HTMLMailCriteria messageCriteriaObject){
		super(messageCriteriaObject);
	}
	
	
	/**
	 * Provided to satisfy the super class.
	 */
	@Override
	protected MailCriteria getMailCriteria() {
		return this.criteria;
	}


	/**
	 * Provided to satisfy the super class.
	 */
	@Override
	protected void setMailCriteria(MailCriteria mailCriteria) {
		this.criteria = (HTMLMailCriteria) mailCriteria;
	}
	
    



	/**
	 * Prepares the mailer request for processing by the
	 * base send logic implemented in the MailerTask.
	 * 
	 * For text HTML email, there may be a need to transform
	 * the data held by the criteria object into the HTML 
	 * to be sent.  Without transformation, the only 
	 * preparation is to cast the content to String,
	 * but the MimeType will still be set as text/html.
	 * 
	 */
	@Override
	public SentMessageNotificationVO prepareContent() throws MailerException{

		if(criteria != null){
			
			if(criteria.getHTMLMailContent()!= null && criteria.getMailContentType() != null){
		
				//assume that it's already String content
				this.body = criteria.getHTMLMailContent();
				this.bodyContentType = criteria.getMailContentType().toString();

			}else{
				//no content was provided
				//  it may not be desirable, but it is legal to send a message with no body.
				this.body = "";
				this.bodyContentType = MailCriteria.ContentType.TEXT_PLAIN.toString();
				LOG.info("MailerTaskSimple prepared an HTML message with no body content. Message description:");
				LOG.info( criteria.getMailInstanceDescription() );
			}
			
			return saveOutgoingMessage();

		}else{
			throw new MailerException("Unable to prepare an HTML mailer request due to null criteria object.");
		}
	}




	/**
     * SendMessage() Dependency Helper
     * 
     * @return MimeType for the BodyPart content
	 */
	@Override
	protected String getContentType() {
		
		return bodyContentType;
	}


    /**
     * SendMessage() Dependency Helper
     * 
     * @return content to add to the MimeBodyPart
     */
	@Override
	protected String getEmailContent() {

		return this.body;
	}


}
