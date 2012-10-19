package com.pls.search.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.pls.core.exceptions.PLSException;
import com.pls.core.service.BaseService;
import com.pls.search.dao.SearchDao;
import com.pls.search.ws.domain.FreightSearchCO;
import com.pls.search.ws.domain.FreightSearchResultVO;

@Stateless
public class SearchService extends BaseService {

	@Inject
	private transient Logger logger;

	@Inject 
	private SearchDao searchDao;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FreightSearchResultVO searchFreight(FreightSearchCO criteria) throws PLSException {
		try {
			return null;
		} catch (Exception ex) {
			logger.log(Level.INFO, "Freight Search - Failed");
			throw new PLSException(0, "An error occurred while sending an email", ex);
		}
	}
}
