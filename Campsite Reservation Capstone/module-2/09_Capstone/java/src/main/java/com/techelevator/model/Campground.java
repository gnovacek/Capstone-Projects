package com.techelevator.model;

import java.math.BigDecimal;

public class Campground {

	private Long campgroundId;
	private int parkID;
	private String name; 
	private String openingMonth; 
	private String closingMonth; 
	private BigDecimal dailyFee;
	
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getParkID() {
		return parkID;
	}
	public void setParkID(int parkID) {
		this.parkID = parkID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpeningMonth() {
		return openingMonth;
	}
	public void setOpeningMonth(String openingMonth) {
		this.openingMonth = openingMonth;
	}
	public String getClosingMonth() {
		return closingMonth;
	}
	public void setClosingMonth(String closingMonth) {
		this.closingMonth = closingMonth;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	public String toString() {
		return name;
	}
}
