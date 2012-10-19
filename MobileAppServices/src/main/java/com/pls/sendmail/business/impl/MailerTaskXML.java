package com.pls.sendmail.business.impl;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;

import com.pls.sendmail.business.MailerTask;
import com.pls.sendmail.co.MailCriteria;
import com.pls.sendmail.co.TemplateMailCriteria;
import com.pls.sendmail.exceptions.MailerException;
import com.pls.sendmail.vo.SentMessageNotificationVO;
import com.pls.sendmail.xml.TransformerProxy;


/**
 * Concrete mailer task.
 * Implements e-mail message where the data is in XML format,
 * gets formatted to HTML using a transformer.
 */
public final class MailerTaskXML extends MailerTask {

	private static final long serialVersionUID = 1153671632550380755L;

	private final static Logger LOG = Logger.getLogger(MailerTaskXML.class.getName());
	
	private TemplateMailCriteria criteria;
	
	private String transformedContent;
	private String contentType;
	
    /**
     * Must supply an TemplateMailCriteria reference to create this task. 
     * @param criteriaObject
     */
    public MailerTaskXML(TemplateMailCriteria criteriaObject){
    	super(criteriaObject);
    }

    
	@Override
	public SentMessageNotificationVO prepareContent()  throws MailerException{
        try {

        	if(criteria == null){
        		throw new MailerException("Criteria object is null in prepareContent().");
        	}
        	
        	transformedContent = TransformerProxy.transformToHTML(criteria.getTemplateName(), criteria.getTemplateContent());
        	contentType = criteria.getMailContentType().toString();
            
			return saveOutgoingMessage();
        	
        } catch (TransformerException te) {
        	String message = new String("XSLT Transformation error for e-mail while sending " + criteria.getMailInstanceDescription());
            LOG.error(message, te);
            throw new MailerException(message, te);
        } catch (Throwable te) {
        	String message = new String("General error in the mailer while sending " + criteria.getMailInstanceDescription());
        	LOG.error(message, te);
            throw new MailerException(message, te);
        }
	}

	

    /**
     * SendMessage() Dependency Helper
     * 
     * This concrete Task is intended for 
     * sending transformed or non-transformed 
     * XML content 
     *  
     * @return MimeType for the BodyPart content
     */
	@Override
	protected String getContentType() {
		
		return this.contentType;
	}


    /**
     * SendMessage() Dependency Helper
     * 
     * @return content to add to the MimeBodyPart
     */
	@Override
	protected String getEmailContent() {

		return this.transformedContent;
	}
    
    

	/**
	 * Provided to satisfy the super class.
	 */
	@Override
	public MailCriteria getMailCriteria() {
		return this.criteria;
	}

	/**
	 * Provided to satisfy the super class.
	 */
	@Override
	protected void setMailCriteria(MailCriteria mailCriteria) {
		this.criteria = (TemplateMailCriteria)mailCriteria;
	}
    
    



}
