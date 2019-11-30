package com.techelevator.npgeek.model;

import java.util.List;

public interface WeatherDAO {

	List<Weather> getFiveDayForecast (String parkCode); 
	
}
