package com.pls.core.ws;

import javax.xml.soap.SOAPFault;
import javax.xml.ws.WebFault;

import com.pls.core.exceptions.PLSException;

@WebFault(name="WebServiceException")
public class WebServiceException extends Exception {

	private int errorCode;
	private String message;
	private Throwable cause;
	
	public WebServiceException(PLSException pe) {
		this.errorCode = pe.getErrorCode();
		this.message = pe.getMessage();
		this.cause = pe.getCause();
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param cause the cause to set
	 */
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
}
