package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private Campground mapCampgroundToRowSet(SqlRowSet results) {
		Campground newCampground = new Campground();
		newCampground.setCampgroundId(results.getLong("campground_id"));
		newCampground.setParkID(results.getInt("park_id"));
		newCampground.setName(results.getString("name"));
		newCampground.setOpeningMonth(results.getString("open_from_mm"));
		newCampground.setClosingMonth(results.getString("open_to_mm"));
		newCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		
		
		return newCampground;
	}

	@Override
	public List<Campground> getAvailableCampgrounds(Park park) {
		ArrayList<Campground> AvailableCampgrounds = new ArrayList<>();
		Long parkId = park.getParkId();
		
		String sqlGetAvailableCampgrounds = "SELECT campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee " 
				                          + "FROM campground WHERE park_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableCampgrounds, parkId);
		while(results.next() ) {
			Campground newCampground = mapCampgroundToRowSet(results);
			AvailableCampgrounds.add(newCampground);
		}
		
		return AvailableCampgrounds;
	}

}
