package com.techelevator.npgeek.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class SurveyResult {

	private Long surveyId; 
	
	
	@NotNull(message="Picking a Favorite Park is Required")
	private String parkCode;
	
	@NotBlank(message="Email is Required")
	@Email(message="Email must be a valid email address")
	private String emailAddress; 
	
	@NotNull(message="State is Required")
	private String state;
	
	@NotNull(message="Physical Activity Level is Required")
	private String activityLevel;
	
	// GETTERS AND SETTERS
	
	public Long getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getActivityLevel() {
		return activityLevel;
	}
	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}
	
	
	
	
}
