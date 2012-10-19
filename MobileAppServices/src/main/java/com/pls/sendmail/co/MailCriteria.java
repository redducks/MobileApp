package com.pls.sendmail.co;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pls.sendmail.vo.HTMLContent;
import com.pls.sendmail.vo.MailerContent;
import com.pls.sendmail.vo.TemplateContent;
import com.pls.sendmail.vo.TextContent;

/**
 * The MailCriteria objects are provided for the consumption of the 
 * Mailer in the ContentDelivery service.  As such, these classes 
 * provide convenience for the processing of Mail delivery but also
 * enforce an interface for requests of the Mailer.  One aspect of 
 * the request interface is the design assumption that the Mailer
 * can assume some processing requirements based on the concrete 
 * type of the MailCriteria object it receives.  As an example,
 * the MIME TYPE of the content is coupled to the concrete instance
 * of MailCriteria and this abstract instance requires that the 
 * content type be specified in order to instantiate an instance of
 * this class.  To avoid causing this to be completely inflexible, 
 * access modifiers are defined such that the concrete instance may 
 * override the content type by setting a different value after 
 * instantiation.  This should rarely be necessary. 
 * 
 * The previous Mailer represented each concrete type of message as a 
 * command to the Mailer.  This implementation represents each type of
 * message to be sent with a criteria.  The intent is that actual 
 * concrete messages will be created that extend the criteria objects
 * provided by the mailer.  An example is the use of XSL templates and
 * template classes by the Load messages. But even in this case, there
 * should probably be a LoadMailCriteria as an extension of 
 * the TemplateMailCriteria. 
 * 
 * @author glawler
 *
 */
public abstract class MailCriteria implements Serializable {

	
	private static final long serialVersionUID = -2676250082193923401L;

	public static final String DEFAULT_TEMPLATE_EXTENSION= ".xsl";
	
	/**
	 * enumerates supported priority values
	 */
	public enum Priority{
		LOW,
		NORMAL,
		HIGH
	}

	
	/**
	 * enumerates supported content types
	 * 
	 * use the toString() method to obtain Mime type
	 */
	public enum ContentType {
		TEXT_PLAIN("text/plain"),
		TEXT_HTML("text/html"),
		TEXT_XML("text/xml");
		
		private String mimeType;
		
		ContentType(String mimeType){
			this.mimeType = mimeType;
		}
		
		/**
		 * provides the corresponding Mime
		 * type for use in building the MimeMessage 
		 */
		@Override
		public String toString(){
			return this.mimeType;
		}
	}
	
	
	
	private MailerAddress sender = null;
	private List<MailerAddress> recipientList = null;
	private List<MailerAddress>  ccRecipientList = null;
	private List<MailerAddress>  bccRecipientList = null;
	
	private String subjectLineText = null;;
	
	protected ContentType mailContentType = null;;
	private Serializable mailContent = null;;
	
	private String templateName = null; 
	
	private List<MailAttachmentCriteria> attachments = null;
	
	private boolean sameSenderForFaxRecipients = false;
		
	private Priority priority = Priority.NORMAL;
	
	
	
	
	/**
	 * Force Content Type to be supplied for instantiation
	 */
	protected MailCriteria(ContentType contentType){
		mailContentType = contentType;
	}
	
	

	/**
	 * Factory constructor to return a concrete MailCriteria
	 * for the content provided.
	 * 
	 * Provided to help the criteria objects to be treated
	 * more generically.
	 * 
	 * @param content
	 * @return an appropriate concrete instance of MailCriteria
	 */
	public static MailCriteria getInstance(MailerContent content){

		if(content != null){

			if(content instanceof TextContent){
				
				TextMailCriteria critObj = new TextMailCriteria();
				critObj.setMailerContent(content);
				return critObj;
				
			}else if(content instanceof TemplateContent){
				
				TemplateMailCriteria critObj = new TemplateMailCriteria();
				critObj.setMailerContent(content);
				return critObj;
			}else if(content instanceof HTMLContent){
				
				HTMLMailCriteria critObj = new HTMLMailCriteria();
				critObj.setMailerContent(content);
				return critObj;
			}
		}
		return null;
		
	}
	
	/**
	 * Concrete types must declare the content type
	 * see {@link ContentType}
	 * @return recognized MIME Type of the mail content
	 */
	public abstract ContentType getMailContentType();

	
	/**
	 * Implement to allow all Criteria objects to be treated abstractly
	 * @return the content part of a MailCriteria object
	 */
	public MailerContent getMailerContent() {
		if(mailContent instanceof MailerContent){
			return (MailerContent)this.mailContent;
		}
		return null;
	}
	

	/**
	 * Implemented to allow all Criteria objects to be treated generically
	 * Override in the concrete classes to support the desired content.
	 * 
	 * This method throws exceptions for the developers when it receives 
	 * the various types of content, because if the correct Criteria object
	 * is used, the content would have been handled in the concrete class.
	 * 
	 * This will accept any Serializable type though, so if it the content
	 * in question isn't one of the defined types, but is Serializable, 
	 * the parameter content is assumed to be valid.  There is no guarantee
	 * that the MailerTask that receives the content will know what to do 
	 * with it though.
	 * 
	 */
	public void setMailerContent(MailerContent contentObject){
		
		if(contentObject != null){
			
			if(contentObject instanceof TextContent){
				throw new IllegalArgumentException("Wrong concrete criteria type, use TextMailCriteria ");
			}
			if(contentObject instanceof TemplateContent){
				throw new IllegalArgumentException("Wrong concrete criteria type, use TemplateMailCriteria ");
			}
			if(contentObject instanceof HTMLContent){
				throw new IllegalArgumentException("Wrong concrete criteria type, use HTMLMailCriteria ");
			}
			
			if(contentObject instanceof Serializable){
				this.setMailerContent((Serializable)contentObject);
			}
			
		}else{
			throw new IllegalArgumentException("Mailer Content cannot be null ");
		}
	}
	
	
	
	/**
	 * Override this method in the concrete classes only if
	 * necessary.  
	 * This description should provide a meaningful 
	 * description of the Mailer request for use in logging.
	 * @return String describing this Mailer request
	 */
	public String getMailInstanceDescription(){
		StringBuffer desc = new StringBuffer("");
		desc.append("Template: ");
		if(getTemplateName()!= null){
			desc.append(getTemplateName());
		}else{
			desc.append(" none specified ");
		}
		desc.append(" To: ");
		if(recipientList != null && !recipientList.isEmpty()){
			boolean more = false;
			for(MailerAddress recipient : recipientList){
				if(more){
					desc.append(", ");					
				}
				desc.append(recipient.getAddress());
				more = true;
			}
		}else{
			desc.append(" none specified ");
		}
		desc.append(" CC: ");
		if(ccRecipientList != null && !ccRecipientList.isEmpty()){
			boolean more = false;
			for(MailerAddress recipient : ccRecipientList){
				if(more){
					desc.append(", ");					
				}
				desc.append(recipient.getAddress());
				more = true;
			}
		}else{
			desc.append(" none specified ");
		}
		desc.append(" BCC: ");
		if(bccRecipientList != null && !bccRecipientList.isEmpty()){
			boolean more = false;
			for(MailerAddress recipient : bccRecipientList){
				if(more){
					desc.append(", ");					
				}
				desc.append(recipient.getAddress());
				more = true;
			}
		}else{
			desc.append(" none specified ");
		}
		desc.append(" From sender: ");
		if(sender != null && sender.getAddress() != null){
			desc.append(sender.getAddress());
		}else{
			desc.append(" unknown ");
		}
		desc.append(" Subject: ");
		if( subjectLineText != null ){
			desc.append(subjectLineText);
		}else{
			desc.append(" unknown ");
		}
		desc.append(" ");
		
		if(this.hasAttachment()){
			desc.append(" This request also includes ");
			desc.append( this.getAttachments().size() );
			desc.append(" attachment(s). ");
		}
		return desc.toString();
	}

	public MailerAddress getSender() {
		return sender;
	}
	
	/**
	 * Get the To recipients
	 * @return TO line recipients
	 */
	public List<MailerAddress> getRecipientList() {
		return recipientList;
	}

	/**
	 * Get the Copy To recipients
	 * @return CC line recipients
	 */
	public List<MailerAddress> getCcRecipientList() {
		return ccRecipientList;
	}

	/**
	 * Get the Blind Copy To list of recipients
	 * @return BCC line recipients
	 */
	public List<MailerAddress> getBccRecipientList() {
		return bccRecipientList;
	}



	/**
	 * Sets the address of the Sender (the FROM Addressee). 
	 * @see javax.mail.internet.MailerAddress
	 * @param sender
	 */
	public void setSender(MailerAddress sender) {
		this.sender = sender;
	}

	
	/**
	 * Sets the address of the Sender (the FROM Addressee).
	 * Convenience method to allow the caller to specify the sender
	 * as a String conforming to "user@host.domain".
	 * This method throws an AddressException if it cannot create 
	 * an MailerAddress container for the supplied parameter
	 * @see javax.mail.internet.MailerAddress
	 * @param sender
	 * @throws AddressException
	 */
	public void setSender(String sender){ // throws AddressException {
		this.sender = new MailerAddress(sender);
	}


	/** 
	 * Set or reset the entire list of Recipients (the TO Addressee).
	 * This methods requires caller to construct the MailerAddress objects.
	 * To add more recipients to an existing list, use:
	 * {@link MailCriteria#addRecipientMailerAddresses(List)}
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientList  
	 */
	public void setRecipientList(List<MailerAddress> recipientList) {
		this.recipientList = recipientList;
	}


	/**
	 * Convenience method to add a single recipient
	 * this method creates an MailerAddress container from the 
	 * supplied parameter and throws an AddressException 
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientAddress
	 * @throws AddressException
	 */
	public void addRecipient(String recipientAddress){ // throws AddressException{
		if(recipientList == null){
			recipientList = new ArrayList<MailerAddress>();
		}
		recipientList.add(new MailerAddress(recipientAddress));
	}

	/**
	 * Convenience method to add a single recipient
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientAddress
	 */
	public void addRecipient(MailerAddress recipientAddress) {
		if(recipientList == null){
			recipientList = new ArrayList<MailerAddress>();
		}
		recipientList.add( recipientAddress );
	}

	/**
	 * Convenience method to add a list of recipients
	 * this method creates an MailerAddress container for each
	 * supplied parameter address.
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientAddress
	 * @throws AddressException 
	 */
	public void addRecipients(List<String> recipientAddresses){ // throws AddressException{
		if(recipientAddresses != null && !recipientAddresses.isEmpty()){
			for(String recipientAddress : recipientAddresses){
				addRecipient(recipientAddress);
			}
		}
	}

	
	/**
	 * Convenience method to append additional recipients to the list. 
	 * @see javax.mail.internet.MailerAddress
	 * @param List<MailerAddress>
	 * @throws AddressException 
	 */
	public void addRecipientMailerAddresses(List<MailerAddress> recipientMailerAddresses){
		if(recipientMailerAddresses!= null){
			if(recipientList == null){
				this.setRecipientList(recipientMailerAddresses);
			}else{
				recipientList.addAll(recipientMailerAddresses);
			}
		}
	}

	
	

	/** 
	 * Set or reset the entire list of Copy-To Recipients (the CC Addressees).
	 * This methods requires caller to construct the MailerAddress objects.
	 * To add more  CC recipients to an existing list, use:
	 * {@link MailCriteria#addCcRecipientMailerAddresses(List)}
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientList  
	 */
	public void setCcRecipientList(List<MailerAddress> ccRecipientList) {
		this.ccRecipientList = ccRecipientList;
	}

	/**
	 * Convenience method to add a single CC recipient
	 * this method creates an MailerAddress container from the 
	 * supplied parameter and throws an AddressException 
	 * @see javax.mail.internet.MailerAddress
	 * @param ccRecipientAddress
	 * @throws AddressException
	 */
	public void addCcRecipient(String ccRecipientAddress){ // throws AddressException{
		if(ccRecipientList == null){
			ccRecipientList = new ArrayList<MailerAddress>();
		}
		ccRecipientList.add(new MailerAddress(ccRecipientAddress));
	}
	/**
	 * Convenience method to add a single CC recipient
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientAddress
	 */
	public void addCcRecipient(MailerAddress ccRecipientAddress) {
		if(ccRecipientList == null){
			ccRecipientList = new ArrayList<MailerAddress>();
		}
		ccRecipientList.add( ccRecipientAddress );
	}

	
	/**
	 * Convenience method to add a list of CC recipients
	 * this method creates an MailerAddress container for each
	 * supplied parameter address.
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientAddress
	 * @throws AddressException 
	 */
	public void addCcRecipients(List<String> ccRecipientAddresses) { // throws AddressException{
		if(ccRecipientAddresses != null && !ccRecipientAddresses.isEmpty()){
			for(String ccRecipientAddress : ccRecipientAddresses){
				addCcRecipient(ccRecipientAddress);
			}
		}
	}

	
	/**
	 * Convenience method to append additional CC recipients to the list. 
	 * @see javax.mail.internet.MailerAddress
	 * @param List<MailerAddress>
	 * @throws AddressException 
	 */
	public void addCcRecipientMailerAddresses(List<MailerAddress> ccRecipientMailerAddresses){
		if(ccRecipientMailerAddresses!= null){
			if(ccRecipientList == null){
				this.setCcRecipientList(ccRecipientMailerAddresses);
			}else{
				ccRecipientList.addAll(ccRecipientMailerAddresses);
			}
		}
	}
	


	/** 
	 * Set or reset the entire list of Blind-Copy-To Recipients (the BCC Addressees).
	 * This methods requires caller to construct the MailerAddress objects.
	 * To add more recipients to an existing list, use:
	 * {@link MailCriteria#addRecipientMailerAddresses(List)}
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientList  
	 */
	public void setBccRecipientList(List<MailerAddress> bccRecipientList) {
		this.bccRecipientList = bccRecipientList;
	}


	/**
	 * Convenience method to add a single BCC recipient
	 * this method creates an MailerAddress container from the 
	 * supplied parameter and throws an AddressException 
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientAddress
	 * @throws AddressException
	 */
	public void addBccRecipient(String bccRecipientAddress){ // throws AddressException{
		if(bccRecipientList == null){
			bccRecipientList = new ArrayList<MailerAddress>();
		}
		bccRecipientList.add(new MailerAddress(bccRecipientAddress));
	}

	/**
	 * Convenience method to add a single bcc recipient
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientAddress
	 */
	public void addBccRecipient(MailerAddress bccRecipientAddress) {
		if(bccRecipientList == null){
			bccRecipientList = new ArrayList<MailerAddress>();
		}
		bccRecipientList.add( bccRecipientAddress );
	}

	/**
	 * Convenience method to add a list of BCC recipients
	 * this method creates an MailerAddress container for each
	 * supplied parameter address.
	 * @see javax.mail.internet.MailerAddress
	 * @param recipientAddress
	 * @throws AddressException 
	 */
	public void addBccRecipients(List<String> bccRecipientAddresses) { // throws AddressException{
		if(bccRecipientAddresses != null && !bccRecipientAddresses.isEmpty()){
			for(String bccRecipientAddress : bccRecipientAddresses){
				addBccRecipient(bccRecipientAddress);
			}
		}
	}

	
	/**
	 * Convenience method to append additional recipients to the list. 
	 * @see javax.mail.internet.MailerAddress
	 * @param List<MailerAddress>
	 * @throws AddressException 
	 */
	public void addBccRecipientMailerAddresses(List<MailerAddress> bccRecipientMailerAddresses){
		if(bccRecipientMailerAddresses!= null){
			if(bccRecipientList == null){
				this.setBccRecipientList(bccRecipientMailerAddresses);
			}else{
				bccRecipientList.addAll(bccRecipientMailerAddresses);
			}
		}
	}


	/**
	 * Get text for the message subject line
	 * @return
	 */
	public String getSubjectLineText() {
		return subjectLineText;
	}


	/**
	 * Set the text for the message subject line
	 * @param subjectLineText
	 */
	public void setSubjectLineText(String subjectLineText) {
		this.subjectLineText = subjectLineText;
	}


	/**
	 * Get the message content
	 * Concrete message types use this method to 
	 * access the content in a more type specific way
	 * @return mailContent 
	 */
	protected Serializable getMailContent() {
		return mailContent;
	}


	/**
	 * Sets the content to be used for the body part of a 
	 * Mime mail message.  The concrete message criteria
	 * classes should provide a MimeType specific set method
	 * that provides public access to set the message content.
	 * 
	 * Usually these concrete methods will set the Mime ContentType
	 * at the same time they set the content. 
	 *  
	 * @param mailContent
	 */
	protected void setMailerContent(Serializable mailContent) {
		this.mailContent = mailContent;
	}



	/**
	 * Sets the MimeType that should be used for the content
	 * of the body part of a mime mail message
	 * 
	 * Concrete instances of the various message types set this
	 * MimeType by default for their intended purpose.
	 *  
	 * @param mailContentType
	 */
	public void setMailContentType(ContentType mailContentType) {
		this.mailContentType = mailContentType;
	}


	/**
	 * Get the Priority for delivery of this 
	 * Mail request.
	 * 
	 * When a message is added to the task queue, it
	 * is inserted according to the priority returned
	 * by this method.  
	 * 
	 * @return Priority value 
	 */
	public Priority getPriority() {
		return priority;
	}


	/**
	 * Set the Priority for delivery of this 
	 * Mail request.
	 * A default value will be used if none
	 * is supplied.
	 */
	public void setPriority(Priority priority) {
		if(priority != null){
			this.priority = priority;
		}
	}


	/**
	 * The name of the XSLT email template to 
	 * apply to the body content of the request
	 * 
	 * @return name to identify the template
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * Provide the name of an XSLT email template to apply to the body content 
	 * of the request before sending the message.
	 * 
	 *  By convention the Template name is the same as the message template concrete class name.
	 * These message template classes must implement com.eflatbed.contentdelivery.vo.mailer.MailerXMLContent
	 * 
	 * A ResourceHelper is responsible for loading this template at run time.
	 * This method checks to be sure the template name provided ends in .xsl, 
	 * since we expect these template names to file names for XSLT files.
	 * 
	 * @param templateName
	 */
	public void setTemplateName(String templateName) {
		if(templateName != null && !templateName.endsWith(DEFAULT_TEMPLATE_EXTENSION)){
			templateName = templateName + DEFAULT_TEMPLATE_EXTENSION;
		}
		this.templateName = templateName;
	}
	
		
	/**
	 * @return True when at least one attachment is found in the list
	 */
	public boolean hasAttachment(){
		return (this.attachments != null && !this.attachments.isEmpty());
	}


	/**
	 * @return the list containing the attachments
	 */
	public List<MailAttachmentCriteria> getAttachments() {
		return attachments;
	}

	
	/**
	 * Set the list of attachments
	 * @param attachments
	 */
	protected void setAttachments(List<MailAttachmentCriteria> attachments){
		this.attachments = attachments;
	}
	

	/**
	 * Convenience method to add a single attachment
	 * @param attachment
	 */
	public void addAttachment(MailAttachmentCriteria attachment) {
		if(this.attachments == null){
			this.attachments = new ArrayList<MailAttachmentCriteria>();
		}
		this.attachments.add(attachment);
	}

	public boolean isSameSenderForFaxRecipients() {
		return sameSenderForFaxRecipients;
	}



	public void setSameSenderForFaxRecipients(boolean sameSenderForFaxRecipients) {
		this.sameSenderForFaxRecipients = sameSenderForFaxRecipients;
	}

	
}
