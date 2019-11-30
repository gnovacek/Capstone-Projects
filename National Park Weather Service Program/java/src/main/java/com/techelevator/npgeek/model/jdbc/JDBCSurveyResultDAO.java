package com.techelevator.npgeek.model.jdbc;

import com.techelevator.npgeek.model.SurveyResultDAO;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.npgeek.model.SurveyResponse;
import com.techelevator.npgeek.model.SurveyResult;

@Component
public class JDBCSurveyResultDAO implements SurveyResultDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSurveyResultDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<SurveyResponse> getAllPosts() {
		List<SurveyResponse> allSurveyResults = new ArrayList<>();

		String sqlSelectAllResults = "SELECT COUNT(survey_result.parkcode) AS surveyCount, park.parkname AS parkName " + 
				"FROM survey_result " + 
				"JOIN park ON park.parkcode = survey_result.parkcode " + 
				"GROUP BY parkName " + 
				"ORDER BY surveyCount DESC, parkName ASC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllResults);

		while (results.next()) {
			SurveyResponse newSurveyResponse = new SurveyResponse();
			
			newSurveyResponse.setCount(results.getInt("surveyCount")); 
			newSurveyResponse.setParkName(results.getString("parkName"));
	

			allSurveyResults.add(newSurveyResponse);

		}
		return allSurveyResults;
	}

	@Override
	public void save(SurveyResult post) {
		
		String sqlInsertReview = "INSERT INTO survey_result(parkcode, emailaddress, state, activitylevel) VALUES (?,?,?,?)";
		jdbcTemplate.update(sqlInsertReview, post.getParkCode(), post.getEmailAddress(),
				post.getState(), post.getActivityLevel());
		
	}



}
