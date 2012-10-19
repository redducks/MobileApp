package com.pls.sendmail.exceptions;



/**
 * Exception for the Mailer 
 */
public class MailerException extends RuntimeException {


	private static final long serialVersionUID = 3499053355471788062L;

	public MailerException() {
		super();
	}

	public MailerException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailerException(String message) {
		super(message);
	}

	public MailerException(Throwable cause) {
		super(cause);
	}
	
	
}
