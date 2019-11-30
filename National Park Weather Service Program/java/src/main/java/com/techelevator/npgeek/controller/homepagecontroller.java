package com.techelevator.npgeek.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.ParkDAO;


@Controller
public class homepagecontroller {

	
	@Autowired
	ParkDAO parkDAO;
	
	
	@RequestMapping(path="/",method=RequestMethod.GET)
	public String displayHomePage(ModelMap modelHolder) {
		List<Park>newParkList = parkDAO.getAllParks() ;
		modelHolder.put("parks", newParkList);
		return "homePage";
	}
}
