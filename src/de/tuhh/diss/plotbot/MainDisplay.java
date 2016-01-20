package de.tuhh.diss.plotbot;

import lejos.nxt.LCD;

public class MainDisplay { //size 0..15, 0..7
	//main display
	
	//the properties below are in array form; each element occupies one row
	private String[] header = {}; //elements that are stationary
	private String[] options = {}; //items that are selected
	private int currentOptionId = 0; //stores the index of the current option in array
	private String[] varNames = {}; //name of variable under monitoring
	private String[] varReadings = {}; //real time value of variable. 3 digits
	private String[] userInputInfo = {}; //tell user what to do
	
	public MainDisplay() {
		LCD.clear();
	}
	
	public int getChoice() { //return selected option to outside
		return currentOptionId;
	}
	
	public void update() { //output all information to the screen
		LCD.clear();
		//output all the info stored by for-loop every string arrays
		//for options, add > or - symbols before them, according to currentOptionId
	}
	
	private void resetProperties() {
		//set all properties to a zero length array;
	}
	
	private void selectPreviousOption() {
		//select previous option, update option variable
		//if no previous option, select the last option
		//update the screen
	}
	
	private void selectNextOption() {
		//select next option, update option variable
		//if no next option, select the first option
		//update the screen
	}
	
	private void enableLeftRightScroll() { 
		Listeners.resetButtons();
		Listeners.left.setPressedResponse(new Runnable() {public void run() {
			selectPreviousOption();
		}});
		Listeners.left.setPressedResponse(new Runnable() {public void run() {
			selectNextOption();
		}});
	}
	
	private void enableEnterScroll() { //enable scrolling the list by pressing enter only
		Listeners.resetButtons();
		Listeners.enter.setPressedResponse(new Runnable() {public void run() {
			selectNextOption();
		}});
	}
	
	public void setReadings(int index, String name, int val) {
		//set the corresponding values and names
		//update screen
	}
	
	//below are methods to display each menus
	//an example of showing a menu
	public void showSampleMenu() {
		resetProperties();
		header = new String[2];
		options = new String[3];
		userInputInfo = new String[3];
		currentOptionId = 0;
		header[0] = "line 1";
		header[1] = "line 2";
		options[0] = "option1";
		options[1] = "option2";
		options[2] = "option3";
		userInputInfo[0] = ""; //blank row example
		userInputInfo[1] = "info1";
		userInputInfo[2] = "info2";
		update();
		enableLeftRightScroll();
	}
	
	public void showSetupMotor(String part, int step) {
		
	}
	
}
