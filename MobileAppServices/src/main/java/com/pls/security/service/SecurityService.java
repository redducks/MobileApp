package com.pls.security.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.pls.core.exceptions.PLSException;
import com.pls.search.dao.SearchDao;
import com.pls.search.ws.domain.FreightSearchCO;
import com.pls.security.ws.vo.UserContextVO;

@Stateless
public class SecurityService {

	@Inject
	private transient Logger logger;

	@Inject 
	private SearchDao searchDao;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UserContextVO validateUser(FreightSearchCO criteria) throws PLSException {
		try {
			return null;
		} catch (Exception ex) {
			logger.log(Level.INFO, "Login - Failed");
			throw new PLSException(0, "An error occurred while attempting to login to the system.", ex);
		}
	}

}
