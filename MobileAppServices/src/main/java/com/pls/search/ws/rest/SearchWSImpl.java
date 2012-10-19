package com.pls.search.ws.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.pls.core.exceptions.PLSException;
import com.pls.core.ws.WebServiceException;
import com.pls.search.service.SearchService;
import com.pls.search.ws.SearchWS;
import com.pls.search.ws.domain.FreightSearchCO;
import com.pls.search.ws.domain.FreightSearchResultVO;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members table.
 */
@Path("/search")
@RequestScoped
public class SearchWSImpl implements SearchWS {

	@EJB
	private SearchService searchService;
	
	@POST
	@Produces("application/xml")
	@Path("/freightSearch")
	@Consumes("application/xml")
	public FreightSearchResultVO searchFreight(FreightSearchCO criteria) throws WebServiceException {
		try {
		return searchService.searchFreight(criteria);
		} catch (PLSException pe) {
			throw new WebServiceException(pe);
		}
	}
}
