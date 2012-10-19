package com.pls.sendmail.vo;

/**
 * Wrapper to allow String to be treated as
 * MailerContent
 *  
 * @author glawler
 *
 */
public class TextContent implements MailerContent {

	private static final long serialVersionUID = -3608922874443830392L;

	private String content;
	private String subject;
	
	public TextContent(String textContent, String subjectLine){
		this.content = textContent;
		this.subject = subjectLine;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public void setSubjectLine(String subject) {
		this.subject = subject;
	}

	@Override
	public String getSubjectLine() {
		return subject;
	}
	
}
