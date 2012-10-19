package com.pls.carrier.model;

public enum CarrierCapacityStatus {
	
	PENDING ("P"),
	ACTIVE ("A"),
	INACTIVE ("I");

	private String code;

	private CarrierCapacityStatus(String code){
    	this.code = code;
    }
	
	public String getCode() {
		return code;
	}
	
}
