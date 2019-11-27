package com.techelevator.view;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Inventory {
	
	// this map won't change... will remain the same throughout the program run
	// these two maps are not private variables anymore strictly for testing purposes 
		 Map<String, VendingMachineItem> codeItemMap = new TreeMap<String, VendingMachineItem>(); 

		 Map<String, Integer> codeCountMap = new TreeMap<String, Integer>();
		 
		private static final int INITIAL_STOCK = 5;

	public Inventory() {
		readInventory();
	}
	// TEST CONSTRUCTOR -- Happy Halloween!
	public Inventory(String str) {

		codeCountMap = new TreeMap<String, Integer>();

		codeCountMap.put("A1", INITIAL_STOCK);
		codeCountMap.put("A2", INITIAL_STOCK);
		codeCountMap.put("B1", INITIAL_STOCK);
		codeCountMap.put("B1", INITIAL_STOCK);
		codeCountMap.put("C1", INITIAL_STOCK);
		
		codeItemMap = new TreeMap<String, VendingMachineItem>();
		
		BigDecimal candyCornPrice = new BigDecimal("0.25");
		VendingMachineItem CandyCorn = new VendingMachineItem("Candy Corn", candyCornPrice, "Candy");

		BigDecimal reesesPrice = new BigDecimal("2.05");
		VendingMachineItem Reeses = new VendingMachineItem("Reeses", reesesPrice, "Candy");

		BigDecimal mountainBooPrice = new BigDecimal("3.44"); 
		VendingMachineItem MountainBoo = new VendingMachineItem("Mountain Boo", mountainBooPrice, "Drink");
		
		BigDecimal ghostPepperPrice = new BigDecimal("2.77"); 
		VendingMachineItem GhostPepperCrisps = new VendingMachineItem("Ghost Pepper Crisps", ghostPepperPrice, "Chip");
		
		BigDecimal zombieCleanPrice = new BigDecimal("1.50"); 
		VendingMachineItem ZombieClean = new VendingMachineItem("Zombie Clean", zombieCleanPrice, "Gum");
	
		codeItemMap.put("A1", CandyCorn);
		codeItemMap.put("A2", Reeses);
		codeItemMap.put("B1", MountainBoo);
		codeItemMap.put("B2", GhostPepperCrisps);
		codeItemMap.put("C1", ZombieClean);
	}

	// breaking the file down by lines and then taking the indexes of certain things
	// and placing them in to the corresponding maps
	public void readInventory() {
		try {
			File file = new File("vendingmachine.csv");

			try (Scanner fileTxt = new Scanner(file)) {
				while (fileTxt.hasNextLine()) {
					String line = fileTxt.nextLine();
					String[] fullItemStats = line.split("\\|");
					codeCountMap.put(fullItemStats[0], INITIAL_STOCK);

					String name = fullItemStats[1];
					String type = fullItemStats[3];

					double priceInt = Double.parseDouble(fullItemStats[2]);
					BigDecimal price = new BigDecimal(priceInt);

					VendingMachineItem item = new VendingMachineItem(name, price, type);
					codeItemMap.put(fullItemStats[0], item);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	// METHODS
	public void decrementItemCount(String code) {
		int newCount = codeCountMap.get(code) - 1;
		codeCountMap.put(code, newCount);
	}

	public String getItemName(String code) {
		String itemName = codeItemMap.get(code).getName();
		return itemName;
	}

	public BigDecimal getItemPrice(String code) {
		BigDecimal itemPrice = codeItemMap.get(code).getPrice();
		return itemPrice;
	}

	public void displayItems() {
		Set<String> codes = codeItemMap.keySet();

		for (String c : codes) {
			String name = codeItemMap.get(c).getName();
			int stock = INITIAL_STOCK - codeItemMap.get(c).getNumberSold();

			if(stock == 0) {
				System.out.println(name + " - SOLD OUT");
			} else {
				System.out.println(name + " - " + stock + " left");
			}
		}
	}
	
	public void displayItemsForSale() {
		Set<String> codes = codeItemMap.keySet();

		for (String c : codes) {
			String name = codeItemMap.get(c).getName();
			BigDecimal price = codeItemMap.get(c).getPrice();

			System.out.println(c + ": " + name + " - $" + price);
		}
	}

	public void addToNumberSold(String code) {
		codeItemMap.get(code).recordSale();
	}

	public void vend(String code, Balance balance) {
		// checking to make sure the item exists 
		if (!codeCountMap.containsKey(code)) {
			System.out.println("Item not found!  Please make another selection.");
			return;
		}

		// checking to make sure there is enough inventory to process this vend
		int itemsLeft = codeCountMap.get(code);

		if (itemsLeft == 0) {
			System.out.println("We are sorry to report that the item that you selected is currently sold out! :(");
			return;
		}

		double itemPrice = codeItemMap.get(code).getPriceDouble();
		// get the balance from the balance Class
		if (itemPrice > balance.getBalanceDouble()) {
			System.out.println("You do not have enough money to make this purchase!");
			return;
		}

		System.out.println("Dispensing " + codeItemMap.get(code).getName() +
				". It cost " + codeItemMap.get(code).getPrice().toString() + 
				". You have " + balance.getBalanceString() + "remaining.");
		System.out.println(getMessage(code));
		decrementItemCount(code);

		BigDecimal price = codeItemMap.get(code).getPrice();

		// this method takes in the price (not the double itemPrice), so that we can do
		// precise math
		balance.subtractBalance(price);
		// based on the code, add 1 to the numberSold

		addToNumberSold(code);
	}

	public String getMessage(String code) {
		String type = codeItemMap.get(code).getType();

		if (type.equalsIgnoreCase("Chip")) {
			return "Crunch Crunch, Yum!";
		} else if (type.equalsIgnoreCase("Candy")) {
			return "Munch Munch, Yum!";
		} else if (type.equalsIgnoreCase("Drink")) {
			return "Glug Glug, Yum!";
		} else if (type.equalsIgnoreCase("Gum")) {
			return "Chew Chew, Yum!";
		}
		return "";
	}

	public String generateSalesReport() {
		String report = "";
		Set<String> codes = codeItemMap.keySet();
		
		double totalSales = 0; 
		
		for (String c : codes) {
			String name = codeItemMap.get(c).getName();
			double price = codeItemMap.get(c).getPriceDouble();
			int numberOfItemsSold = codeItemMap.get(c).getNumberSold();

			totalSales += price * numberOfItemsSold;

			report += name + " | " +  numberOfItemsSold + "\n";
		}
		
		BigDecimal salesDecimal = new BigDecimal(totalSales);
		salesDecimal = salesDecimal.setScale(2, RoundingMode.HALF_DOWN);
		String totalSalesString = salesDecimal.toString();
		
		report += "\n";
		report += "TOTAL SALES: $" + totalSalesString + "\n";
		
		return report;
		
	}
}

