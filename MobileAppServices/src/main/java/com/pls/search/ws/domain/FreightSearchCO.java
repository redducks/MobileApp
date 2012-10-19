package com.pls.search.ws.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="carrierCapacity")
public class FreightSearchCO implements Serializable {

	private static final long serialVersionUID = 2732952970020318312L;

	private String scac;
	
	private String mcNumber;
	
	private String carrierContact;
	
	private String equipmentType;
	
	private Long maxWeight;
	
	private Long noOfTrucks;
	
	private Date availableDate;
	
	private String originCity;
	
	private String originState;
	
	private String originZip;
	
	private String originCountry;
	
	private String originZone;
	
	private String destCity;
	
	private String destState;
	
	private String destZip;
	
	private String destCountry;
	
	private String destZone;
	
	private String status;

	/**
	 * @return the scac
	 */
	public String getScac() {
		return scac;
	}

	/**
	 * @param scac the scac to set
	 */
	public void setScac(String scac) {
		this.scac = scac;
	}

	/**
	 * @return the mcNumber
	 */
	public String getMcNumber() {
		return mcNumber;
	}

	/**
	 * @param mcNumber the mcNumber to set
	 */
	public void setMcNumber(String mcNumber) {
		this.mcNumber = mcNumber;
	}

	/**
	 * @return the carrierContact
	 */
	@XmlElement(required=true)
	public String getCarrierContact() {
		return carrierContact;
	}

	/**
	 * @param carrierContact the carrierContact to set
	 */
	public void setCarrierContact(String carrierContact) {
		this.carrierContact = carrierContact;
	}

	/**
	 * @return the equipmentType
	 */
	@XmlElement(required=true)
	public String getEquipmentType() {
		return equipmentType;
	}

	/**
	 * @param equipmentType the equipmentType to set
	 */
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	/**
	 * @return the maxWeight
	 */
	@XmlElement(required=true)
	public Long getMaxWeight() {
		return maxWeight;
	}

	/**
	 * @param maxWeight the maxWeight to set
	 */
	public void setMaxWeight(Long maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * @return the noOfTrucks
	 */
	@XmlElement(required=true)
	public Long getNoOfTrucks() {
		return noOfTrucks;
	}

	/**
	 * @param noOfTrucks the noOfTrucks to set
	 */
	public void setNoOfTrucks(Long noOfTrucks) {
		this.noOfTrucks = noOfTrucks;
	}

	/**
	 * @return the availableDate
	 */
	@XmlElement(required=true)
	public Date getAvailableDate() {
		return availableDate;
	}

	/**
	 * @param availableDate the availableDate to set
	 */
	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	/**
	 * @return the originCity
	 */
	public String getOriginCity() {
		return originCity;
	}

	/**
	 * @param originCity the originCity to set
	 */
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}

	/**
	 * @return the originState
	 */
	public String getOriginState() {
		return originState;
	}

	/**
	 * @param originState the originState to set
	 */
	public void setOriginState(String originState) {
		this.originState = originState;
	}

	/**
	 * @return the originZip
	 */
	public String getOriginZip() {
		return originZip;
	}

	/**
	 * @param originZip the originZip to set
	 */
	public void setOriginZip(String originZip) {
		this.originZip = originZip;
	}
	
	/**
	 * @return the originCountry
	 */
	public String getOriginCountry() {
		return originCountry;
	}

	/**
	 * @param originCountry the originCountry to set
	 */
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	/**
	 * @return the originZone
	 */
	public String getOriginZone() {
		return originZone;
	}

	/**
	 * @param originZone the originZone to set
	 */
	public void setOriginZone(String originZone) {
		this.originZone = originZone;
	}

	/**
	 * @return the destCity
	 */
	public String getDestCity() {
		return destCity;
	}

	/**
	 * @param destCity the destCity to set
	 */
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	/**
	 * @return the destState
	 */
	public String getDestState() {
		return destState;
	}

	/**
	 * @param destState the destState to set
	 */
	public void setDestState(String destState) {
		this.destState = destState;
	}

	/**
	 * @return the destZip
	 */
	public String getDestZip() {
		return destZip;
	}

	/**
	 * @param destZip the destZip to set
	 */
	public void setDestZip(String destZip) {
		this.destZip = destZip;
	}

	/**
	 * @return the destCountry
	 */
	public String getDestCountry() {
		return destCountry;
	}

	/**
	 * @param destCountry the destCountry to set
	 */
	public void setDestCountry(String destCountry) {
		this.destCountry = destCountry;
	}

	/**
	 * @return the destZone
	 */
	public String getDestZone() {
		return destZone;
	}

	/**
	 * @param destZone the destZone to set
	 */
	public void setDestZone(String destZone) {
		this.destZone = destZone;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	@XmlElement(required=true)
	public void setStatus(String status) {
		this.status = status;
	}
}
