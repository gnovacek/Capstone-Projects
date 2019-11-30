package com.techelevator.model;

import java.util.List;

public interface ParkDAO {

	List<Park> getAllAvailableParks();
	
	
	Park getParkInformation(String parkName);
}
