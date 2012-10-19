package com.pls.core.exceptions;

public class PLSException extends Exception {
	
	private int errorCode;
	private String message;
	private Throwable cause;
	
	public PLSException(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public PLSException(int errorCode, Throwable cause) {
		this.errorCode = errorCode;
		this.cause = cause;
	}
	
	public PLSException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public PLSException(int errorCode, String message, Throwable cause) {
		this.errorCode = errorCode;
		this.message = message;
		this.cause = cause;
	}
	
	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}
	
	/**
	 * @param cause the cause to set
	 */
	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	
}
