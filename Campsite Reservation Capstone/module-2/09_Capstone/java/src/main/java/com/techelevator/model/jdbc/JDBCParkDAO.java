package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private Park mapParkToRowSet(SqlRowSet results) {
		Park newPark = new Park();
		newPark.setParkId(results.getLong("park_id"));
		newPark.setName(results.getString("name"));
		newPark.setLocation(results.getString("location"));
		newPark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		newPark.setArea(results.getInt("area"));
		newPark.setVisitors(results.getInt("visitors"));
		newPark.setDescription(results.getString("description"));

		return newPark;
	}
	
	@Override
	public List<Park> getAllAvailableParks() {
		ArrayList<Park> AllAvailableParks = new ArrayList<>();

		String sqlGetAllAvailableParks = "SELECT park_id, name, location, establish_date, area, visitors, description "
				            		   + "FROM park ORDER BY name ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllAvailableParks);
		while (results.next()) {
			Park newPark = mapParkToRowSet(results);
			AllAvailableParks.add(newPark);
		}

		return AllAvailableParks;
	}

	@Override
	public Park getParkInformation(String parkName) {
			Park pickedPark = new Park();
			
			String sqlPickedPark = "SELECT park_id, name, location, establish_date, area, visitors, description "
								 + "FROM park WHERE name = ?";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlPickedPark, parkName);
			
			while(results.next()) {
				pickedPark = mapParkToRowSet(results);
				
			}
			
		return pickedPark;
	}

}
