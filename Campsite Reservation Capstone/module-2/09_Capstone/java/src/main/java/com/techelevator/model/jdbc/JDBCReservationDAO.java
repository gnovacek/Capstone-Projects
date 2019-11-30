package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.Site;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private Reservation mapReservationToRowSet(SqlRowSet results) {
		Reservation newReservation = new Reservation();
		newReservation.setReservationId(results.getLong("reservation_id"));
		newReservation.setSiteId(results.getInt("site_id"));
		newReservation.setName(results.getString("name"));
		newReservation.setFromDate(results.getDate("from_date").toLocalDate());
		newReservation.setToDate(results.getDate("to_date").toLocalDate());
		newReservation.setCreateDate(results.getDate("create_date").toLocalDate());
		
		return newReservation;
	}

	@Override
	public List<Reservation> getReservations(Site site) {
		List<Reservation> reservationList = new ArrayList<>();
		Long siteId = site.getSiteId();
		
		String sqlGetReservations = "SELECT reservation_id, site_id, name, from_date, to_date, create_date " 
				                  + "FROM reservation WHERE site_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservations, siteId);
		while(results.next()) {
			Reservation newreservation = mapReservationToRowSet(results);
			reservationList.add(newreservation);
		}
		
		return reservationList;
	}

	@Override
	public void bookReservation(Site siteChoice, LocalDate fromDate, LocalDate toDate, String nameInput) {
		String sqlBookReservation = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) "
                                  + "VALUES (?,?,?,?,NOW())";
		jdbcTemplate.update(sqlBookReservation, siteChoice.getSiteId(), nameInput, fromDate, toDate);
	}

	@Override
	public Reservation getBookedReservation(Site siteChoice, LocalDate fromDate, LocalDate toDate, String nameInput) {
		String sqlGetBookedReservations = "SELECT reservation_id, site_id, name, from_date, to_date, create_date " 
										+ "FROM reservation "
				                        + "WHERE site_id = ? AND name = ? AND from_date = ? AND to_date = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetBookedReservations, siteChoice.getSiteId(), nameInput, fromDate, toDate);
		if(results.next()) {
			return mapReservationToRowSet(results);
		} else
		
		return null;
	}

}
