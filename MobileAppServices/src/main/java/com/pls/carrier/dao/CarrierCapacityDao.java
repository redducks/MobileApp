package com.pls.carrier.dao;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.pls.carrier.model.CarrierCapacity;
import com.pls.core.exceptions.ExceptionCodes;
import com.pls.core.exceptions.PLSException;

public class CarrierCapacityDao {

	@Inject
	private EntityManager em;

	@Inject
	private transient Logger logger;

	public Long saveCarrierCapacity(CarrierCapacity carrierCapacity) throws PLSException {
		try {
			em.persist(carrierCapacity);
			em.flush();
			return carrierCapacity.getCarrierCapacityId();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "An error occurred when saving the carrier capacity");
			throw new PLSException(ExceptionCodes.UNKNOWN_ERROR.getCode(), "An error occurred when saving the carrier capacity", ex);
		}
	}

	public Long getCarrier(String scac, String mcNumber) throws PLSException {
		try {
			StringBuilder sql = new StringBuilder("select ORG_ID from ORGANIZATIONS where 1 = 1 ");
			if (scac != null && !"".equals(scac.trim())) {
				sql.append(" and upper(SCAC) = :scac ");
			}
			if (mcNumber != null && !"".equals(mcNumber.trim())) {
				sql.append(" and upper(MC_NUM) = :mcNumber ");
			}

			Query query = em.createNativeQuery(sql.toString());
			if (scac != null && !"".equals(scac.trim())) {
				query.setParameter("scac", scac.toUpperCase());
			}
			if (mcNumber != null && !"".equals(mcNumber.trim())) {
				query.setParameter("mcNumber", mcNumber.toUpperCase());
			}

			BigDecimal orgId = (BigDecimal)query.getSingleResult();
			return orgId.longValue();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "An error occurred when finding the carrier.");
			throw new PLSException(ExceptionCodes.UNKNOWN_ERROR.getCode(), "An error occurred when finding the carrier.", ex);
		}
	}

	public Object[] getCarrierContact(String contactEmail, Long orgId) throws PLSException {
		try {
			String sql = "SELECT U.PERSON_ID, OU.ORG_USER_ID FROM USERS U, ORGANIZATIONS O, ORGANIZATION_USERS OU " +
					" WHERE U.PERSON_ID=OU.PERSON_ID AND OU.ORG_ID=O.ORG_ID AND U.EMAIL_ADDRESS = :contactEmail and O.ORG_ID = :orgId";

			Query query = em.createNativeQuery(sql);
			query.setParameter("contactEmail", contactEmail);
			query.setParameter("orgId", orgId);

			return (Object[]) query.getSingleResult();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "An error occurred when finding the carrier contact.");
			throw new PLSException(ExceptionCodes.UNKNOWN_ERROR.getCode(), "An error occurred when finding the carrier contact.", ex);
		}
	}

	public Long getGeneratedCCId() {
		String sql = "select CC_SEQ.nextval from dual";

		Query query = em.createNativeQuery(sql);
		BigDecimal ccId = (BigDecimal)query.getSingleResult();
		return ccId.longValue();
	}
}
