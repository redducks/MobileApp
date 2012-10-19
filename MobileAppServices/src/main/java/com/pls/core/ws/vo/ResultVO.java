package com.pls.core.ws.vo;

import java.io.Serializable;

public class ResultVO implements Serializable {

	private Boolean success;

	public ResultVO() {
		this.success = false;
	}
	
	public ResultVO (Boolean success) {
		this.success = success;
	}
	/**
	 * @return the success
	 */
	public Boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
