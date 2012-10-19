package com.pls.sendmail.co;

import java.io.Serializable;

/**
 * Generic Address class for the application.
 * This mimics the InternetAddress class that is ultimately
 * used by the mailer.  This one is kinder though and
 * doesn't throw javax.mail exceptions all over the place.
 * 
 * The Mailer will convert these objects into InteretAddress
 * instances when each MailTask is prepared.
 * 
 * @see javax.mail.internet.InternetAddress
 * 
 * @author glawler
 *
 */
public class MailerAddress implements Serializable {

	private static final long serialVersionUID = 2854723096846175126L;

	
	private String name;
	private String address;
	
	

	public MailerAddress() {
		super();
	}
	
	/**
	 * Construct with just an address
	 * @param address
	 */
	public MailerAddress(String address) {
		super();
		this.name = null;
		this.address = address;
	}
	
	
	/**
	 * Construct with an address and personalization
	 * @param Internet format address
	 * @param personal or descriptive name 
	 */
	public MailerAddress(String address, String name) {
		super();
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	
}
