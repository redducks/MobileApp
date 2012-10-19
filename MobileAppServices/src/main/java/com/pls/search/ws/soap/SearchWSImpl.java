package com.pls.search.ws.soap;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.pls.core.exceptions.PLSException;
import com.pls.core.ws.WebServiceException;
import com.pls.search.service.SearchService;
import com.pls.search.ws.SearchWS;
import com.pls.search.ws.domain.FreightSearchCO;
import com.pls.search.ws.domain.FreightSearchResultVO;

@WebService (name="searchWS", portName="search")
public class SearchWSImpl implements SearchWS {
	
	@EJB
	private SearchService searchService;


	@Override
	@WebMethod(operationName="searchFreight")
	public @WebResult(name="result") FreightSearchResultVO searchFreight(@WebParam(name="freight") FreightSearchCO criteria) throws WebServiceException {
		try {
			return searchService.searchFreight(criteria);
		} catch (PLSException pe) {
			throw new WebServiceException(pe);
		}
	}
}
