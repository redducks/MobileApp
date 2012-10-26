package com.pls.carrier.dao;

import static com.pls.core.util.ErrorCodeMatcher.hasErrorCode;

import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.pls.core.dao.AbstractDaoTest;
import com.pls.core.dao.PersistenceHelper;
import com.pls.core.exceptions.ExceptionCodes;
import com.pls.core.exceptions.PLSException;

public class CarrierCapacityDaoTest extends AbstractDaoTest<CarrierCapacityDao> {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetCarrierByScac() throws Exception {
		Long result = bean.getCarrier(SCAC, null);
		
		Assert.assertEquals("Incorrect Carrier Org Id returned", CARRIER_ID, result);
	}
	
	@Test
	public void testGetCarrierByMcNum() throws Exception {
		Long result = bean.getCarrier(null, MCNUMBER);
		
		Assert.assertEquals("Incorrect Carrier Org Id returned", CARRIER_ID, result);
	}
	
	@Test
	public void testGetCarrierByScacAndMcNum() throws Exception {
		Long result = bean.getCarrier(SCAC, MCNUMBER);
		
		Assert.assertEquals("Incorrect Carrier Org Id returned", CARRIER_ID, result);
	}
	
	@Test 
	public void testGetCarrierByInvalidScac() throws Exception {
		thrown.expect(PLSException.class);
		thrown.expect(hasErrorCode(ExceptionCodes.UNKNOWN_CARRIER));
		bean.getCarrier(INVALID_SCAC, null);
	}
	
	@Test
	public void testGetCarrierByInvalidMcNum() throws Exception {
		thrown.expect(PLSException.class);
		thrown.expect(hasErrorCode(ExceptionCodes.UNKNOWN_CARRIER));
		bean.getCarrier(null, INVALID_MCNUM);
	}
	
	@Test 
	public void testGetCarrierByInvalidScacAndMcNum() throws Exception {
		thrown.expect(PLSException.class);
		thrown.expect(hasErrorCode(ExceptionCodes.UNKNOWN_CARRIER));
		bean.getCarrier(INVALID_SCAC, INVALID_MCNUM);
	}
	
	@Test 
	public void testGetCarrierByInvalidScacAndValidMcNum() throws Exception {
		thrown.expect(PLSException.class);
		thrown.expect(hasErrorCode(ExceptionCodes.UNKNOWN_CARRIER));
		bean.getCarrier(INVALID_SCAC, MCNUMBER);
	}
	
	@Test
	public void testGetCarrierByValidScacAndInvalidMcNum() throws Exception {
		thrown.expect(PLSException.class);
		thrown.expect(hasErrorCode(ExceptionCodes.UNKNOWN_CARRIER));
		bean.getCarrier(SCAC, INVALID_MCNUM);
	}
	
	@Test
	public void testGetZoneIdByZoneName() throws Exception {
		Long result = bean.getZoneIdByName(ZONE_NAME);
		
		Assert.assertEquals("Incorrect Zone Id returned", EXPECTED_ZONE_ID, result);
	}
	
	@Test 
	public void testGetZoneIdByInvalidZoneName() throws Exception {
		thrown.expect(PLSException.class);
		thrown.expect(hasErrorCode(ExceptionCodes.UNKNOWN_ZONE));
		bean.getZoneIdByName(INVALID_ZONE_NAME);
	}
	
	@Test 
	public void testGetGeneratedCCId() throws Exception {
		Long ccId = bean.getGeneratedCCId();
		Assert.assertNotNull("CCID generated is null", ccId);
		
		boolean result = (ccId > 0);
		Assert.assertTrue("CCID generated is zero", result);
	}
	
	@Before
	public void beforeClass() throws Exception {
		bean = new CarrierCapacityDao();
		setField("em", PersistenceHelper.getEntityManager());
		setField("logger", LOGGER);
	}
	
	private static final String SCAC = "9876";
	private static final String MCNUMBER = "123456";
	private static final String INVALID_SCAC = "ABCD";
	private static final String INVALID_MCNUM = "564675";
	private static final Long CARRIER_ID = 999999L;
	private static final String ZONE_NAME = "ZONE 999";
	private static final String INVALID_ZONE_NAME = "ZONE XYZ";
	private static final Long EXPECTED_ZONE_ID = 999999L;
	
	private final static Logger LOGGER = Logger.getLogger(CarrierCapacityDao.class.getName()); 
}
