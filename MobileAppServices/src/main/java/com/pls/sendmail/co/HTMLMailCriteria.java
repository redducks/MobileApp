package com.pls.sendmail.co;

import com.pls.sendmail.vo.HTMLContent;
import com.pls.sendmail.vo.MailerContent;

/**
 * Concrete MailCriteria implementation for requesting HTML
 * message processing from the Mailer.
 * 
 * @author glawler
 *
 */
public class HTMLMailCriteria extends MailCriteria {

	
	private static final long serialVersionUID = 6865432272989860386L;


	/**
	 * This Constructor defaults the content type to TEXT/HTML
	 * Use a different concrete class or as a last resort 
	 * override this value after instantiation if the content 
	 * must be other than HTML. 
	 */
	public HTMLMailCriteria() {
		super(MailCriteria.ContentType.TEXT_HTML);
	}

	
	/**
	 * Convenience constructor for an HTML email.
	 * Assumes text content in the body text parameter is valid HTML 
	 * @param senderAddress
	 * @param recipientAddress
	 * @param subjectLine
	 * @param bodyText
	 * @throws Exception 
	 */
	public HTMLMailCriteria(String senderAddress, String recipientAddress, String subjectLine, String htmlMessageBody){// throws Exception{
		this();

		setSender(senderAddress);
		addRecipient(recipientAddress);
		setSubjectLineText(subjectLine);
		setHTMLMailContent(htmlMessageBody);
	}
	
	
	
	/**
	 * This concrete instance allows the content type to 
	 * be overriden, but it's not recommended
	 */
	@Override
	public ContentType getMailContentType() {
		return  mailContentType;
	}




	/**
	 * Return the HTML message body content
	 * @return HTML body content 
	 */
	public String getHTMLMailContent() {
		return (String) this.getMailContent();
	}


	/**
	 * Set the HTML content to use as the message body.
	 * @param mailContent String containing HTML markup
	 */
	public void setHTMLMailContent(String mailContent) {
		this.setMailerContent(mailContent);
		this.setMailContentType(MailCriteria.ContentType.TEXT_HTML);
	}
	
	
	
	@Override
	public void setMailerContent(MailerContent contentObject) {
		if(contentObject != null){
			
			if(contentObject instanceof HTMLContent){
				
				setSubjectLineText( contentObject.getSubjectLine() );
				setHTMLMailContent( ((HTMLContent) contentObject).getContent()  );
				setTemplateName( null );
			}else {
				super.setMailerContent(contentObject);
			}
			
		}else{
			throw new IllegalArgumentException("Mailer Content cannot be null ");
		}
	}
	
	
}
