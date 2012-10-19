package com.pls.core.util;


import java.io.IOException;
import java.util.Properties;

public final class PropertyUtil {

	private static Properties properties;
	
	private static final String PROPERTIES_FILE = "/properties/pls.properties"; 
	
	private static final String DEFAULT_RECEIVER_ADDRESS = "DEFAULT_RECEIVER_ADDRESS";
	private static final String POST_CAPACITY_RECIPIENT = "POST_CAPACITY_RECIPIENT";
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

	public static final String getDefaultReceiverAddress() {
		return (String) properties.get(DEFAULT_RECEIVER_ADDRESS);
	}
	
	public static final String getDefaultSenderAddress() {
		return (String) properties.get(DEFAULT_SENDER_ADDRESS);
	}
	
	public static final String getPostCapacityRecipient() {
		String recipient = (String) properties.get(POST_CAPACITY_RECIPIENT);
		if (recipient == null || "".equals(recipient.trim())) {
			return getDefaultReceiverAddress();
		}
		
		return recipient;
	}
}