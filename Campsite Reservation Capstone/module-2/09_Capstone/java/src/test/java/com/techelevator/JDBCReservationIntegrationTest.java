package com.techelevator;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;


public class JDBCReservationIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO parkDAO;
	private JDBCCampgroundDAO campgroundDAO;
	private JDBCSiteDAO siteDAO;
	private JDBCReservationDAO reservationDAO;
	
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
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		
	}
	
	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void test_getReservations() {
		Site chosenSite = new Site();
		Long id = new Long(1);
		chosenSite.setSiteId(id);
		Reservation testReservation = reservationDAO.getReservations(chosenSite).get(0);
		
		Assert.assertEquals(1, testReservation.getSiteId());
		Assert.assertEquals(1, testReservation.getReservationId().longValue());
		Assert.assertEquals("Smith Family Reservation", testReservation.getName());
	}
	
	@Test
	public void test_bookReservation() {
		String nameInput = "Jessica";
		LocalDate fromDate = LocalDate.of(2019, 11, 26);
		LocalDate toDate = LocalDate.of(2019, 11, 30);
		Site chosenSite = new Site();
		Long id = new Long(1);
		chosenSite.setSiteId(id);
		reservationDAO.bookReservation(chosenSite, fromDate, toDate, nameInput);
		
		Assert.assertEquals(1, reservationDAO.getBookedReservation(chosenSite, fromDate, toDate, nameInput).getSiteId());
		Assert.assertEquals(fromDate, reservationDAO.getBookedReservation(chosenSite, fromDate, toDate, nameInput).getFromDate());
        Assert.assertEquals(toDate, reservationDAO.getBookedReservation(chosenSite, fromDate, toDate, nameInput).getToDate());
        Assert.assertEquals("Jessica", reservationDAO.getBookedReservation(chosenSite, fromDate, toDate, nameInput).toString());
	}

}
