package com.pls.sendmail.co;


import com.pls.sendmail.vo.MailerContent;
import com.pls.sendmail.vo.TextContent;

/**
 * Concrete MailCriteria implementation for requesting Plain Text
 * processing from the Mailer.
 * 
 * @author glawler
 *
 */
public class TextMailCriteria extends MailCriteria {

	
	private static final long serialVersionUID = -3843999065364684917L;


	/**
	 * This Constructor defaults the content type to TEXT/PLAIN
	 * Use a different concrete class or as a last resort 
	 * override this value after instantiation if the content 
	 * must be other than plain text. 
	 */
	public TextMailCriteria(){
		super(MailCriteria.ContentType.TEXT_PLAIN);
	}
	
	/**
	 * Convenience constructor for a simple text email, such as a notification from a subsystem or exception handler.
	 * Assumes text content in the body text parameter is plain text US ASCII characters 
	 * @param senderAddress
	 * @param recipientAddress
	 * @param subjectLine
	 * @param bodyText
	 * @throws Exception 
	 */
	public TextMailCriteria(String senderAddress, String recipientAddress, String subjectLine, String bodyText) {
		super(MailCriteria.ContentType.TEXT_PLAIN);

		setSender(senderAddress);
		addRecipient(recipientAddress);
		setSubjectLineText(subjectLine);
		setTextMailContent(bodyText);
		
	}

	/**
	 * SimpleMailCritieria is limited to sending plain text email
	 */
	@Override
	public ContentType getMailContentType() {
		return mailContentType;
	}

	/**
	 * Get the simple mail content as a string
	 * @return Text content for the message body part
	 */
	public String getTextMailContent(){
		return (String)super.getMailContent();
	}
	
	
	/**
	 * Set the Text body content for the message
	 * SimpleMailCriteria assumes that this content 
	 * contains only plain text characters.  Use 
	 * another message type to send other then plain
	 * text.
	 * @param mailContent
	 */
	public void setTextMailContent(String mailContent){
		super.setMailerContent(mailContent);
	}
	
	
	@Override
	public void setMailerContent(MailerContent contentObject) {
		if(contentObject != null){
			
			if(contentObject instanceof TextContent){
				
				setSubjectLineText( contentObject.getSubjectLine() );
				setTextMailContent( ((TextContent) contentObject).getContent()  );
				setTemplateName( null );

			}else {
				super.setMailerContent(contentObject);
			}
			
		}else{
			throw new IllegalArgumentException("Mailer Content cannot be null ");
		}
	}
}
