package com.techelevator.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.view.Menu;

public class MenuTest {

	private ByteArrayOutputStream output;

	@Before
	public void setup() {
		output = new ByteArrayOutputStream();
		
	}

	@Test
	public void menu_state_starts_successfully() {
		int startingState = 0;
		
		Menu mainMenu = getMenuForTesting();
		
		int mainMenuState = mainMenu.getState();
		
		Assert.assertEquals(startingState, mainMenuState);
	}

	@Test
	public void returns_object_corresponding_to_user_choice() {
		String expected = "2";
		String[] options = new String[] { "1", expected, "3" };
		Menu menu = getMenuForTestingWithUserInput("2\n");

		String result = menu.getMenuChoice(options);

		Assert.assertEquals(expected, result);
	}


	private Menu getMenuForTestingWithUserInput(String userInput) {
		ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
		return new Menu(input, output);
	}

	private Menu getMenuForTesting() {
		return getMenuForTestingWithUserInput("1\n");
	}
}
