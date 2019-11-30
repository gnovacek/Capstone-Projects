package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private Site mapSiteToRowSet(SqlRowSet results) {
		Site newSite = new Site();
		newSite.setSiteId(results.getLong("site_id"));
		newSite.setSiteNumber(results.getInt("site_number"));
		newSite.setCampgroundId(results.getInt("campground_id"));
		newSite.setMaxOccupancy(results.getInt("max_occupancy"));
		newSite.setAccessible(results.getBoolean("accessible"));
		newSite.setMaxRvLength(results.getInt("max_rv_length"));
		newSite.setUtilities(results.getBoolean("utilities"));
		
		return newSite;
	}

	@Override
	public List<Site> getAvailableSites(Campground campground, LocalDate fromDate, LocalDate toDate) {
		List<Site> availableSites = new ArrayList<>();
		
		String sqlGetAvailableSites = "SELECT DISTINCT * "
				+ "FROM site " + "WHERE campground_id = ? AND site_id NOT IN ("
				+ "SELECT site_id FROM reservation WHERE (? <= reservation.to_date AND ? >= reservation.from_date)) " 
				+ "LIMIT 5";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSites, campground.getCampgroundId(), fromDate, toDate);
		while(results.next()) {
			Site newSite = mapSiteToRowSet(results);
			availableSites.add(newSite);
		}
		return availableSites;
	}

}
