package com.pls.carrier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.drools.runtime.rule.ConsequenceException;

import com.pls.carrier.dao.CarrierCapacityDao;
import com.pls.carrier.model.CarrierCapacity;
import com.pls.carrier.model.CarrierCapacityStatus;
import com.pls.carrier.ws.domain.CarrierCapacityCO;
import com.pls.core.exceptions.ExceptionCodes;
import com.pls.core.exceptions.PLSException;
import com.pls.core.service.BaseService;
import com.pls.core.util.ObjectUtil;
import com.pls.core.util.PropertyUtil;
import com.pls.sendmail.vo.TemplateContent;

@Stateless
public class CarrierCapacityService extends BaseService {

	@Inject
	private transient Logger logger;

	@Inject 
	private CarrierCapacityDao ccDao;

	@Inject 
	private CarrierCapacityConverter converter;
	
	public static final String POST_CAPACITY_RULE_FILE = "rules/postCarrierCapacity.drl";

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	protected CarrierCapacity validateNSaveCapacity(CarrierCapacityCO carrierCapacity) throws PLSException {
		// Fire the rules
		fireRules(POST_CAPACITY_RULE_FILE, carrierCapacity);

		// Check if SCAC and MC Number combination is valid
		Long carrierOrgId = ccDao.getCarrier(carrierCapacity.getScac(), carrierCapacity.getMcNumber());

		// Check if Carrier Contact is valid
		//		Object[] contactId = ccDao.getCarrierContact(carrierCapacity.getCarrierContact(), carrierOrgId);

		// Get the origin zone id
		Long originZone = null;
		if (!ObjectUtil.isEmpty(carrierCapacity.getOriginZone())) {
			originZone = ccDao.getZoneIdByName(carrierCapacity.getOriginZone());
		}

		// Get the Destination zone id
		Long destZone = null;
		if (!ObjectUtil.isEmpty(carrierCapacity.getDestZone())) {
			destZone = ccDao.getZoneIdByName(carrierCapacity.getDestZone());
		}

		// Convert the Web service criteria object to domain object
		CarrierCapacity capacityDo = converter.convert(carrierCapacity);
		capacityDo.setCarrierOrgId(carrierOrgId);
		//		capacityDo.setContactPersonId(((BigDecimal)contactId[0]).longValue());
		//		capacityDo.setOrgUserId(((BigDecimal)contactId[1]).longValue());
		capacityDo.setOrigZoneId(originZone);
		capacityDo.setPrefDestZoneId(destZone);
		
		// Get the CCID
		capacityDo.setCcId(ccDao.getGeneratedCCId());

		// Save the Capacity
		ccDao.saveCarrierCapacity(capacityDo);

		return capacityDo;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	protected Boolean sendMail(CarrierCapacity capacityDo) throws PLSException {
		try {
			if (CarrierCapacityStatus.PENDING.getCode().equals(capacityDo.getStatus())) {
				//send mail to the users
				TemplateContent template = converter.getMailTemplate(capacityDo);
				sendMail(template, PropertyUtil.getPostCapacityRecipient(), PropertyUtil.getDefaultSenderAddress());
			}

			return true;
		} catch (Exception ex) {
			throw new PLSException(ExceptionCodes.ERROR_SENDING_EMAIL.getCode(), "An error occurred while sending an email", ex);
		}
	}

	public Boolean postCapacity(CarrierCapacityCO carrierCapacity) throws PLSException {
		try {
			logger.log(Level.INFO, "Post Carrier Capacity - Started");

			CarrierCapacity capacityDo = validateNSaveCapacity(carrierCapacity);

			if (capacityDo.getCarrierCapacityId() != null) {
				return sendMail(capacityDo);
			}

			logger.log(Level.INFO, "Post Carrier Capacity with {0} is successful", capacityDo.getCarrierCapacityId());
			return true;
		} catch (ConsequenceException ce) { 
			logger.log(Level.SEVERE, ce.getCause().getMessage());
			throw (PLSException) ce.getCause();
		} catch (PLSException ex) {
			logger.log(Level.INFO, "Post Carrier Capacity - Failed");
			throw ex;
		}
	}
}
