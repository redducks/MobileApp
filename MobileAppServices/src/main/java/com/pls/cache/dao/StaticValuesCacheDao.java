package com.pls.cache.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class StaticValuesCacheDao {
	@Inject
	private EntityManager em;

	@Inject
	private transient Logger logger;

	public Map<String, String> getEquipmentTypes() {
		Map<String, String> eqTypes = new HashMap<String, String>();
		try {
			Query query = em.createNativeQuery("select CONTAINER_CD, DESCRIPTION from CONTAINER_TYPES");
			
			List<Object[]> result = (List<Object[]>) query.getResultList();

			for (Object[] entry : result) {
				eqTypes.put((String)entry[0], (String)entry[1]);
			}
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "An error occurred when load the static values 'Equipment Types'");
		}
		return eqTypes;
	}
	
	public Map<String, String> getCapacityZones() {
		Map<String, String> zones = new HashMap<String, String>();
		try {
			Query query = em.createNativeQuery("select z_14.zone_id, z_14.name, ze_80.state_code FROM " +
							" zones z_14 LEFT OUTER JOIN zone_elements ze_80 ON z_14.zone_id = ze_80.zone_id " +
							" WHERE z_14.shipper_org_id = 38941 AND z_14.capacity_zone = 'Y' ORDER BY z_14.name");
			
			List<Object[]> result = (List<Object[]>) query.getResultList();
			
			String zoneId = "";
			StringBuilder zoneDesc = new StringBuilder("");
			for (Object[] entry : result) {
				if (zoneId.equals(entry[0].toString())) {
					zoneDesc.append(", ").append(entry[2]);
				} else {
					if (!zoneId.equals("")) {
						zones.put(entry[0].toString().trim(), zoneDesc.toString());
					}
					
					zoneId = entry[0].toString();
					zoneDesc = new StringBuilder(entry[1].toString()).append(" - ").append(entry[2]);
				}
			}
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "An error occurred when load the static values 'Equipment Types'");
		}
		return zones;
	}
}
