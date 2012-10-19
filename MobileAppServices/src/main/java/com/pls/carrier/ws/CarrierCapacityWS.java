package com.pls.carrier.ws;

import com.pls.carrier.ws.domain.CarrierCapacityCO;
import com.pls.core.ws.WebServiceException;
import com.pls.core.ws.vo.ResultVO;

public interface CarrierCapacityWS {

	public ResultVO postCapacity(CarrierCapacityCO carrierCapacity) throws WebServiceException;
}
