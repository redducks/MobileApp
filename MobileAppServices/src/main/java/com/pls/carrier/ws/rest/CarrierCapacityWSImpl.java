package com.pls.carrier.ws.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.pls.carrier.service.CarrierCapacityService;
import com.pls.carrier.ws.CarrierCapacityWS;
import com.pls.carrier.ws.domain.CarrierCapacityCO;
import com.pls.core.exceptions.PLSException;
import com.pls.core.ws.WebServiceException;
import com.pls.core.ws.vo.ResultVO;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members table.
 */
@Path("/carrier")
@RequestScoped
public class CarrierCapacityWSImpl implements CarrierCapacityWS {

	@EJB
	private CarrierCapacityService ccService;
	
	@POST
	@Produces("application/xml")
	@Path("/postCapacity")
	@Consumes("application/xml")
	public ResultVO postCapacity(CarrierCapacityCO carrierCapacity) throws WebServiceException {
		try {
		return new ResultVO(ccService.postCapacity(carrierCapacity));
		} catch (PLSException pe) {
			throw new WebServiceException(pe);
		}
	}
}
