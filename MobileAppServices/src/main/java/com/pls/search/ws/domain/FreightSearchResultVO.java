package com.pls.search.ws.domain;

import java.io.Serializable;
import java.util.Date;

import com.pls.core.ws.vo.ResultVO;

public class FreightSearchResultVO extends ResultVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -467331067835223305L;
	
	private Date loadDate;
	private String equipmentType;
	private Long loadId;
	private String originCity;
	private String originState;
	private String originZip;
	private String destCity;
	private String destState;
	private String destZip;
	private String contactEmail;
	private String contactPhone;
	private String instructions;
	

	public FreightSearchResultVO() {
		super(false);
	}
	
	public FreightSearchResultVO (Boolean success) {
		super(success);
	}

	public Date getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Long getLoadId() {
		return loadId;
	}

	public void setLoadId(Long loadId) {
		this.loadId = loadId;
	}

	public String getOriginCity() {
		return originCity;
	}

	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}

	public String getOriginState() {
		return originState;
	}

	public void setOriginState(String originState) {
		this.originState = originState;
	}

	public String getOriginZip() {
		return originZip;
	}

	public void setOriginZip(String originZip) {
		this.originZip = originZip;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	public String getDestState() {
		return destState;
	}

	public void setDestState(String destState) {
		this.destState = destState;
	}

	public String getDestZip() {
		return destZip;
	}

	public void setDestZip(String destZip) {
		this.destZip = destZip;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
}
