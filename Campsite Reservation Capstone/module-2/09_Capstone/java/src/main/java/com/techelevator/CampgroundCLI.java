package com.techelevator;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;

public class CampgroundCLI {

	private TheRealMenu menu;
	private BasicDataSource dataSource;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;

	private PrintWriter out;
	private Scanner in;

	// Park Menu
	private static final String PARK_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String PARK_MENU_OPTION_SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String PARK_MENU_OPTION_RETURN_TO_MAIN = "Return to Previous Screen";
	private static final String[] PARK_MENU_OPTIONS = { PARK_MENU_OPTION_VIEW_CAMPGROUNDS,
			PARK_MENU_OPTION_SEARCH_FOR_RESERVATION, PARK_MENU_OPTION_RETURN_TO_MAIN };

	// Campgrounds Menu
	private static final String CAMPGROUNDS_MENU_OPTION_SEARCH_FOR_RESERVATION = "Search for Available Reservation";
	private static final String CAMPGROUNDS_MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] CAMPGROUNDS_MENU_OPTIONS = { CAMPGROUNDS_MENU_OPTION_SEARCH_FOR_RESERVATION,
			CAMPGROUNDS_MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN };

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI cli = new CampgroundCLI(dataSource);
		cli.run();

	}

	public CampgroundCLI(BasicDataSource dataSource) {
		this.dataSource = dataSource;
		this.menu = new TheRealMenu(System.in, System.out);
		// construct DAOs
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
	}

	public void run() {

		while (true) {
			printMainMenu();
		}
	}

	private void printMainMenu() {
		System.out.println();
		System.out.println("Welcome to the Super Totally Awesome Campground Reservation App!");
		System.out.println("****************************************************************");
		System.out.println();
		System.out.println("View Parks Interface");
		System.out.println("Select a Park for Further Details");

		List<Park> allParks = parkDAO.getAllAvailableParks();
		String[] allParkNames = new String[allParks.size() + 1];

		int i = 0;

		for (Park currentPark : allParks) {
			allParkNames[i] = currentPark.getName();
			i++;
		}

		allParkNames[i] = "Quit";

		String parkChoice = (String) menu.getChoiceFromOptions(allParkNames);

		if (parkChoice.equals("Quit")) {
			System.out.println();
			System.out.println("Thank you for using the Super Totally Awesome Campground Reservation App!");
			System.out.println("****************************************************************");
			System.out.println();
			System.exit(0);
		} else {
			parkMenu(parkDAO.getParkInformation(parkChoice));
		}
	}

	private void parkMenu(Park park) {
		System.out.println();
		System.out.println("****************************************************************");

		System.out.println(park.getName() + " National Park");
		System.out.printf("%-16s %s%n", "Location:", park.getLocation());
		System.out.printf("%-16s %s%n", "Established:", park.getEstablishDate());
		System.out.printf("%-16s %,d sq km%n", "Area:", park.getArea());
		System.out.printf("%-16s %,d%n", "Annual Visitors:", park.getVisitors());
		System.out.println();

		StringBuilder description = new StringBuilder(park.getDescription());
		int i = 0;
		while ((i = description.indexOf(" ", i + 60)) != -1) {
			description.replace(i, i + 1, "\n");
		}
		System.out.println(description.toString());

		String choice = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);

		if (choice.equals(PARK_MENU_OPTION_RETURN_TO_MAIN)) {
			printMainMenu();
		} else if (choice.equals(PARK_MENU_OPTION_VIEW_CAMPGROUNDS)) {
			campgroundMenu(park);
		} else if (choice.equals(PARK_MENU_OPTION_SEARCH_FOR_RESERVATION)) {
			reservationMenu(park);
		}
	}

	private void campgroundMenu(Park park) {
		System.out.println();
		System.out.println("****************************************************************");

		printCampgroundInformation(park);

		String choice = (String) menu.getChoiceFromOptions(CAMPGROUNDS_MENU_OPTIONS);

		if (choice.equals(CAMPGROUNDS_MENU_OPTION_SEARCH_FOR_RESERVATION)) {
			reservationMenu(park);
		} else if (choice.equals(CAMPGROUNDS_MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN)) {
			parkMenu(park);
		}
	}

	private void reservationMenu(Park park) {
		System.out.println();
		System.out.println("****************************************************************");
		System.out.println("Search for Campground Reservation");

		printCampgroundInformation(park);

		System.out.println();
		System.out.println("Which campground?");

		List<Campground> allCampgrounds = campgroundDAO.getAvailableCampgrounds(park);

		String[] allCampgroundNames = new String[allCampgrounds.size() + 1];
		Map<Integer, Campground> campgroundIdMap = new LinkedHashMap<Integer, Campground>();

		int i = 0;
		for (Campground campground : allCampgrounds) {
			allCampgroundNames[i] = campground.getName();
			campgroundIdMap.put(i, campground);
			i++;
		}

		allCampgroundNames[i] = "Cancel";

		int choice = menu.getIndexFromOptions(allCampgroundNames);

		if (choice == allCampgroundNames.length - 1) {
			System.exit(0);
		} else {
			selectDatesForReservation(campgroundIdMap.get(choice));
		}

	}

	private void selectDatesForReservation(Campground campground) {

		while (true) {
			Scanner scanner = new Scanner(System.in);

			System.out.println();
			System.out.println("What is the arrival date? yyyy-mm-dd ");

			String arrivalDate = scanner.nextLine();

			String[] aDate = arrivalDate.split("-");

			String arrivalYear = aDate[0];
			String arrivalMonth = aDate[1];
			String arrivalDay = aDate[2];

			int aYear = Integer.parseInt(arrivalYear);
			int aMonth = Integer.parseInt(arrivalMonth);
			int aDay = Integer.parseInt(arrivalDay);
			
			// additional month and day validation

			if (aMonth < 1 || aMonth > 12) {
				System.out.println("Invalid Month, please try again!");
				break;

			}
			if (aDay < 1 || aDay > 31) {
				System.out.println("Invalid Day, please try again!");
				break;

			}
			if (aDay > 30 && (aMonth == 2 || aMonth == 4 || aMonth == 6 || aMonth == 9 || aMonth == 11)) {
				System.out.println("Invalid Day, please try again!");
				break;

			} else if (aDay > 28 && (aMonth == 2)){
				System.out.println("Invalid Day, please try again!");
				break;
			}
			
			LocalDate arrivalLocalDate = LocalDate.of(aYear, aMonth, aDay);

			if (arrivalLocalDate.isBefore(LocalDate.now())) {
				System.out.println("Invalid Year, please select a date that is not in the past!");
				break;
			}
			
			getValidArrivalDate(arrivalDate, aMonth, aDay, campground);

			System.out.println();
			System.out.println("What is the departure date? yyyy-mm-dd ");

			String departureDate = scanner.nextLine();

			String[] dDate = departureDate.split("-");

			String departureYear = dDate[0];
			String departureMonth = dDate[1];
			String departureDay = dDate[2];

			int dYear = Integer.parseInt(departureYear);
			int dMonth = Integer.parseInt(departureMonth);
			int dDay = Integer.parseInt(departureDay);

			// additional month and day validation

			if (dMonth < 1 || dMonth > 12) {
				System.out.println("Invalid Month, please try again!");
					break;

						}
			if (dDay < 1 || dDay > 31) {
				System.out.println("Invalid Day, please try again!");
					break;

						}
			if (dDay > 30 && (dMonth == 2 || dMonth == 4 || dMonth == 6 || dMonth == 9 || dMonth == 11)) {
				System.out.println("Invalid Day, please try again!");
					break;

			} else if (dDay > 28 && (dMonth == 2)){
				System.out.println("Invalid Day, please try again!");
					break;
						}
						
			LocalDate departureLocalDate = LocalDate.of(dYear, dMonth, dDay);

			if (departureLocalDate.isBefore(LocalDate.now())) {
				System.out.println("Invalid Year, please select a date that is not in the past!");
				break;
			} else if (departureLocalDate.isBefore(arrivalLocalDate)) {
				System.out.println("Invalid Departure Date - you can't depart before you arrive!  You are wild!");
				break;
			}
			
			getValidDepartureDate(departureDate, dMonth, dDay, campground);

			List<Site> availableSites = siteDAO.getAvailableSites(campground, arrivalLocalDate, departureLocalDate);

			if (availableSites.size() == 0) {
				System.out.println("No sites are available for that time-frame.");
			}

			String[] options = new String[availableSites.size() + 1];
			int i = 0;
			Long daysBetween = ChronoUnit.DAYS.between(arrivalLocalDate, departureLocalDate);
			BigDecimal daysBetweenBD = new BigDecimal(daysBetween);
			// display Campsites
			System.out.printf("%-2s |%-8s |%-10s |%-10s |%-13s |%-7s |%-5s ", "", "Site No.", "Max Occup.",
					"Accessible", "Max RV Length", "Utility", "Cost   |");
			for (Site site : availableSites) {
				options[i] = String.format("|%-9s|%-11s|%-11s|%-14s|%-8s|$%-6s|", site.getSiteNumber(),
						site.getMaxOccupancy(), booleanToYesNo(site.isAccessible()), site.getMaxRvLength(),
						booleanToYesNo(site.isUtilities()), campground.getDailyFee().multiply(daysBetweenBD));
				i++;
			}
			options[i] = "Cancel";

			int choice = menu.getIndexFromOptions(options);

			if (choice == options.length - 1)
				break;
			else {
				createReservation(availableSites.get(choice), arrivalLocalDate, departureLocalDate);
//				break;
				run();
			}
		}
	}

	private void createReservation(Site site, LocalDate fromDate, LocalDate toDate) {
		while (true) {

			Scanner scanner = new Scanner(System.in);

			System.out.print("What name should the reservation be made under? ");
			String resName = scanner.nextLine();

			reservationDAO.bookReservation(site, fromDate, toDate, resName);

			Reservation ourRes = reservationDAO.getBookedReservation(site, fromDate, toDate, resName);
			Long resId = ourRes.getReservationId();

			System.out.println("The reservation has been made and the confirmation id is: " + resId);
			break;
		
		}
	}

	// helper method
	private void printCampgroundInformation(Park park) {
		List<Campground> allCampgrounds = campgroundDAO.getAvailableCampgrounds(park);

		System.out.println(park.getName() + " National Park Campgrounds");
		System.out.printf("%-32s %-10s %-10s %-8s%n", "Name", "Open", "Close", "Daily Fee");
		for (Campground camp : allCampgrounds) {
			int openMonthNum = Integer.parseInt(camp.getOpeningMonth());
			int closeMonthNum = Integer.parseInt(camp.getClosingMonth());
// trying to change the months into Strings and not 01, 02, etc. 
			System.out.printf("%-32s %-10s %-10s $%-8s%n", camp.getName(), Month.of(openMonthNum),
					Month.of(closeMonthNum), camp.getDailyFee().toPlainString());
		}
	}

	// the system hates the boolean input from the site, because of course it does
	private String booleanToYesNo(boolean input) {
		return (input) ? "Yes" : "No";
	}

	private void getValidArrivalDate(String arrivalDate, int aMonth, int aDay, Campground selectedCampground) {

//		LocalDate date;

		while (true) {
//			try {
//				LocalDate date = LocalDate.parse(arrivalDate); // parse input to date YYYY-MM-DD

//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY"); //to switch

//			} catch (DateTimeParseException e) {
//				System.out.println("Please enter a valid date!");
//				continue;
//			}
		
			if (arrivalDate.length() > 10) {
				System.out.println("Please enter a valid date!");
				break;
			} else {

				int openingNumber = Integer.parseInt(selectedCampground.getOpeningMonth());
				int closingNumber = Integer.parseInt(selectedCampground.getClosingMonth());

				if (aMonth >= openingNumber && aMonth <= closingNumber) {
					break;
				} else {
					System.out.println("This park is not open during this time!");
					selectDatesForReservation(selectedCampground);
					
				
				
				}
			}
		}
	}

	private void getValidDepartureDate(String departureDate, int dMonth, int dDay, Campground selectedCampground) {

//			LocalDate date;

		while (true) {
//			try {
//				LocalDate date = LocalDate.parse(departureDate); // parse input to date YYYY-MM-DD

//					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY"); //to switch

//			} catch (DateTimeParseException e) {
//				System.out.println("Please enter a valid date!");
//				continue;
//			}

			if (departureDate.length() > 10) {
				System.out.println("Please enter a valid date!");
			} else {

				int openingNumber = Integer.parseInt(selectedCampground.getOpeningMonth());
				int closingNumber = Integer.parseInt(selectedCampground.getClosingMonth());

				if (dMonth >= openingNumber && dMonth <= closingNumber) {
					break;
				} else {
					System.out.println("This park is not open during this time!");
					selectDatesForReservation(selectedCampground);
				
				}
			}

		}

	}
}