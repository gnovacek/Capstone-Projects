package com.techelevator;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.techelevator.model.Park;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;

public class JDBCCampgroundDAOIntegrationTest{
	
	private static SingleConnectionDataSource dataSource;
	private JDBCCampgroundDAO campgroundDAO;
	private JDBCParkDAO parkDAO;
	
	@BeforeClass
	public static void setupBeforeClass() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void cleanupAfterClass() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		parkDAO = new JDBCParkDAO(dataSource);
		
	}
	
	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void test_getAvailableCampgrounds() {
		Park testPark = new Park();
		testPark = parkDAO.getAllAvailableParks().get(0);
		
		Assert.assertEquals("Blackwoods", campgroundDAO.getAvailableCampgrounds(testPark).get(0).getName());
		Assert.assertEquals("Seawall", campgroundDAO.getAvailableCampgrounds(testPark).get(1).getName());
		Assert.assertEquals("Schoodic Woods", campgroundDAO.getAvailableCampgrounds(testPark).get(2).getName());
		Assert.assertEquals("01", campgroundDAO.getAvailableCampgrounds(testPark).get(0).getOpeningMonth());
		Assert.assertEquals("05", campgroundDAO.getAvailableCampgrounds(testPark).get(1).getOpeningMonth());
		Assert.assertEquals("05", campgroundDAO.getAvailableCampgrounds(testPark).get(2).getOpeningMonth());
		Assert.assertEquals("12", campgroundDAO.getAvailableCampgrounds(testPark).get(0).getClosingMonth());
		Assert.assertEquals("09", campgroundDAO.getAvailableCampgrounds(testPark).get(1).getClosingMonth());
		Assert.assertEquals("10", campgroundDAO.getAvailableCampgrounds(testPark).get(2).getClosingMonth());
		Assert.assertEquals(new BigDecimal ("35.0"), campgroundDAO.getAvailableCampgrounds(testPark).get(0).getDailyFee());
		Assert.assertEquals(new BigDecimal ("30.0"), campgroundDAO.getAvailableCampgrounds(testPark).get(1).getDailyFee());
		Assert.assertEquals(new BigDecimal ("30.0"), campgroundDAO.getAvailableCampgrounds(testPark).get(2).getDailyFee());
	}

}
