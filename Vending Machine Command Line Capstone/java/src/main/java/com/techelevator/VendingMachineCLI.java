package com.techelevator;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public static void main(String[] args) {
		//TODO: setup() to load in Inventory from CSV file
		
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		menu.run();
	}
}
