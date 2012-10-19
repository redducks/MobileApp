package com.pls.sendmail.co;

import java.io.Serializable;

/**
 * The MailAttachmentCriteria object is provided for the encapsulation 
 * of all the required components for passing an attachment to the
 * mailer.  
 * Implemented as an independent  class to support collections of 
 * attachments in the MailCriteria objects.
 * 
 * It's probably best to provide Convenience methods for each 
 * type of attachment that is expected. 
 * 
 * 
 * @author glawler
 *
 */
public class MailAttachmentCriteria implements Serializable {

	
	private static final long serialVersionUID = -1091079593509206268L;


	/**
	 * enumerates supported attachment types
	 */
	public enum AttachmentType {
		TEXT_PLAIN("text/plain"),
		TEXT_HTML("text/html"),
		APPLICATION_PDF("application/pdf"),
		APPLICATION_EXCEL("application/xls"),
		APPLICATION_RTF("application/rtf"),
		APPLICATION_XML("application/xml"),
		APPLICATION_ZIP("application/zip");
		
		private String mimeType;
		
		AttachmentType(String mimeType){
			this.mimeType = mimeType;
		}
		
		public String toString(){
			return this.mimeType;
		}
	}
	
	protected AttachmentType attachmentContentType;
	protected Serializable attachementContent;
	protected String attachmentFilename;


	public MailAttachmentCriteria(){
	}
	
	public MailAttachmentCriteria(AttachmentType contentType,
			                      Serializable content, 	
			                      String filename ){
		
		this.attachmentContentType = contentType;
		this.attachementContent = content;
		this.attachmentFilename = filename;
	}
	
	
	/**
	 * AttachmentCriteria for a PDF file
	 * 
	 * Convenience for specific file type attachments
	 * @param pdfBytes
	 * @param filename
	 * @return criteria object for the desired attachment
	 */
	public static MailAttachmentCriteria createPDFAttachment( byte[] pdfBytes, String filename	){
		return new MailAttachmentCriteria( AttachmentType.APPLICATION_PDF, pdfBytes, filename );
	}
	
	
	public AttachmentType getAttachmentContentType() {
		return attachmentContentType;
	}


	public void setAttachmentContentType(AttachmentType attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}


	public Serializable getAttachementContent() {
		return attachementContent;
	}


	public void setAttachementContent(Serializable attachementContent) {
		this.attachementContent = attachementContent;
	}


	public String getAttachmentFilename() {
		return attachmentFilename;
	}


	public void setAttachmentFilename(String attachmentFilename) {
		this.attachmentFilename = attachmentFilename;
	}


	
	
}
