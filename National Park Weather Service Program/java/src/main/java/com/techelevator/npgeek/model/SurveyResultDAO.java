package com.techelevator.npgeek.model;

import java.util.List;



public interface SurveyResultDAO {

	List<SurveyResponse> getAllPosts();

	void save(SurveyResult post);
	
	
}
