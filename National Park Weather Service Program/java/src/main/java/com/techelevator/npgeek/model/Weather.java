package com.techelevator.npgeek.model;

import java.util.ArrayList;
import java.util.List;

public class Weather {

	private String parkCode;
	private int fiveDayForecastValue; 
	private int lowF;
	private int highF;
	private int highC;
	private int lowC;
	private int low;
	private int high;
	private String forecast;
	private String forecastImg; 
	private List<String> conditions; 
	
	// GETTERS AND SETTERS 
	public String getForecastImg() {
		return forecastImg;
	}
	public void setForecastImg(String forecastImg) {
		this.forecastImg = forecastImg;
	}
	public List<String> getConditions() {
		return conditions;
	}
	public void setConditions(String forecast, int high, int low) {
		conditions = new ArrayList<String>(); 
		
		if(forecast.equals("snow")) {
			this.conditions.add("Pack Snowshoes"); 
			this.forecastImg = "snow.png"; 
		} else if (forecast.equals("cloudy")) {
			this.forecastImg = "cloudy.png"; 
		} else if (forecast.equals("partly cloudy")) {
			this.forecastImg = "partlyCloudy.png"; 
		} else if (forecast.equals("rain")) {
			this.conditions.add("Pack Rain Gear and Waterproof Shoes"); 
			this.forecastImg = "rain.png"; 
		} else if (forecast.equals("sunny")) {
			this.conditions.add("Pack Sunblock"); 
			this.forecastImg = "sunny.png"; 
		} else if (forecast.equals("thunderstorms")) {
			this.conditions.add("Seek Shelter and Avoid Hiking on Exposed Ridges"); 
			this.forecastImg = "thunderstorms.png"; 
		}
		
		if(high > 75 || low > 75) {
			this.conditions.add("Because of the high temperature, please bring an extra gallon of water."); 
		}
		
		if(high - low > 20) {
			this.conditions.add("Because of the temperate, please wear a breathable layer."); 
		}
		
		if (low < 20 || high < 20) {
			this.conditions.add("Because of the extreme cold temperatures, please be aware of frostbite."); 
		}
		
	}
	
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public int getFiveDayForecastValue() {
		return fiveDayForecastValue;
	}
	public void setFiveDayForecastValue(int fiveDayForecastValue) {
		this.fiveDayForecastValue = fiveDayForecastValue;
	}
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}

	public void setLow(int lowF) {
		this.lowF = lowF;
		this.lowC = (lowF - 32) * 5/9;
	}
	public void setHigh(int highF) {
		this.highF = highF;
		this.highC = (highF - 32) * 5/9;
	}
	public int getLowF() {
		return lowF;
	}
	public int getHighF() {
		return highF;
	}
	public int getLowC() {
		return lowC;
	}
	public int getHighC() {
		return highC;
	}
}
