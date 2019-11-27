package com.techelevator.view;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VendingMachineItem {
	private String name; 
	private BigDecimal price; 	
	private String type; 
	private int numberSold; 
	// type can be beverage, chips, candy, or gum  
	
	public VendingMachineItem(String name, BigDecimal price, String type) {
		this.name = name; 
		this.price = price; 
		this.price = this.price.setScale(2, RoundingMode.HALF_DOWN);
		this.type = type; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public double getPriceDouble() {
		return price.doubleValue();
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getNumberSold() {
		return numberSold; 
	}
	
	public void recordSale() {
		this.numberSold++; 
	}
	
	
}
