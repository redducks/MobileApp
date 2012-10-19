package com.pls.cache.model;

import java.util.HashMap;
import java.util.Map;

public class StaticValuesCache {
	
	public static final String EQUIPMENT_TYPES_KEY = "CONTAINER_TYPES";
	public static final String CAPACITY_ZONES_KEY = "CAPACITY_ZONES";

	private static Map<String, Map<String, String>> cacheMap = new HashMap<String, Map<String, String>>(); 
	
	public static Map<String, String> getCache(String cacheKey) {
		return cacheMap.get(cacheKey);
	}
	
	public static void clearCache(String cacheKey) {
		cacheMap.remove(cacheKey);
	}
	
	public static void addToCache(String cacheKey, Map<String, String> staticValues) {
		cacheMap.put(cacheKey, staticValues);
	}
}
