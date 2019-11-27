package com.techelevator.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;
	private int menuState;
	private Inventory inventory;
	private Balance balance;
	private Log log;
	
	//menu state: corresponds to index of output array
	private final int MAIN_MENU_STATE = 0;
	private final int PURCHASE_MENU_STATE = 1;
	private final int DISPLAY_ITEMS_STATE = 2;
	private final int FEED_MONEY_STATE = 3;
	private final int SELECT_PRODUCT_STATE = 4;
	private final int FINISH_TRANSACTION_STATE = 5;
	
	//Main Menu
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit"; 
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, null };

	
	//Purchase Menu
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money"; 
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product"; 
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction"; 
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};
	
	//other prompts
	private static final String FEED_MONEY_PROMPT = "Enter the amount of money you'd like to feed the machine (whole dollars only) >>> ";
	private static final String PRODUCT_PROMPT = "Select your product using its location code >>> ";
	private static final String MENU_PROMPT = "\nPlease choose an option >>> ";
	
	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
		this.menuState = MAIN_MENU_STATE;
		inventory = new Inventory();
		balance = new Balance();
		try {
			log = new Log("log.txt");
		} catch (IOException e) {
			System.out.println("IO Issue!");
		}
		out.println("Welcome to the Vendo-Matic 800!");
		
	}
	
	//main loop. logic based on state and input occurs in helper methods
	public void run() {
		while(true) {
			print();
			String input = getInput();
			process(input);
		}
	}
		
	//calls appropriate print method based on state
	public void print() {
		out.println("\n***********************************");
		if (menuState == MAIN_MENU_STATE) {
				
			displayMenuOptions(MAIN_MENU_OPTIONS);
				
		} else if (menuState == PURCHASE_MENU_STATE) {
				
			displayMenuOptions(PURCHASE_MENU_OPTIONS);
				
		} else if (menuState == DISPLAY_ITEMS_STATE) {
				
			inventory.displayItems();
				
				
		} else if (menuState == FEED_MONEY_STATE) {
				
			out.println(FEED_MONEY_PROMPT);
				
		} else if (menuState == SELECT_PRODUCT_STATE) {
				
			inventory.displayItemsForSale();
			out.println("all items displayed here!");
			out.println(PRODUCT_PROMPT);
				
		} else if (menuState == FINISH_TRANSACTION_STATE) {
				
			out.println("Finishing transaction");
				
		}
		out.flush();
	}
		
	//prompt user for input.  validate input based on state(location in menu and type of info requested)
	//always returns string, future method will parse if needed
	public String getInput() {
		String input = "";
			
		if (menuState == MAIN_MENU_STATE) {
				
			input = getMenuChoice(MAIN_MENU_OPTIONS);
				
		} else if (menuState == PURCHASE_MENU_STATE) {
			
			input = getMenuChoice(PURCHASE_MENU_OPTIONS);
				
		} else if (menuState == FEED_MONEY_STATE) {
				
			input = getIntFromUserInput();
				
		} else if (menuState == SELECT_PRODUCT_STATE) {
				
			input = getProductCodeFromUserInput();
				
		}
			
		return input;
	}
		
	//MAIN SWITCHBOARD! take action based on current location/input and update state for next loop
	public void process(String input) {
		if (menuState == MAIN_MENU_STATE) {
			int choice = Integer.parseInt(input);
				
			if(choice == 1) {
				menuState = DISPLAY_ITEMS_STATE;
			} else if (choice == 2) {
				menuState = PURCHASE_MENU_STATE;
			} else if (choice == 3) {
				System.exit(0);
			} else if (choice == 4) {
				DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss-a");
				Date date = new Date();
				String dateTime = dateFormat.format(date);
					
				String salesPath = "Sales-Report-" + dateTime + ".csv";
				Log salesReport;
				try {
					salesReport = new Log(salesPath);
					salesReport.printSalesReport(inventory);
				} catch (IOException e) {
					out.println("Invalid File Thing!");
					e.printStackTrace();
				}
				
			}
			
		} else if (menuState == PURCHASE_MENU_STATE) {
			int choice = Integer.parseInt(input);
				
			if (choice == 1) {
				menuState = FEED_MONEY_STATE;
			} else if (choice == 2) {
				menuState = SELECT_PRODUCT_STATE;
			} else if (choice == 3) {
				menuState = FINISH_TRANSACTION_STATE;
			}
				
		} else if (menuState == DISPLAY_ITEMS_STATE) {
			menuState = MAIN_MENU_STATE;
				
			} else if (menuState == FEED_MONEY_STATE) {
				
				int newMoney = Integer.parseInt(input);
				balance.feedMoney(newMoney);
				double newMoneyDouble = (double)newMoney;
				log.logFeed(balance, newMoneyDouble);
				
				out.println("Adding " + newMoney + "dollars to your balance!");
				menuState = PURCHASE_MENU_STATE;
				
			} else if (menuState == SELECT_PRODUCT_STATE) {
				
				inventory.vend(input, balance);
				log.logVend(balance, inventory, input);
				
				menuState = PURCHASE_MENU_STATE;
				
			} else if (menuState == FINISH_TRANSACTION_STATE) {
				balance.makeChange();
				log.logChange(balance);
				balance.resetBalance();
				out.println("Your balance is now " + balance.getBalanceString());
				menuState = MAIN_MENU_STATE;
				
			}
		}
		
		
		//helper input methods - loop, validate, return
		public String getMenuChoice(String[] options) {
			String choice = null;
		
			while (choice == null) {
			
				String userInput = in.nextLine();
			
				try {
					int selectedOption = Integer.parseInt(userInput);
					if (selectedOption > 0 && selectedOption <= options.length) {
					choice = Integer.toString(selectedOption);
					}
				} catch (NumberFormatException e) {
				// eat the exception, an error message will be displayed below since choice will be null
				}
				if (choice == null) {
					out.println("\n*** " + userInput + " is not a valid option ***\n");
					out.println(MENU_PROMPT);
					out.flush();
				}
			}
			return choice;
		}
	
		public String getIntFromUserInput() {
			String choice = null;
		while (choice == null) {
			
			String userInput = in.nextLine();
			try {
				int selectedOption = Integer.valueOf(userInput);
				if (selectedOption > 0) {
					choice = Integer.toString(selectedOption);
				}
			} catch (NumberFormatException e) {
				// eat the exception, an error message will be displayed below since choice will be null
			}
			if (choice == null) {
				out.println("\n*** " + userInput + " is not a valid option ***\n");
				out.println(FEED_MONEY_PROMPT);
				out.flush();
				
			}
		}
		return choice;
	}
	
	public String getProductCodeFromUserInput() {
		String choice = null;
		while (choice == null) {
			
			String userInput = in.nextLine();
			try {
				String selectedOption = userInput;
				String firstChar = selectedOption.substring(0,1);
				String secondChar = selectedOption.substring(1,2);
				if (firstChar.matches("[A-Z]") && secondChar.matches("\\d")) {
					choice = selectedOption;
				}
			} catch (NumberFormatException e) {
				// eat the exception, an error message will be displayed below since choice will be null
			}
		
			if (choice == null) {
				out.println("\n*** " + userInput + " is not a valid option ***\n");
				out.println(PRODUCT_PROMPT);
				out.flush();
			}
		}
		return choice;
	}

	public void displayMenuOptions(String[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			if (options[i] != null) {
				out.println(optionNum + ") " + options[i]);
			}
		}
		
		if(menuState == PURCHASE_MENU_STATE) {
			out.println();
			out.println("Current Money Provided: " + balance.getBalanceString());
			
		}
		out.println(MENU_PROMPT);
		out.flush();
	}
	
	//FOR TESTING ONLY
	public int getState() {
		return menuState;
	}
	
	
}
