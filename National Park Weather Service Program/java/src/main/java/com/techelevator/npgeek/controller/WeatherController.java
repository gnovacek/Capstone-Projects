package com.techelevator.npgeek.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionAttributeStore;

import com.techelevator.npgeek.model.Conversion;
import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.ParkDAO;
import com.techelevator.npgeek.model.Weather;
import com.techelevator.npgeek.model.WeatherDAO;

@Controller
public class WeatherController {

	@Autowired
	WeatherDAO weatherDAO;

	@Autowired
	ParkDAO parkDAO;

	// GET: weather based on parkDetail selection
	@RequestMapping(path = "/parkDetail/{parkCode}", method = RequestMethod.GET)
	public String displayParkDetailWeather(ModelMap modelHolder, HttpSession session, @PathVariable String parkCode) {
		

		Park newPark = parkDAO.getParkByCode(parkCode);
		modelHolder.addAttribute("park", newPark);
		modelHolder.addAttribute("weather", weatherDAO.getFiveDayForecast(parkCode));
		if(session.getAttribute("weatherScale") == null) {
			session.setAttribute("weatherScale", "F");
		}
		
		String tempUnit = (String)session.getAttribute("weatherScale");
		
		if (tempUnit != null) {
			if (tempUnit.equals("C")) {
				session.setAttribute("weatherScale", "C");
			} else if (tempUnit.equals("F")) {
				session.setAttribute("weatherScale", "F");
			}

		}

		return "weather";
	}

	@RequestMapping(path = "parkDetail/{parkCode}/weather", method = RequestMethod.GET)
	public String getWeatherPage(ModelMap modelHolder, HttpSession session, @PathVariable String parkCode) {

		String convert = (String) session.getAttribute("convert");

		if (convert == null) {
			convert = "F";
			session.setAttribute("convert", convert);
		}

		List<Weather> fiveDayForecast = weatherDAO.getFiveDayForecast(parkCode);
			
			Park newPark = parkDAO.getParkByCode(parkCode); 

		if (convert.equals("C")) {
			for (Weather temps : fiveDayForecast) {
				int tempInt;

				tempInt = (int) Conversion.convertFtoC(temps.getHighC());
				temps.setHigh(tempInt);
				tempInt = (int) Conversion.convertFtoC(temps.getLowC());
				temps.setLow(tempInt);
			}
		}

		modelHolder.addAttribute("weather", fiveDayForecast);

//				modelHolder.put("park", newPark); 

		return "weather";
	}

	@RequestMapping(path = "/parkDetail/{parkCode}/weather", method = RequestMethod.POST)
	public String showParkDetailWithConversion(@PathVariable String parkCode, @RequestParam String convert,
			HttpSession session, ModelMap modelHolder) {
		session.setAttribute("weatherScale", convert);

		return "redirect:/parkDetail/" + parkCode;

	}

}
