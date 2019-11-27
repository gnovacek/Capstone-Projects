package com.techelevator.view;

import java.math.BigDecimal;

public class Balance {
	private BigDecimal balance;

	public Balance() {
		this.balance = new BigDecimal("0.00");
	}
	
	//OVERLOADED CONSTRUCTOR FOR TESTING ONLY 
	public Balance(String str) {
		balance = new BigDecimal("10.00");
	}
	

	// METHODS
	public String getBalanceString() {
		return "$" + this.balance.toString();
	}
	
	public BigDecimal getBalance() {
		return this.balance;
	}
	
	public double getBalanceDouble() {
		return this.balance.doubleValue();
	}

	public void feedMoney(int newDollars) {
		BigDecimal addToBalance = new BigDecimal(newDollars);

		this.balance = this.balance.add(addToBalance);
	}

	public void subtractBalance(BigDecimal price) {
		this.balance = this.balance.subtract(price); 
	}
	
	public void makeChange() {
		double balanceDub = this.getBalanceDouble();
		double numberOfPenniesDub = balanceDub * 100; 
		int numberOfPennies = (int)numberOfPenniesDub; 
		
		int numberOfQuarters = numberOfPennies/25; 
		numberOfPennies = numberOfPennies % 25; 

		int numberOfDimes = numberOfPennies/10; 
		numberOfPennies = numberOfPennies % 10; 
		
		int numberOfNickels = numberOfPennies/5;
		numberOfPennies = numberOfPennies % 5; 
				
		System.out.println("You get back " + numberOfQuarters + " quarters and " + numberOfDimes + " dimes and " + numberOfNickels + " nickels and " + numberOfPennies + " pennies.");
		
	}
	
	public void resetBalance() {
		this.balance = BigDecimal.ZERO;
	}
	
// FOR TESTING ONLY
	public String makeChangeTest() {
		double balanceDub = this.getBalanceDouble();
		double numberOfPenniesDub = balanceDub * 100; 
		int numberOfPennies = (int)numberOfPenniesDub; 
		
		int numberOfQuarters = numberOfPennies/25; 
		numberOfPennies = numberOfPennies % 25; 

		int numberOfDimes = numberOfPennies/10; 
		numberOfPennies = numberOfPennies % 10; 
		
		int numberOfNickels = numberOfPennies/5;
		numberOfPennies = numberOfPennies % 5; 
				
		return "You get back " + numberOfQuarters + " quarters and " + numberOfDimes + " dimes and " + numberOfNickels + " nickels and " + numberOfPennies + " pennies.";
		
	}

}

  