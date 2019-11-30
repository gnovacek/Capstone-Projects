package com.techelevator;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;

public class JDBCSiteDAOIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO siteDAO;
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
		siteDAO = new JDBCSiteDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		parkDAO = new JDBCParkDAO(dataSource);
		
	}
	
	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void test_getAvailableSites() {
		LocalDate fromDate = LocalDate.of(2019, 10, 29);
		LocalDate toDate = LocalDate.of(2019, 11, 03);
		Park testPark = parkDAO.getAllAvailableParks().get(0);
		Campground testCampground = campgroundDAO.getAvailableCampgrounds(testPark).get(0);
		
		Assert.assertEquals(1, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(0).getSiteId().longValue());
		Assert.assertEquals(2, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(1).getSiteId().longValue());
		Assert.assertEquals(3, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(2).getSiteId().longValue());
        Assert.assertEquals(5, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(3).getSiteId().longValue());
        
        Assert.assertEquals(1, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(0).getCampgroundId());
        Assert.assertEquals(1, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(1).getCampgroundId());
        Assert.assertEquals(1, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(2).getCampgroundId());
        Assert.assertEquals(1, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(3).getCampgroundId());
        
        testCampground = campgroundDAO.getAvailableCampgrounds(testPark).get(1);
        
        Assert.assertEquals(277, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(0).getSiteId().longValue());
		Assert.assertEquals(278, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(1).getSiteId().longValue());
		Assert.assertEquals(279, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(2).getSiteId().longValue());
        Assert.assertEquals(280, siteDAO.getAvailableSites(testCampground, fromDate, toDate).get(3).getSiteId().longValue());
        
	}

}
