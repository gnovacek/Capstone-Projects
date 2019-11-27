package com.techelevator.view;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedWriter;

public class Log {
	
	private String path;
	private File log;
	private FileWriter fileWriter;
	private PrintWriter printWriter;
	private DateFormat dateFormat;
	
	public Log(String path) throws IOException{
		this.path = path;
		log = new File(path);
		fileWriter = new FileWriter(log);
		printWriter = new PrintWriter(fileWriter);
		dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
		
	}
	
	public void logFeed(Balance balance, double amountFed) {
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		
		String feedMoney = "FEED MONEY: ";
		
		BigDecimal amountFedDecimal = new BigDecimal(amountFed);
		amountFedDecimal = amountFedDecimal.setScale(2, RoundingMode.HALF_DOWN);
		
		String currentBalance = balance.getBalanceString();
		String amountFedString = amountFedDecimal.toString();
		
		String logEntry = dateTime + " " + feedMoney + " " + amountFedString + " " + currentBalance;
		
		printWriter.println(logEntry);
	}
	
	public void logVend(Balance balance, Inventory inventory, String code) {
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		String itemName = inventory.getItemName(code);
		
		BigDecimal previousBalance = balance.getBalance().add(inventory.getItemPrice(code));
		String previousBalanceString = previousBalance.toString();
		
		String currentBalance = balance.getBalanceString();
		
		String logEntry = dateTime + " " + itemName + " " + previousBalanceString + " " + currentBalance;
		
		printWriter.println(logEntry);
	}
	
	public void logChange(Balance balance) {
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		
		String currentBalance = balance.getBalanceString();
		
		String logEntry = dateTime + " GIVE CHANGE: " + currentBalance + " $0.00";
		
		printWriter.println(logEntry);
	}
	
	public void printSalesReport(Inventory inventory) {
		
		String report = inventory.generateSalesReport();
		
		printWriter.print(report);
		printWriter.flush();
	}
	
	
	
}
