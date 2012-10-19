package com.pls.sendmail.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertyUtil {

	private static Properties properties;
	
	private static final String PROPERTIES_FILE = "/properties/sendmail.properties"; 
	
	private static final String MAIL_SERVER_HOST = "MAIL_SERVER_HOST";
	private static final String MAIL_SERVER_ENABLED = "MAIL_SERVER_ENABLED"; 
	private static final String MAIL_SERVER_FAX_DOMAIN = "MAIL_SERVER_FAX_DOMAIN";
	private static final String MAIL_SERVER_FAX_SENDER = "MAIL_SERVER_FAX_SENDER";
	private static final String DEFAULT_SENDER_ADDRESS = "DEFAULT_SENDER_ADDRESS";
	
	static {
		properties = new Properties();  
		try {
			properties.load(PropertyUtil.class.getResourceAsStream(PROPERTIES_FILE));
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	private PropertyUtil() {
		// No initialization to be done
	}
	
	public static final String getHost() {
		return (String) properties.get(MAIL_SERVER_HOST);
	}
	
	public static final Boolean isEnabled() {
		return Boolean.valueOf(properties.get(MAIL_SERVER_ENABLED).toString());
	}
	
	public static final String getFaxDomain() {
		return (String) properties.get(MAIL_SERVER_FAX_DOMAIN);
	}
	
	public static final String getFaxSender() {
		return (String) properties.get(MAIL_SERVER_FAX_SENDER);
	}
	
	public static final String getDefaultSenderAddress() {
		return (String) properties.get(DEFAULT_SENDER_ADDRESS);
	}
}
