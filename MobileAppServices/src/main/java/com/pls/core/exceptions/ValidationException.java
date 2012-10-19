package com.pls.core.exceptions;

public class ValidationException extends PLSException {

	private static final long serialVersionUID = 3718252607303094197L;
	
	public ValidationException(ExceptionCodes errorCode) {
		super(errorCode.getCode());
	}
	
	public ValidationException(ExceptionCodes errorCode, String message) {
		super(errorCode.getCode(), message);
	}
}
