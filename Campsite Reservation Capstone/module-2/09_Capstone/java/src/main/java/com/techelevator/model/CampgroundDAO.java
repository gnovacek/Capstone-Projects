package com.techelevator.model;

import java.util.List;

public interface CampgroundDAO {
	
	List<Campground> getAvailableCampgrounds(Park park);

}
