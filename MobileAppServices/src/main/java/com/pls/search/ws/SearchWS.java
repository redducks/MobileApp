package com.pls.search.ws;

import com.pls.core.ws.WebServiceException;
import com.pls.search.ws.domain.FreightSearchCO;
import com.pls.search.ws.domain.FreightSearchResultVO;

public interface SearchWS {

	public FreightSearchResultVO searchFreight(FreightSearchCO criteria) throws WebServiceException;
}
