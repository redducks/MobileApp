package com.pls.search.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.pls.carrier.model.CarrierCapacity;
import com.pls.core.exceptions.ExceptionCodes;
import com.pls.core.exceptions.PLSException;
import com.pls.search.ws.domain.FreightSearchCO;

public class SearchDao {

	@Inject
	private EntityManager em;

	@Inject
	private transient Logger logger;

	public List getFreightSearchResults(FreightSearchCO criteria) throws PLSException {
		try {
			String sql = "SELECT U.PERSON_ID, OU.ORG_USER_ID FROM USERS U, ORGANIZATIONS O, ORGANIZATION_USERS OU " +
					" WHERE U.PERSON_ID=OU.PERSON_ID AND OU.ORG_ID=O.ORG_ID AND U.EMAIL_ADDRESS = :contactEmail and O.ORG_ID = :orgId";

			Query query = em.createNativeQuery(sql);

			return (List) query.getResultList();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "An error occurred when finding the carrier contact.");
			throw new PLSException(ExceptionCodes.UNKNOWN_ERROR.getCode(), "An error occurred when finding the carrier contact.", ex);
		}
	}
}
