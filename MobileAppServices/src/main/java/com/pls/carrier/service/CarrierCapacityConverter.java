package com.pls.carrier.service;

import javax.inject.Inject;

import com.pls.cache.service.StaticValuesCacheService;
import com.pls.carrier.model.CarrierCapacity;
import com.pls.carrier.ws.domain.CarrierCapacityCO;
import com.pls.core.util.DateUtility;
import com.pls.core.util.ObjectUtil;
import com.pls.sendmail.templates.CarrierCapacityTemplate;

/**
 * This class is intended for converting web service objects to domain model and domain model objects to email templates etc.
 * @author pchalla
 *
 */
public class CarrierCapacityConverter {
	
	@Inject
	private StaticValuesCacheService cacheService;

	public CarrierCapacity convert(CarrierCapacityCO carrierCapacity) {
		CarrierCapacity vo = new CarrierCapacity();
		vo.setContainerCd(carrierCapacity.getEquipmentType());
		vo.setMaxWeight(carrierCapacity.getMaxWeight());
		vo.setNoOfTrucks(carrierCapacity.getNoOfTrucks());
		vo.setAvailStartDate(carrierCapacity.getAvailableDateTime());
		vo.setStatus(carrierCapacity.getStatus());
		vo.setOrigCity(carrierCapacity.getOriginCity());
		vo.setOrigState(carrierCapacity.getOriginState());
		vo.setOrigZip(carrierCapacity.getOriginZip());
//		if (carrierCapacity.getOriginZone() != null && !"".equals(carrierCapacity.getOriginZone().trim())) {
//			vo.setOrigZoneId(Long.parseLong(carrierCapacity.getOriginZone()));
//		}
		vo.setPrefDestCity(carrierCapacity.getDestCity());
		vo.setPrefDestState(carrierCapacity.getDestState());
		vo.setPrefDestZip(carrierCapacity.getDestZip());
		vo.setPrefDestCountry(carrierCapacity.getDestCountry());
//		if (carrierCapacity.getDestZone() != null && !"".equals(carrierCapacity.getDestZone().trim())) {
//			vo.setPrefDestZoneId(Long.parseLong(carrierCapacity.getDestZone()));
//		}
		vo.setContactEmailAddr(carrierCapacity.getCarrierContact());
		vo.setCarrierScac(carrierCapacity.getScac());

		return vo;
	}
	
	public CarrierCapacityTemplate getMailTemplate(CarrierCapacity co) {
		CarrierCapacityTemplate template = new CarrierCapacityTemplate();
		template.setEquipmentType(co.getContainerCd());
		template.setNoOfTrucks("" + co.getNoOfTrucks());
		template.setMaxWeight("" + co.getMaxWeight());
		
		template.setCarrierScac(co.getCarrierScac());
		template.setContactEmail(co.getContactEmailAddr());
		template.setEquipmentTypeDesc(cacheService.getEquipmentTypeDesc(co.getContainerCd()));
		
		if(co.getAvailStartDate() != null){
			template.setStartDateTime("" + DateUtility.dateToString(co.getAvailStartDate(), "MM/dd/yyyy hh:mm a"));
		}

		StringBuffer orig = new StringBuffer();
		if(!ObjectUtil.isEmpty(co.getOrigCity())) {
			orig.append(co.getOrigCity());
		}
		if(!ObjectUtil.isEmpty(co.getOrigState())) {
			orig.append(", " + co.getOrigState() + " ");
		}
		if(!ObjectUtil.isEmpty(co.getOrigCountry())) {
			orig.append(co.getOrigCountry() + " ");
		}
		if(!ObjectUtil.isEmpty(co.getOrigZip())) {
			orig.append(co.getOrigZip());
		}
		template.setOrigin(orig.toString());
		
		StringBuffer prefDest = new StringBuffer();
		if(!ObjectUtil.isEmpty(co.getPrefDestCity())) {
			prefDest.append(co.getPrefDestCity());
		}
		if(!ObjectUtil.isEmpty(co.getPrefDestState())) {
			prefDest.append(", " + co.getPrefDestState() + " ");
		}
		if(!ObjectUtil.isEmpty(co.getPrefDestCountry())) {
			prefDest.append(co.getPrefDestCountry() + " ");
		}
		if(!ObjectUtil.isEmpty(co.getPrefDestZip())) {
			prefDest.append(co.getPrefDestZip());
		}
		template.setPreferredDest(prefDest.toString());
		template.setOrigZone(cacheService.getZoneDescription(co.getOrigZoneId()));
		template.setDestZone(cacheService.getZoneDescription(co.getPrefDestZoneId()));
		
		return template;
	}

}
