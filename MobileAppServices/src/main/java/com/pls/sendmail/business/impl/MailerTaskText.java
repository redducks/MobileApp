package com.pls.sendmail.business.impl;

import org.apache.log4j.Logger;

import com.pls.sendmail.business.MailerTask;
import com.pls.sendmail.co.MailCriteria;
import com.pls.sendmail.co.TextMailCriteria;
import com.pls.sendmail.exceptions.MailerException;
import com.pls.sendmail.vo.SentMessageNotificationVO;

/**
 * Mailer Task corresponding to the SimpleMailCriteria object
 * @author glawler
 *
 */
public class MailerTaskText extends MailerTask {

	private static final long serialVersionUID = 3249175962900513924L;

	private final static Logger LOG = Logger.getLogger(MailerTaskText.class.getName());

	private TextMailCriteria criteria;
	
	protected String body;
	protected String bodyContentType;
	
	/**
	 * Must supply a SimpleMailCriteria reference to create this task
	 * @param mailCriteria
	 */
	public MailerTaskText(TextMailCriteria mailCriteria) {
		super(mailCriteria);
	}

	/**
	 * Provide access to the criteria object for the request
	 * according to the common MailCriteria interface  
	 */
	@Override
	protected MailCriteria getMailCriteria() {
		return this.criteria;
	}

	
	/**
	 * Implemented to support the base class constructor.
	 * 
	 * The abstract base class enforces existence of a MailCriteria
	 * object by setting the reference via this method 
	 */
	@Override
	protected void setMailCriteria(MailCriteria mailCriteria) {
		this.criteria = (TextMailCriteria) mailCriteria;
	}
	
	
	
	/**
	 * Prepares the mailer request for processing by the
	 * base send logic implemented in the MailerTask.
	 * 
	 * For simple plain text email, the only preparation is to 
	 * cast the content to String and set the MimeType.
	 * 
	 */
	@Override
	public SentMessageNotificationVO prepareContent() throws MailerException{

		if(criteria != null){
			
			if(criteria.getTextMailContent()!= null && criteria.getMailContentType() != null){
		
				this.body = criteria.getTextMailContent();
				this.bodyContentType = criteria.getMailContentType().toString();
			}else{
				//no content was provided
				//  it may not be desirable, but it is legal to send a message with no body.
				this.body = "";
				this.bodyContentType = MailCriteria.ContentType.TEXT_PLAIN.toString();
				LOG.info("MailerTaskSimple prepared a message with no body content. Message description:");
				LOG.info( criteria.getMailInstanceDescription() );
			}
		
			return saveOutgoingMessage();
		}else{
			throw new MailerException("Unable to prepare a mailer request due to null criteria object.");
		}
	}



	/**
     * SendMessage() Dependency Helper
     * 
     * Implemented to provide the correct ContentType value for 
     * the plain text content intended to be handled by this 
     * task.   
     *  
     * @return MimeType for the BodyPart content
	 */
	@Override
	protected String getContentType() {
		return this.bodyContentType;
	}

	/**
     * SendMessage() Dependency Helper
     * 
     * Implemented to provide access to the character 
     * data content for the plain text content intended 
     * to be handled by this task.   
     *  
     * @return MimeType for the BodyPart content
	 */
	@Override
	protected String getEmailContent() {
		return this.body;
	}



}
