package com.techelevator.npgeek.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.npgeek.model.SurveyResponse;
import com.techelevator.npgeek.model.SurveyResult;
import com.techelevator.npgeek.model.SurveyResultDAO;


@Controller
public class SurveyController {

	@Autowired
	SurveyResultDAO surveyResultDao;
	
	
		// GET: /survey
		// Return the empty survey page
		@RequestMapping(path = "/survey", method = RequestMethod.GET)
		public String getSurveyPage(Model modelHolder) {
			
			if(!modelHolder.containsAttribute("survey")) {
				modelHolder.addAttribute("survey", new SurveyResult()); 
			}
			return "survey";
		}
		
		// POST: /survey
		// Validate the model and redirect to confirmation (if successful) or return
		// the
		// registration view (if validation fails)
		@RequestMapping(path = "/survey", method = RequestMethod.POST)
		public String submitSurvey(@Valid @ModelAttribute("survey") 
				SurveyResult surveyFormValues, 
				BindingResult result, 
				RedirectAttributes flash,
				@RequestParam String parkCode,
				@RequestParam String emailAddress,
				@RequestParam String state,
				@RequestParam String activityLevel) {
			
			if(result.hasErrors()) {
				flash.addFlashAttribute("survey", surveyFormValues); 
				flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "survey", result); 
				return "redirect:/survey";
			}
			
			flash.addFlashAttribute("message", "You have successfully registered.");
			
			
			SurveyResult post = new SurveyResult(); 
			
			post.setParkCode(parkCode);
			post.setEmailAddress(emailAddress);
			post.setState(state);
			post.setActivityLevel(activityLevel);
			
			surveyResultDao.save(post);
			return "redirect:/surveyConfirmation";
		}
		
		
		@RequestMapping(path="/surveyConfirmation", method=RequestMethod.GET)
		public String showSurveyConfirmationPage(ModelMap modelHolder) {
			
			List<SurveyResponse> posts = surveyResultDao.getAllPosts(); 
			modelHolder.put("allPosts", posts); 
			
			return "surveyConfirmation";
		}
		

	
}
