package com.pls.cache.service;

import java.util.Map;

import javax.inject.Inject;

import com.pls.cache.dao.StaticValuesCacheDao;
import com.pls.cache.model.StaticValuesCache;

public class StaticValuesCacheService {

	@Inject
	private StaticValuesCacheDao dao;
	
	public String getEquipmentTypeDesc(String eqType) {
		Map<String, String> cache = StaticValuesCache.getCache(StaticValuesCache.EQUIPMENT_TYPES_KEY);

		if (cache == null) {
			Map<String, String> eqTypesCache = dao.getEquipmentTypes();
			StaticValuesCache.addToCache(StaticValuesCache.EQUIPMENT_TYPES_KEY, eqTypesCache);
		}

		cache = StaticValuesCache.getCache(StaticValuesCache.EQUIPMENT_TYPES_KEY);
		return cache.get(eqType);
	}
	
	public String getZoneDescription(Long zoneId) {
		if (zoneId == null) 
			return "";
		
		Map<String, String> cache = StaticValuesCache.getCache(StaticValuesCache.CAPACITY_ZONES_KEY);

		if (cache == null) {
			Map<String, String> zonesCache = dao.getCapacityZones();
			StaticValuesCache.addToCache(StaticValuesCache.CAPACITY_ZONES_KEY, zonesCache);
		}

		cache = StaticValuesCache.getCache(StaticValuesCache.CAPACITY_ZONES_KEY);
		return cache.get(zoneId.toString().trim());
	}
}
