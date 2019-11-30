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
import com.techelevator.model.jdbc.JDBCParkDAO;


public class JDBCParkDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
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
		parkDAO = new JDBCParkDAO(dataSource);
		
	}
	
	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void test_getAllAvailableParks() {
		Assert.assertEquals("Acadia", parkDAO.getAllAvailableParks().get(0).getName());
		Assert.assertEquals("Arches", parkDAO.getAllAvailableParks().get(1).getName());
		Assert.assertEquals("Cuyahoga Valley", parkDAO.getAllAvailableParks().get(2).getName());
	}	
	
	@Test
	public void test_getParkInformation() {
		Assert.assertEquals("Maine", parkDAO.getParkInformation("Acadia").getLocation());
		Assert.assertEquals(Date.valueOf("1919-02-26"), parkDAO.getParkInformation("Acadia").getEstablishDate());
		Assert.assertEquals(47389, parkDAO.getParkInformation("Acadia").getArea());
		Assert.assertEquals(2563129, parkDAO.getParkInformation("Acadia").getVisitors());
		Assert.assertEquals("Utah", parkDAO.getParkInformation("Arches").getLocation());
		Assert.assertEquals(Date.valueOf("1929-04-12"), parkDAO.getParkInformation("Arches").getEstablishDate());
		Assert.assertEquals(76518, parkDAO.getParkInformation("Arches").getArea());
		Assert.assertEquals(1284767, parkDAO.getParkInformation("Arches").getVisitors());
		Assert.assertEquals("Ohio", parkDAO.getParkInformation("Cuyahoga Valley").getLocation());
		Assert.assertEquals(Date.valueOf("2000-10-11"), parkDAO.getParkInformation("Cuyahoga Valley").getEstablishDate());
		Assert.assertEquals(32860, parkDAO.getParkInformation("Cuyahoga Valley").getArea());
		Assert.assertEquals(2189849, parkDAO.getParkInformation("Cuyahoga Valley").getVisitors());
	}
}
