package com.pls.sendmail.templates;

import javax.xml.bind.annotation.XmlRootElement;

import com.pls.sendmail.vo.TemplateContent;

@XmlRootElement(name="carrierCapacity")
public class CarrierCapacityTemplate implements TemplateContent {

	private static final long serialVersionUID = -5655857690416850414L;
	
	private String carrierScac;
	private String contactEmail;
	private String equipmentType;
	private String equipmentTypeDesc;
	private String noOfTrucks;
	private String origin;
	private String preferredDest;
	private String startDateTime;
	private String endDateTime;
	private String maxWeight;
	private String origZone;
	private String destZone;
	private String carrierMcNum;

	/**
	 * @return the carrierScac
	 */
	public String getCarrierScac() {
		return carrierScac;
	}

	/**
	 * @param carrierScac the carrierScac to set
	 */
	public void setCarrierScac(String carrierScac) {
		this.carrierScac = carrierScac;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the equipmentType
	 */
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
	 * @return the equipmentTypeDesc
	 */
	public String getEquipmentTypeDesc() {
		return equipmentTypeDesc;
	}

	/**
	 * @param equipmentTypeDesc the equipmentTypeDesc to set
	 */
	public void setEquipmentTypeDesc(String equipmentTypeDesc) {
		this.equipmentTypeDesc = equipmentTypeDesc;
	}

	/**
	 * @return the noOfTrucks
	 */
	public String getNoOfTrucks() {
		return noOfTrucks;
	}

	/**
	 * @param noOfTrucks the noOfTrucks to set
	 */
	public void setNoOfTrucks(String noOfTrucks) {
		this.noOfTrucks = noOfTrucks;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the preferredDest
	 */
	public String getPreferredDest() {
		return preferredDest;
	}

	/**
	 * @param preferredDest the preferredDest to set
	 */
	public void setPreferredDest(String preferredDest) {
		this.preferredDest = preferredDest;
	}

	/**
	 * @return the startDateTime
	 */
	public String getStartDateTime() {
		return startDateTime;
	}

	/**
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * @return the endDateTime
	 */
	public String getEndDateTime() {
		return endDateTime;
	}

	/**
	 * @param endDateTime the endDateTime to set
	 */
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 * @return the maxWeight
	 */
	public String getMaxWeight() {
		return maxWeight;
	}

	/**
	 * @param maxWeight the maxWeight to set
	 */
	public void setMaxWeight(String maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * @return the origZone
	 */
	public String getOrigZone() {
		return origZone;
	}

	/**
	 * @param origZone the origZone to set
	 */
	public void setOrigZone(String origZone) {
		this.origZone = origZone;
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
	 * @return the carrierMcNum
	 */
	public String getCarrierMcNum() {
		return carrierMcNum;
	}

	/**
	 * @param carrierMcNum the carrierMcNum to set
	 */
	public void setCarrierMcNum(String carrierMcNum) {
		this.carrierMcNum = carrierMcNum;
	}

	public String getSubjectLine() {
		return "A new Capacity Posting has been created";
	}
	
	public String getTemplateName() {
		return this.getClass().getSimpleName();
	}
}
