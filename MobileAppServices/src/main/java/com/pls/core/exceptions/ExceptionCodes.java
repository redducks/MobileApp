package com.pls.core.exceptions;

public enum ExceptionCodes {

	UNKNOWN_ERROR(1000),
	CARRIER_CONTACT_REQUIRED(1001),
	EQUIPMENT_TYPE_REQUIRED(1002),
	SCAC_OR_MCNUM_REQUIRED(1003),
	INVALID_WEIGHT(1004),
	INVALID_NO_OF_TRUCKS(1005),
	ORIGIN_REQUIRED(1006),
	DESTINATION_REQUIRED(1007),
	START_DATE_REQUIRED(1008),
	INVALID_START_DATE(1009),
	STATUS_REQUIRED(1010),
	INVALID_STATUS(1011),
	ERROR_SENDING_EMAIL(1012);
	
	private int code;

	private ExceptionCodes(int code){
    	this.code = code;
    }
	
	public int getCode() {
		return code;
	}
}
