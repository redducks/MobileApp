package com.pls.sendmail.co;

import java.io.Serializable;

import com.pls.sendmail.vo.MailerContent;
import com.pls.sendmail.vo.TemplateContent;

/**
 * Concrete instance of a MailCriteria object. 
 * 
 * This class represents Mailer requests that start as a
 * raw XML document and are converted into an HTML message
 * using an XSLT style sheet as a template for creating the
 * email.   
 * 
 * This criteria object assumes that the target representation is
 * HTML.
 *  
 * The raw data XML document is an implementation of MailerTemplateContent
 * and contains the data in an object form.  This interface is a marker
 * denoting that the supplied object tree is annotated for use with the
 * JAXB Marshaller.
 *  
 * The raw data XML objects are marshalled to an XML document right before 
 * transformation to HTML occurs.
 * 
 * @author glawler
 */
public class TemplateMailCriteria extends MailCriteria {

	

	private static final long serialVersionUID = -3513352540054355354L;


	/**
	 * This Constructor defaults the content type to TEXT/HTML
	 * After the transformation template is applied, the content will be HTML.
	 * Use a different concrete class or as a last resort 
	 * override this value after instantiation if the content 
	 * must be other than valid HTML. 
	 */
	public TemplateMailCriteria() {
		super(MailCriteria.ContentType.TEXT_HTML);
	}

	
	
	/**
	 * This concrete instance allows the content type to 
	 * be overriden, but it's not recommended
	 */
	@Override
	public ContentType getMailContentType() {
		return mailContentType;
	}

	
	/**
	 * Provide the XML source to be included as message content 
	 * without transformation, the content will be sent as 
	 * TEXT_XML
	 * 
	 * @param xmlSource
	 */
	protected void setXMLContent(byte[] xmlSource){
		this.setMailerContent(xmlSource);
		this.setMailContentType(MailCriteria.ContentType.TEXT_XML);
	}
	
	
	/**
	 * Retrieve the JAXB annotated object tree.
	 * @return message content payload cast back to TemplateContent
	 */
	public TemplateContent getTemplateContent(){
		return (TemplateContent)this.getMailContent();
	}
	

	/**
	 * Cast to serializable and store the content
	 * @param templateContent
	 */
	public void setTemplateContent(TemplateContent templateContent) {
		super.setMailerContent((Serializable)templateContent);
	}




	@Override
	public void setMailerContent(MailerContent contentObject) {

		if(contentObject != null){
			
			if(contentObject instanceof TemplateContent){
				
				setSubjectLineText( contentObject.getSubjectLine() );
				setTemplateName( contentObject.getClass().getSimpleName() );
				setTemplateContent((TemplateContent)contentObject);
			}else{
				super.setMailerContent(contentObject);
			}
		}else{
			throw new IllegalArgumentException("Mailer Content cannot be null ");
		}
	}
	
	
}
