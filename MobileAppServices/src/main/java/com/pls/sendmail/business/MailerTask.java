package com.pls.sendmail.business;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import com.pls.sendmail.business.impl.MailerTaskManager;
import com.pls.sendmail.co.MailAttachmentCriteria;
import com.pls.sendmail.co.MailCriteria;
import com.pls.sendmail.co.MailerAddress;
import com.pls.sendmail.exceptions.MailerException;
import com.pls.sendmail.util.PropertyUtil;
import com.pls.sendmail.vo.SentMessageNotificationVO;

/**
 * Abstract base task containing logic applicable to all MailerTasks.
 * 
 * This abstract class represents the interface for all concrete mail tasks and implements
 * the default behavior.  The default behavior contains all the logic required to construct
 * a javax.mail.Message and send this message to the mail host server for ultimately delivery.
 * In most cases this logic is reused by all types of tasks.
 * 
 * A mail task has to be constructed and submitted to the MailTaskManager.
 * Then when its time comes, the manager will call the tasks run() method.
 * The design of the mailer assumes that all processing intensive operations and
 * any operation prone to failure will be complete before scheduling a Task with
 * the Task Manager.  In this way, the invocation of the run() method will take 
 * the minimum amount of time, and will be likely to succeed.  
 * 
 * The InternetAddress objects are created when the Mailer calls prepareRecipients()
 * on each task.  PrepareRecipients converts the addresses and populates the lists
 * provided in this class.
 * 
 * Message specific content handling is supported by requiring extensions of this class to
 * implement a prepareContent() method.  This method is invoked prior to scheduling
 * a task with the TaskManager.
 * 
 * The run() method of this class filters the recipients to separate Fax
 * and Email and then delegates to a generic sendMessage() method that 
 * should be able to support the majority of messaging use cases.  If this is not 
 * acceptable for an extension of this class, override the runTask() method and 
 * provide case specific send behavior.    
 */
public abstract class MailerTask implements Serializable {

	private static final long serialVersionUID = -8726964367508860773L;

	private final static Logger LOG = Logger.getLogger(MailerTask.class.getName());

	public static final String EMPTY_STRING = "";

	
	private int taskPriority;
		
	private Session session;
    private Transport transport;
    
    
    private Date sentDate;
    
	private InternetAddress emailSender = null;
	private List<InternetAddress> emailRecipientList = null;
	private List<InternetAddress>  emailCCRecipientList = null;
	private List<InternetAddress>  emailBCCRecipientList = null;

	private InternetAddress faxSender = null;
	private List<InternetAddress> faxRecipientList = null;

	private SentMessageNotificationVO savedMessage;
   
    /**
     * Constructor
     * Sets the Priority value according to the value supplied 
     * in the criteria object for the request.
     * Sets the criteria reference for future use by the logic
     * of this base class.
     */
    protected MailerTask(MailCriteria mailCriteria){
    	taskPriority = mailCriteria.getPriority().ordinal();
    	setMailCriteria(mailCriteria);
    }
    
    /**
     * Constructor
     * Ignores the requested priority of the criteria object 
     * in favor of the parameter value
     * Sets the criteria reference for future use by the logic
     * of this base class.
     * @param initialPriority
     */
    protected MailerTask(MailCriteria mailCriteria, int initialPriority){
    	taskPriority = initialPriority;
    	setMailCriteria(mailCriteria);
    }
    

    /**
     * This base class does not hold any criteria
     * information but it does require access to the 
     * shared regions of the MailCriteria abstract base
     * class. 
     * Force the subclasses to hold the criteria
     * object reference.
     * It is intended for the subclasses
     * to hold the reference as an instance of the actual
     * concrete class to avoid the need for casting, etc.
     * @return MailCriteria for a concrete instance
     */
    protected abstract MailCriteria getMailCriteria();
    
    
    /**
     * This method is invoked by the constructor in order
     * to enforce the requirement that a concrete subclass 
     * of a MailerTask provide access to the shared regions
     * of the MailCriteria abstract base class.  
     *  
     * It is intended for the subclasses to hold the reference
     * as an instance of the actual concrete class to avoid 
     * the need for casting, etc.
     * @param MailCriteria for the concrete instance
     */
    protected abstract void setMailCriteria(MailCriteria mailCriteria);
    
    
    /**
     * The description is some task's descriptive text
     * designed to provide information for
     * monitoring and logging purposes.
     * First attempt is to get the description based on
     * the criteria the Task is executing against.
     * If all else fails at least try to return the 
     * class name of the instance.
     * @return the task's description
     * */
    public String getDescription(){
    	StringBuffer sb = new StringBuffer("");
    	sb.append(" Task is instance of ");
    	sb.append( this.getClass().getName() );
    	sb.append(". Task Description is: ");
    	if(getMailCriteria() != null && getMailCriteria().getMailInstanceDescription() != null){
    		sb.append( getMailCriteria().getMailInstanceDescription() );
    	}else{
        	sb.append(" unknown ");
    	}
    	return sb.toString();
    }

    
    /**
     * This method is invoked when the run() method is called by the MailerTaskManager 
     * 
     * This method separate email and fax recipients in 
     * preparation for the message to be sent.  The injected fax properties are
     * set via the Mailer MBean.
     * 
     * It should not be necessary to override this method.  
     * @throws MailerExcpetion
     */
    protected void filterFaxRecipients(String propFaxSender, String propFaxDomain) throws MailerException{

    	
    	if(propFaxSender != null){
    		try {
				this.faxSender = new InternetAddress( propFaxSender );
			} catch (AddressException e) {
	    		throw new MailerException(" Fax Sender address is not the proper format, check Mailer configuration. ", e);
			} 
    	}else{
    		throw new MailerException(" Fax Sender address is not available, check Mailer configuration. ");
    	}

    	if(propFaxDomain == null){
    		throw new MailerException(" Fax Domain is not available, check Mailer configuration. ");
    	}

    	
    	
    	faxRecipientList = new ArrayList<InternetAddress>();
    	
    	ArrayList<InternetAddress> emailRecipientsToBeRemoved = new ArrayList<InternetAddress>();
    	ArrayList<InternetAddress> emailCCRecipientToBeRemoved = new ArrayList<InternetAddress>();
    	ArrayList<InternetAddress> emailBCCRecipientToBeRemoved = new ArrayList<InternetAddress>();

    	if( this.getRecipientList() != null ){
    		for(InternetAddress recipient : getRecipientList() ){
    			if( recipient.getAddress().toLowerCase().indexOf( propFaxDomain ) >= 0 ) {
    				faxRecipientList.add(recipient);
    				emailRecipientsToBeRemoved.add( recipient );
    			}
    		}
		}


    	if( this.getCcRecipientList() != null ){
    		for(InternetAddress recipient : getCcRecipientList() ){
    			if( recipient.getAddress().toLowerCase().indexOf( propFaxDomain ) >= 0 ) {
    				faxRecipientList.add(recipient);
    				emailCCRecipientToBeRemoved.add( recipient );
    			}
    		}
		}

    	if( this.getBccRecipientList() != null ){
    		for(InternetAddress recipient : getBccRecipientList() ){
    			if( recipient.getAddress().toLowerCase().indexOf( propFaxDomain ) >= 0 ) {
    				faxRecipientList.add(recipient);
    				emailBCCRecipientToBeRemoved.add( recipient );
    			}
    		}
		}

    	if ( emailRecipientsToBeRemoved.size() > 0 ) {
    		for(InternetAddress recipient : emailRecipientsToBeRemoved ){
    			emailRecipientList.remove(recipient);
    		}
    	}
    	emailRecipientsToBeRemoved = null;
    	
    	if ( emailCCRecipientToBeRemoved.size() > 0 ) {
    		for(InternetAddress recipient : emailCCRecipientToBeRemoved ){
    			emailCCRecipientList.remove(recipient);
    		}
    	}
    	emailCCRecipientToBeRemoved = null;
    	
    	if ( emailBCCRecipientToBeRemoved.size() > 0 ) {
    		for(InternetAddress recipient : emailBCCRecipientToBeRemoved ){
    			emailBCCRecipientList.remove(recipient);
    		}
    	}
    	emailBCCRecipientToBeRemoved = null;
    	
    	
    }
    
    
	/**
     * This method is invoked by the Mailer when the task is being created 
     * 
     * This method converts the MailerAddress object from the criteria into
     * InternetAddress objects for use by the MailerTask.
     * 
     * @throws MailerExcpetion
     */
    public void prepareRecipients() throws MailerException{

    	
    	if(getMailCriteria() == null){
    		throw new MailerException(" Mail Critiria object is null. ");
    	}
    	
    	if (getMailCriteria().getSender() == null) {
    		getMailCriteria().setSender(PropertyUtil.getDefaultSenderAddress());
    	}
       	this.emailSender = convertAddress(getMailCriteria().getSender());

    	
    	emailRecipientList = new ArrayList<InternetAddress>();
    	emailCCRecipientList = new ArrayList<InternetAddress>();
    	emailBCCRecipientList = new ArrayList<InternetAddress>();

    	
    	if( getMailCriteria().getRecipientList() != null ){
    		for(MailerAddress recipient : getMailCriteria().getRecipientList() ){
   				// Checking address exists
   				if (recipient.getAddress() != null) {
   					emailRecipientList.add(  convertAddress(recipient)  );
   				}
    		}
		}


    	if( getMailCriteria().getCcRecipientList() != null ){
    		for(MailerAddress recipient : getMailCriteria().getCcRecipientList() ){
   				// Checking address exists
    			if (recipient.getAddress() != null) {
   					emailCCRecipientList.add(  convertAddress(recipient)  );
   				}
    		}
		}

    	if( getMailCriteria().getBccRecipientList() != null ){
    		for(MailerAddress recipient : getMailCriteria().getBccRecipientList() ){
   				// Checking address exists
   				if (recipient.getAddress() != null) {
   					emailBCCRecipientList.add(  convertAddress(recipient)  );
   				}
    		}
		}
    }
    
    
    /**
     * This method is invoked by the Session bean delegate 
     * when a Mailer request arrives at the service interface.
     * 
     * Implement this method in each concrete MailerTask to
     * perform all of the intensive processing for the message
     * in question, such as transforming the content with a 
     * style sheet.  
     * 
     * This will reduce the amount of processing time required 
     * for the Tasks to complete when they are run by the MailerTaskManager 
     * 
     */
    public abstract SentMessageNotificationVO prepareContent() throws MailerException;
    
    
    /**
     * Sets up common Task parameters and then delegates
     * to the concrete instance
     * @param session 
     * @param transport 
     * @throws MailerException 
     */
    public final void run( Session s, Transport t ) throws MailerException{
    	
    	if(s != null && t != null){
        	session = s;
        	transport = t;
    	}else{
    		StringBuffer errorMessage = new StringBuffer("");
    		errorMessage.append("Unable to run Mailer Task ");
    		errorMessage.append(this.getDescription());
    		errorMessage.append(". Mail Session and Transport parameters cannot be null. ");
    		
    		LOG.error(errorMessage.toString());
    		
    		throw new IllegalArgumentException(errorMessage.toString());
    	}
    	
    	//call to a delegate method to allow specific execution by the concrete instance
    	try {
    		
    		//separate Fax and Email
    		if(!getMailCriteria().isSameSenderForFaxRecipients())
    			filterFaxRecipients(getFaxSenderFromSession(), getFaxDomainFromSession());
    		
			runTask();
		} catch (MessagingException e) {

    		StringBuffer errorMessage = new StringBuffer("");
    		errorMessage.append("Exception while running Mailer Task ");
    		errorMessage.append(this.getDescription());

    		LOG.error(errorMessage, e);
    		
			throw new MailerException(errorMessage.toString(), e);
		}
    	
    	this.session = null;
    	this.transport = null;
    }

    
    /**
     * When the time comes and a task is run, this method 
     * will be invoked.  This default implementation just 
     * invokes the send method. 
     * {@link MailerTask#sendMessage()}
     *   
     * The run() method invoked by the manager is final, and 
     * so this delegate method is provided to handle a case 
     * where run behavior must be overridden in a concrete class.
     * 
     * The handling of all message types should be equivalent by 
     * this point in the processing and for the majority 
     * of the messages, there should be no reason to override
     * this method.  Message specific logic should be handled 
     * up-front in the prepareContent() method invocation.
     *   
     * If it is necessary to override at this point in the 
     * subclass, be sure to either call sendMessage() or 
     * re-implement the send logic.
     * 
     * @throws MessagingException 
     */
    protected void runTask() throws MessagingException{
    	sendMessage();
    }
    
    
    /**
     * Base implementation of the Send Message logic.
     * 
     * This method constructs and sends a MimeMessage.
     * 
     * It is intended that all concrete MailerTasks should be
     * able to use this implementation of sendMessage.  By the 
     * time a task is run, and this method is invoked, all of 
     * the message specific processing should have been completed
     * and the tasks should be equivalent with respect to what is
     * required to construct and send a MimeMessage.
     * 
     * A collection of delegate methods are used to inject the 
     * dependencies in order to allow a concrete subclass to 
     * override only a specific dependency injection.
     * See the methods commented with:
     *      "SendMessage() Dependency Helper" 
     * 
     * @throws MessagingException
     */
    protected void sendMessage( ) throws MessagingException{
    	
        // Construct and send the message
        MimeMessage msg = new MimeMessage(session);
        
        if(getEmailRecipients() != null){
        	msg.setRecipients(Message.RecipientType.TO, getEmailRecipients());
        }

        if ( getEmailCCRecipients() != null){
        	msg.setRecipients(Message.RecipientType.CC, getEmailCCRecipients());
        }
        
    	if ( getEmailBCCRecipients() != null){
    		msg.setRecipients(Message.RecipientType.BCC, getEmailBCCRecipients());
    	}
        
        msg.setFrom( getEmailSender() );
        
        msg.setSubject( getEmailSubject() );
        msg.setSentDate( getSentDate() );

        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent( getEmailContent(), getContentType());

    	Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp);       


        
        //check for attachments
    	if(hasAttachment()){
    		for(MailAttachmentCriteria ac : getAttachments()){

    			MimeBodyPart mbpAttachment = new MimeBodyPart();
    			if (ac.getAttachementContent() instanceof byte[]) {
					ByteArrayDataSource fds = new ByteArrayDataSource(
							(byte[]) ac.getAttachementContent(), ac
									.getAttachmentContentType().toString());
					mbpAttachment.setDataHandler(new DataHandler(fds));
				} else {
					mbpAttachment.setContent(ac.getAttachementContent(), ac
							.getAttachmentContentType().toString());
				}
				mbpAttachment.setFileName(ac.getAttachmentFilename());
                mp.addBodyPart(mbpAttachment);
    		}
    	}

        // add the Multipart to the message
        msg.setContent(mp);               

        // Even if there are no email recipients, there may be Fax recipients.
        // This guard ensures there are email recipients before trying to send.
        if ( msg.getAllRecipients() != null && msg.getAllRecipients().length > 0 ) {
        	msg.saveChanges();
        	transport.sendMessage(msg, msg.getAllRecipients());
        	LOG.debug( MailerTaskManager.DEBUG_SUBSYSTEM + " Sent e-mail " + getMailCriteria().getMailInstanceDescription());
        }
        
       
        // Always check if we need to send a Fax message.  This may occur either with or without email recipients
        if ( getFaxRecipients() != null && getFaxRecipients().length > 0 ) {
        	MimeMessage faxMsg = new MimeMessage(session);
      	
        	faxMsg.setSubject( getFaxSubject() );
        	faxMsg.setSentDate( getSentDate() );

        	MimeBodyPart faxMbp = new MimeBodyPart();
        	faxMbp.setContent( getFaxContent(), getContentType() );

        	Multipart faxMp = new MimeMultipart();
        	faxMp.addBodyPart(faxMbp);

        	//add the Multipart to the message
        	faxMsg.setContent(faxMp);
          
        	//set TO
        	faxMsg.setRecipients(Message.RecipientType.TO, getFaxRecipients());
      	
        	//set FROM
        	faxMsg.setFrom( getFaxSender() );
      	
        	// send message to fax provider
        	faxMsg.saveChanges();
        	transport.sendMessage( faxMsg, faxMsg.getAllRecipients() );
        	LOG.debug( MailerTaskManager.DEBUG_SUBSYSTEM + " Sent fax  " + getMailCriteria().getMailInstanceDescription());
          
        }
    }
    
    
    /**
     * Saves the email message to the MESSAGE value object.
     * 
     * Takes the lists from the criteria lists which is upstream
     * of the fax filter, which splits the recipient lists into
     * fax and email specific collections
     */
    protected SentMessageNotificationVO saveOutgoingMessage() {

    	
    	SentMessageNotificationVO messageVO = new SentMessageNotificationVO();
        
        messageVO.setDateCreated( getSentDate() );

        messageVO.setFromEmail( getMailCriteria().getSender().getAddress() );
        messageVO.setSubject( getMailCriteria().getSubjectLineText() );
        messageVO.setToList( listToStringList( getRecipientList() ));
        messageVO.setCcList( listToStringList( getCcRecipientList() ));
        messageVO.setBccList( listToStringList( getBccRecipientList() ));
        
        //description for persistence.  use template name if available, else message subject
        if(getMailCriteria().getTemplateName() != null){
        	messageVO.setDescription(getMailCriteria().getTemplateName());
        }else{
        	messageVO.setDescription(getMailCriteria().getSubjectLineText());
        }

        // call to the concrete classes get content method, at the end of prepareContent() 
        //  we assume that the content is ready, and this is what we want to return.
        messageVO.setMessageContents( getEmailContent() );

        return messageVO;
    }

    /**
     * SendMessage() Dependency Helper
     * @return sender's address
     */
    protected InternetAddress getEmailSender(){
    	
    	return this.emailSender;
    }

    
    public List<InternetAddress> getRecipientList(){
    	return this.emailRecipientList;
    }
    
    
    /**
     * SendMessage() Dependency Helper
     * 
     * @return TO recipients as InternetAddress[]
     */
    protected InternetAddress[] getEmailRecipients(){
    
    	if(emailRecipientList != null &&
    	          !emailRecipientList.isEmpty()   )
      	{
      		return listToArray( emailRecipientList ); 
      	}else{
      		return null;
      	}
    }

    
    public List<InternetAddress> getCcRecipientList() {

    	return this.emailCCRecipientList;
	}
    
    
    /**
     * SendMessage() Dependency Helper
     * 
     * @return CC recipients as InternetAddress[]
     */
    protected InternetAddress[] getEmailCCRecipients(){
        
    	if(emailCCRecipientList != null &&
  	          !emailCCRecipientList.isEmpty()   )
    	{
    		return listToArray( emailCCRecipientList ); 
    	}else{
    		return null;
    	}
    }

    
    public List<InternetAddress> getBccRecipientList() {

    	return this.emailBCCRecipientList;
	}

    
    /**
     * SendMessage() Dependency Helper
     * 
     * @return BCC recipients as InternetAddress[]
     */
    protected InternetAddress[] getEmailBCCRecipients(){
        
    	if(emailBCCRecipientList != null &&
    	          !emailBCCRecipientList.isEmpty()   )
    	{
    		return listToArray( emailBCCRecipientList ); 
    	}else{
    		return null;
    	}
    }

    /**
     * SendMessage() Dependency Helper
     * 
     * @return subject line 
     */
    protected String getEmailSubject(){
    	if(getMailCriteria() != null && getMailCriteria().getSubjectLineText() != null){
        	return getMailCriteria().getSubjectLineText();
    	}else{
    		return EMPTY_STRING;
    	}
    }

    
    /**
     * SendMessage() Dependency Helper
     * 
     * final because this method marks the Date on the first call, 
     * then returns the same value to subsequent callers.  
     * This keeps the audit log in sync with the stamp on the 
     * actual message and/or fax.
     * 
     * @return Date to time stamp the message
     */
    protected final Date getSentDate(){
    	if(sentDate == null){
    		sentDate = new Date(); 
    	}
    	return sentDate;
    }
    
    
    
    /**
     * SendMessage() Dependency Helper
     * 
     * @return content to add to the MimeBodyPart
     */
    protected abstract String getEmailContent();
    
    
    
    /**
     * SendMessage() Dependency Helper
     * 
     * @return sender's address
     */
    protected InternetAddress getFaxSender(){
    	return this.faxSender;
    }
    
    /**
     * SendMessage() Dependency Helper
     * 
     * @return TO recipients as InternetAddress[]
     */
    protected InternetAddress[] getFaxRecipients(){
    	
    	if(	faxRecipientList != null &&
    	          !faxRecipientList.isEmpty()   )
    	{
    		return listToArray(faxRecipientList); 
    	}else{
    		return null;
    	}
    }

    
    /**
     * SendMessage() Dependency Helper
     * 
     * This method uses the same subject as the email message.
     * 
     * @return subject line 
     */
    protected String getFaxSubject(){
    	if(getMailCriteria() != null && getMailCriteria().getSubjectLineText() != null){
        	return getMailCriteria().getSubjectLineText();
    	}else{
    		return EMPTY_STRING;
    	}
    }

    
    /**
     * SendMessage() Dependency Helper
     * 
     * This message returns the email content
     * 
     * @return content to add to the MimeBodyPart
     */
    protected String getFaxContent(){
    	return getEmailContent();
    }
    

    
    
    /**
     * SendMessage() Dependency Helper
     * 
     * The concrete Task must specify the content type
     * based on the MailCriteria it is implemented to
     * support.   
     *  
     * @return MimeType for the BodyPart content
     */
    protected abstract String getContentType();
    
    
    /**
     * SendMessage() Dependency Helper
     * 
     * @return boolean check for attachments
     */
    protected boolean hasAttachment(){
    	return getMailCriteria().hasAttachment();
    }
    
    
    /**
     * SendMessage() Dependency Helper
     * 
     * @return non-empty List of attachment criteria objects
     */
    protected List<MailAttachmentCriteria> getAttachments(){
    	return getMailCriteria().getAttachments();
    }
    

    /**
     * Helper method.
     * javax.mail objects need arrays, the criteria objects use collections.
     * Converts between a List and array, just to keep the code cleaner.
     * @param List<InternetAddress>
     * @return InternetAddress[]
     */
    protected InternetAddress[] listToArray(List<InternetAddress> addressList){
    	return (InternetAddress[]) addressList.toArray( new InternetAddress[addressList.size()] );
    }


    /**
     * Helper method.
     * the audit object needs List<String>, the criteria objects use collections of InternetAddress.
     * Converts between a types,  just to keep the code cleaner.
     * @param addressList
     * @return 
     */
    protected List<String> listToStringList(List<InternetAddress> addressList){
    	List<String> addresses = new ArrayList<String>();
    	if(addressList != null){
    		for(InternetAddress address : addressList){
    			addresses.add(new String(address.getAddress()));
    		}
    	}
		return addresses;
    }
    
    
    /**
     * Gets the Fax Sender property set on the Mailer Session
     * @return Fax Sender from the Mailer Configuration
     */
    private String getFaxSenderFromSession(){
    	if(session!= null ){
        	return this.session.getProperty( MailerConstants.PROP_FAX_SENDER );
    	}else{
    		return null;
    	}
    }
    
    
    private String getFaxDomainFromSession(){
    	if(session!= null ){
        	return this.session.getProperty( MailerConstants.PROP_FAX_DOMAIN );
    	}else{
    		return null;
    	}
    }
    
    
    /**
     * Returns the task priority.
     * 
     * This method is invoked on every task when scheduling
     * a task to be run.   
     * 
     * When adding to the task queue, The mail manager
     * will skip all the tasks with lower priority.
     * An Enumeration was created to represent the priority values.
     * @See MailCriteria.Priority
     * 
     * There are two ways to assign the priority of a message.
     * 1) Priority can (should) be set on the MailCriteria object used to 
     * request mailer processing.  If no value is specified explicitly,
     * a the default priority is assigned.
     * 2) When instantiating a MailerTask, the desired priority can be
     * passed as a parameter along with the MailCriteria object, causing the
     * parameter value to be used instead of the MailCriteria's value.   
     *   
     */
    public int getPriority() {
        return taskPriority;
    }

    /**
     * Invoked as event to notify the task that it is scheduled.
     * The default implementation does nothing
     */
    public void eventScheduled() {
    }
    
    
    /**
     * Accessor for the message object created during the send process.
     * This will be returned to the caller
     * @return
     */
    public SentMessageNotificationVO getSavedMessage(){
    	
    	if(savedMessage == null){
            savedMessage = saveOutgoingMessage();
    	}
    	return this.savedMessage;
    }

    
    /**
     * Change the application Address objects into InternetAddress objects.
     * @param address 
     * @return InternetAddress object for the given parameter
     */
    private InternetAddress convertAddress(MailerAddress address){
    	
    	try {
			if(address != null && address.getAddress() != null){
				if(address.getName()!= null){
					return new InternetAddress(address.getAddress(), address.getName()); 
				}else{
					return new InternetAddress(address.getAddress()); 
				}
			}else{
				throw new MailerException("Cannot convert null MailerAddress to InternetAddress.");
			}
		} catch (AddressException e) {
			String msg = new String("Address " + address.getAddress() + " cannot be parsed as a valid internet address.");
			LOG.error(msg, e);
			throw new MailerException(msg, e);
		} catch (UnsupportedEncodingException e) {
			StringBuffer msg = new StringBuffer("MailerAddress ");
			msg.append( address.getAddress() );
			msg.append( ", " );
			msg.append( address.getName() );
			msg.append(" is not in a recognizable format as a valid internet address.");
			LOG.error(msg.toString(), e);
			throw new MailerException(msg.toString(), e);
		}
    }
    
    
}
