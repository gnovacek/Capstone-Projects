package com.techelevator.view;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test; 
import org.junit.Assert; 

public class BalanceTest {
	
	Balance balance = new Balance(); 
	
	@Before
	public void setup() {
		balance = new Balance(); 
	}
	
	@Test
	public void get_balance_string_returns_$_string() {
		String returnValue = balance.getBalanceString(); 
		
		Assert.assertEquals("$0.00", returnValue);
		
	}
	
	@Test public void get_balance_returns_big_decimal_value() {
		BigDecimal returnValue = balance.getBalance(); 
		
		BigDecimal expectedValue = new BigDecimal("0.00"); 
		
		Assert.assertEquals(expectedValue, returnValue);
	}
	
	@Test public void get_balance_double_returns_double_value() {
		double returnValue = balance.getBalanceDouble(); 
	 
		
		Assert.assertEquals(0.00, returnValue, 0.001);
	}
	
	@Test public void feed_money_adds_to_balance() {
		balance.feedMoney(10);
	 
BigDecimal returnValue = balance.getBalance(); 
		
		BigDecimal expectedValue = new BigDecimal("10.00"); 
		
		Assert.assertEquals(expectedValue, returnValue);
	}
	
@Test public void subtract_balance() {
		
	BigDecimal price = new BigDecimal("2.50");
		balance.feedMoney(10);
		balance.subtractBalance(price);
	 
BigDecimal returnValue = balance.getBalance(); 
		
		BigDecimal expectedValue = new BigDecimal("7.50"); 
		
		Assert.assertEquals(expectedValue, returnValue);
	}
	
	@Test public void reset_balance_back_to_zero() {
		
		balance.feedMoney(10);
		balance.resetBalance();
	 
BigDecimal returnValue = balance.getBalance(); 
		
		BigDecimal expectedValue = BigDecimal.ZERO; 
		
		Assert.assertEquals(expectedValue, returnValue);
	}
	
	
	@Test public void make_change_test() {
		BigDecimal price = new BigDecimal("9.50");
		
		balance.feedMoney(10);
		balance.subtractBalance(price);
		String returnValue = balance.makeChangeTest();
		
String expectedValue = "You get back 2 quarters and 0 dimes and 0 nickels and 0 pennies."; 
		
		Assert.assertEquals(expectedValue, returnValue);
	}
	
	
}
