package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	List<Site> getAvailableSites(Campground campground, LocalDate fromDate, LocalDate toDate);

}
