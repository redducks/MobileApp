package com.pls.carrier.ws.soap;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.pls.carrier.service.CarrierCapacityService;
import com.pls.carrier.ws.CarrierCapacityWS;
import com.pls.carrier.ws.domain.CarrierCapacityCO;
import com.pls.core.exceptions.PLSException;
import com.pls.core.ws.WebServiceException;
import com.pls.core.ws.vo.ResultVO;

@WebService (name="carrierCapacityWS", portName="carrierCapacity")
public class CarrierCapacityWSImpl implements CarrierCapacityWS {
	
	@EJB
	private CarrierCapacityService ccService;


	@Override
	@WebMethod(operationName="postCapacity")
	public @WebResult(name="result") ResultVO postCapacity(@WebParam(name="carrierCapacity") CarrierCapacityCO carrierCapacity) throws WebServiceException {
		try {
			return new ResultVO(ccService.postCapacity(carrierCapacity));
		} catch (PLSException pe) {
			throw new WebServiceException(pe);
		}
	}
}
