package com.pls.sendmail.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * This is the value object that allows every email 
 * to be saved for audit, troubleshooting, etc.
 * This contains the message that was queued to be sent by the
 * the MailerTaskManager.  It gets returned to the caller 
 * so that the caller may save the message in the database (or not) for
 * audit and tracking purposes,...things of that nature.
 * 
 */
public class SentMessageNotificationVO implements Serializable{

	private static final long serialVersionUID = 6709284727977125652L;

	private Date dateCreated;
	private String fromEmail;
	private List<String> toList;
	private List<String> ccList;
	private List<String> bccList;
	private String subject;
	private String description;
	private String messageContents;

	public SentMessageNotificationVO(){
	}

	

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getToList() {
		return toList;
	}

	public void setToList(List<String> toList) {
		this.toList = toList;
	}
	
	public List<String> getCcList() {
		return ccList;
	}

	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}
	
	public List<String> getBccList() {
		return bccList;
	}
	
	public void setBccList(List<String> bccList) {
		this.bccList = bccList;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessageContents() {
		return messageContents;
	}

	public void setMessageContents(String messageContents) {
		this.messageContents = messageContents;
	}
	
}
