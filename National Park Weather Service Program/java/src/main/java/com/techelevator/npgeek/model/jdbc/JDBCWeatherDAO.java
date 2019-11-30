package com.techelevator.npgeek.model.jdbc;

import com.techelevator.npgeek.model.WeatherDAO;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.npgeek.model.Weather;

@Component
public class JDBCWeatherDAO implements WeatherDAO{

	private JdbcTemplate jdbcTemplate;

	public JDBCWeatherDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private Weather mapWeatherToRowSet(SqlRowSet results) {
		Weather newWeather = new Weather();
		newWeather.setParkCode(results.getString("parkcode"));
		newWeather.setFiveDayForecastValue(results.getInt("fivedayforecastvalue"));
		newWeather.setLow(results.getInt("low"));
		newWeather.setHigh(results.getInt("high"));
		newWeather.setForecast(results.getString("forecast"));
		newWeather.setConditions(newWeather.getForecast(), newWeather.getHighF(), newWeather.getLowF());
		
		return newWeather;
	}
	
	@Override
	public List<Weather> getFiveDayForecast(String parkCode) {

		List<Weather> AllWeather = new ArrayList<>(); 

		String sqlSelectWeather = "SELECT * FROM weather WHERE parkcode = ?";
		SqlRowSet results =jdbcTemplate.queryForRowSet(sqlSelectWeather, parkCode);

		while(results.next()) {
			Weather newWeather = mapWeatherToRowSet(results);  
			AllWeather.add(newWeather); 
		}

		return AllWeather;
	}
	
}
