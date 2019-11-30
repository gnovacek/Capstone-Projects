package com.techelevator.npgeek.model;

public class Conversion {

	private static double temperature;

	public static double convertCtoF(double c) {
		temperature = ((c * 1.8) + 32);
		return temperature;
	}

	public static double convertFtoC(double f) {
		temperature = ((f - 32) * 0.556);
		return temperature;
	}

	public static double getTemperature() {
		return temperature;
	}

	public static void setTemperature(double temperature) {
		Conversion.temperature = temperature;
	}

}
