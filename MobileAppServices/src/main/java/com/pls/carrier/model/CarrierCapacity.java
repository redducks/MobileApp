package com.pls.carrier.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name="CARRIER_CAPACITY")
@SequenceGenerator(name="CARRIER_CAPACITY_SEQ", sequenceName="CARRIER_CAPACITY_SEQ", allocationSize=1)
public class CarrierCapacity implements Serializable {

	private static final long serialVersionUID = -5669912812902962859L;

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE,  generator="CARRIER_CAPACITY_SEQ")
	@Column(name = "CARRIER_CAPACITY_ID")
	private Long carrierCapacityId;
	
	@Column(name = "CC_ID")
	private Long ccId;
	
	@Column(name = "CARRIER_ORG_ID")
	private Long carrierOrgId;
	
	@Column(name = "CONTAINER_CD")
	private String containerCd;
	
	@Column(name = "NO_OF_TRUCKS")
	private Long noOfTrucks;
	
	@Column(name = "PREF_COMMODITY")
	private String prefCommodity;
	
	@Column(name = "MAX_WEIGHT")
	private Long maxWeight;
	
	@Column(name = "ORIG_CITY")
	private String origCity;
	
	@Column(name = "ORIG_STATE")
	private String origState;
	
	@Column(name = "ORIG_ZIP")
	private String origZip;
	
	@Column(name = "ORIG_COUNTRY")
	private String origCountry;
	
	@Column(name = "AVAIL_START_DATE")
	private Date availStartDate;
	
	@Column(name = "AVAIL_END_DATE")
	private Date availEndDate;
	
	@Column(name = "CONTACT_PERSON_ID")
	private Long contactPersonId;
	
	@Column(name = "PREF_DEST_CITY")
	private String prefDestCity;
	
	@Column(name = "PREF_DEST_STATE")
	private String prefDestState;
	
	@Column(name = "PREF_DEST_ZIP")
	private String prefDestZip;
	
	@Column(name = "PREF_DEST_COUNTRY")
	private String prefDestCountry;
	
	@Column(name = "ORIG_ZONE_ID")
	private Long origZoneId;
	
	@Column(name = "PREF_DEST_ZONE_ID")
	private Long prefDestZoneId;
	
	private String status = "A";
	
	@Column(name = "DATE_CREATED")
	private Date dateCreated = new Date();
	
	@Column(name = "CREATED_BY")
	private Long createdBy = 0L;
	
	@Column(name = "DATE_MODIFIED")
	private Date dateModified = new Date();
	
	@Column(name = "MODIFIED_BY")
	private Long modifiedBy = 0L;
	
	@Version
	private int version = 1;
	
	@Column(name = "ORG_USER_ID")
	private Long orgUserId;
	
	@Column(name = "CONTACT_EMAIL_ADDR")
	private String contactEmailAddr;
	
	@Transient
	private String carrierName;
	
	@Transient
	private String carrierScac;
	
	@Transient
	private String contactFirstName;
	
	@Transient
	private String contactLastName;
	
	@Transient
	private String contactPhone;

	/**
	 * @return the carrierCapacityId
	 */
	public Long getCarrierCapacityId() {
		return carrierCapacityId;
	}

	/**
	 * @param carrierCapacityId the carrierCapacityId to set
	 */
	public void setCarrierCapacityId(Long carrierCapacityId) {
		this.carrierCapacityId = carrierCapacityId;
	}

	/**
	 * @return the ccId
	 */
	public Long getCcId() {
		return ccId;
	}

	/**
	 * @param ccId the ccId to set
	 */
	public void setCcId(Long ccId) {
		this.ccId = ccId;
	}

	/**
	 * @return the carrierOrgId
	 */
	public Long getCarrierOrgId() {
		return carrierOrgId;
	}

	/**
	 * @param carrierOrgId the carrierOrgId to set
	 */
	public void setCarrierOrgId(Long carrierOrgId) {
		this.carrierOrgId = carrierOrgId;
	}

	/**
	 * @return the containerCd
	 */
	public String getContainerCd() {
		return containerCd;
	}

	/**
	 * @param containerCd the containerCd to set
	 */
	public void setContainerCd(String containerCd) {
		this.containerCd = containerCd;
	}

	/**
	 * @return the noOfTrucks
	 */
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
	 * @return the prefCommodity
	 */
	public String getPrefCommodity() {
		return prefCommodity;
	}

	/**
	 * @param prefCommodity the prefCommodity to set
	 */
	public void setPrefCommodity(String prefCommodity) {
		this.prefCommodity = prefCommodity;
	}

	/**
	 * @return the maxWeight
	 */
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
	 * @return the origCity
	 */
	public String getOrigCity() {
		return origCity;
	}

	/**
	 * @param origCity the origCity to set
	 */
	public void setOrigCity(String origCity) {
		this.origCity = origCity;
	}

	/**
	 * @return the origState
	 */
	public String getOrigState() {
		return origState;
	}

	/**
	 * @param origState the origState to set
	 */
	public void setOrigState(String origState) {
		this.origState = origState;
	}

	/**
	 * @return the origZip
	 */
	public String getOrigZip() {
		return origZip;
	}

	/**
	 * @param origZip the origZip to set
	 */
	public void setOrigZip(String origZip) {
		this.origZip = origZip;
	}

	/**
	 * @return the origCountry
	 */
	public String getOrigCountry() {
		return origCountry;
	}

	/**
	 * @param origCountry the origCountry to set
	 */
	public void setOrigCountry(String origCountry) {
		this.origCountry = origCountry;
	}

	/**
	 * @return the availStartDate
	 */
	public Date getAvailStartDate() {
		return availStartDate;
	}

	/**
	 * @param availStartDate the availStartDate to set
	 */
	public void setAvailStartDate(Date availStartDate) {
		this.availStartDate = availStartDate;
	}

	/**
	 * @return the availEndDate
	 */
	public Date getAvailEndDate() {
		return availEndDate;
	}

	/**
	 * @param availEndDate the availEndDate to set
	 */
	public void setAvailEndDate(Date availEndDate) {
		this.availEndDate = availEndDate;
	}

	/**
	 * @return the contactPersonId
	 */
	public Long getContactPersonId() {
		return contactPersonId;
	}

	/**
	 * @param contactPersonId the contactPersonId to set
	 */
	public void setContactPersonId(Long contactPersonId) {
		this.contactPersonId = contactPersonId;
	}

	/**
	 * @return the prefDestCity
	 */
	public String getPrefDestCity() {
		return prefDestCity;
	}

	/**
	 * @param prefDestCity the prefDestCity to set
	 */
	public void setPrefDestCity(String prefDestCity) {
		this.prefDestCity = prefDestCity;
	}

	/**
	 * @return the prefDestState
	 */
	public String getPrefDestState() {
		return prefDestState;
	}

	/**
	 * @param prefDestState the prefDestState to set
	 */
	public void setPrefDestState(String prefDestState) {
		this.prefDestState = prefDestState;
	}

	/**
	 * @return the prefDestZip
	 */
	public String getPrefDestZip() {
		return prefDestZip;
	}

	/**
	 * @param prefDestZip the prefDestZip to set
	 */
	public void setPrefDestZip(String prefDestZip) {
		this.prefDestZip = prefDestZip;
	}

	/**
	 * @return the prefDestCountry
	 */
	public String getPrefDestCountry() {
		return prefDestCountry;
	}

	/**
	 * @param prefDestCountry the prefDestCountry to set
	 */
	public void setPrefDestCountry(String prefDestCountry) {
		this.prefDestCountry = prefDestCountry;
	}

	/**
	 * @return the origZoneId
	 */
	public Long getOrigZoneId() {
		return origZoneId;
	}

	/**
	 * @param origZoneId the origZoneId to set
	 */
	public void setOrigZoneId(Long origZoneId) {
		this.origZoneId = origZoneId;
	}

	/**
	 * @return the prefDestZoneId
	 */
	public Long getPrefDestZoneId() {
		return prefDestZoneId;
	}

	/**
	 * @param prefDestZoneId the prefDestZoneId to set
	 */
	public void setPrefDestZoneId(Long prefDestZoneId) {
		this.prefDestZoneId = prefDestZoneId;
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
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the dateModified
	 */
	public Date getDateModified() {
		return dateModified;
	}

	/**
	 * @param dateModified the dateModified to set
	 */
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	/**
	 * @return the modifiedBy
	 */
	public Long getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the orgUserId
	 */
	public Long getOrgUserId() {
		return orgUserId;
	}

	/**
	 * @param orgUserId the orgUserId to set
	 */
	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}

	/**
	 * @return the contactEmailAddr
	 */
	public String getContactEmailAddr() {
		return contactEmailAddr;
	}

	/**
	 * @param contactEmailAddr the contactEmailAddr to set
	 */
	public void setContactEmailAddr(String contactEmailAddr) {
		this.contactEmailAddr = contactEmailAddr;
	}

	/**
	 * @return the carrierName
	 */
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName the carrierName to set
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

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
	 * @return the contactFirstName
	 */
	public String getContactFirstName() {
		return contactFirstName;
	}

	/**
	 * @param contactFirstName the contactFirstName to set
	 */
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	/**
	 * @return the contactLastName
	 */
	public String getContactLastName() {
		return contactLastName;
	}

	/**
	 * @param contactLastName the contactLastName to set
	 */
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
}
