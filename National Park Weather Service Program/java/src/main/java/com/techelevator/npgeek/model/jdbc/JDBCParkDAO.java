package com.techelevator.npgeek.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.ParkDAO;

@Component
public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		ArrayList<Park> parks = new ArrayList<Park>();
		String sqlGetAllParks = "Select parkcode, parkname, state, acreage, elevationinfeet, milesoftrail, numberofcampsites, climate, yearfounded, annualvisitorcount, inspirationalquote, inspirationalquotesource, parkdescription, entryfee, numberofanimalspecies\n"
				+ "FROM park;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while (results.next()) {
			Park theparks = mapRowToPark(results);
			parks.add(theparks);
		}
		return parks;
	}

	@Override
	public Park getParkByCode(String parkCode) {
		Park newPark = null; 
		
		String sqlGetParkbyCode = "Select * FROM park "
				+ "WHERE parkcode = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkbyCode, parkCode);
		while (results.next()) {
			newPark = mapRowToPark(results);
			
		}
		
		return newPark;
	}

	@Override
	public Park getParkByName(String parkName) {
		return null;
	}

	private Park mapRowToPark(SqlRowSet results) {
		Park newPark = new Park();
		newPark.setParkCode(results.getString("parkcode"));
		newPark.setParkName(results.getString("parkname"));
		newPark.setState(results.getString("state"));
		newPark.setAcreage(results.getInt("acreage"));
		newPark.setElevationInFeet(results.getInt("elevationinfeet"));
		newPark.setMilesOfTrail(results.getDouble("milesoftrail"));
		newPark.setNumberOfCampsites(results.getInt("numberofcampsites"));
		newPark.setClimate(results.getString("climate"));
		newPark.setYearFounded(results.getInt("yearfounded"));
		newPark.setAnnualVisitorCount(results.getInt("annualvisitorcount"));
		newPark.setInspirationalQuote(results.getString("inspirationalquote"));
		newPark.setInspirationalQuoteSource(results.getString("inspirationalquotesource"));
		newPark.setParkDescription(results.getString("parkdescription"));
		newPark.setEntryFee(results.getInt("entryfee"));
		newPark.setNumberOfAnimalSpecies(results.getInt("numberofanimalspecies"));

		return newPark;
	}

}
