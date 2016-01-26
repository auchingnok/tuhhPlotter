package de.tuhh.diss.plotbot;

import lejos.nxt.LCD;

public class MainDisplay { //size 0..15, 0..7
	//the properties below are in array form; each element occupies one row
	private static String[] header = {}; //elements that are stationary
	private static String[] options = {}; //items that are selected
	private static int currentOptionId = 0; //stores the index of the current option in array
	private static String[] varNames = {}; //name of variable under monitoring
	private static String[] varReadings = {}; //real time value of variable. 3 digits
	private static String[] userInputInfo = {}; //tell user what to do

	public MainDisplay() {}

	public int getChoice() { //return selected option to outside
		return currentOptionId;
	}

	private static void update() { //output all information to the screen
		LCD.clear();
		//output all the info stored by for-loop every string arrays
		//for options, add > or - symbols before them, according to currentOptionId	
		int y=0;
		for(int i=0; i<header.length;i++){
			LCD.drawString(header[i], 0, y);
			y++;
		}
		for(int i=0; i<options.length;i++){
			LCD.drawString(options[i], 1, y);
			if(currentOptionId == i)
				LCD.drawString(">", 0, y);
			else
				LCD.drawString("-", 0, y);
			y++;
		}
		for(int i=0; i<varNames.length;i++){
			LCD.drawString("["+varNames[i]+"="+varReadings[i]+"]", 0, y);
			y++;
		}
		for(int i=0; i<userInputInfo.length;i++){
			LCD.drawString(userInputInfo[i], 0, y);
			y++;
		}
	}

	private static void reset() {
		//remove all buttons listeners
		//set all properties to a zero length array;
		currentOptionId = 0;
		header = new String[0];
		options = new String[0];
		varNames = new String[0];
		varReadings = new String[0];
		userInputInfo = new String[0];
	}

	public static void setReadings(int index, int val) {
		//set the corresponding values and names
		//update screen
		String valst=Integer.toString(val);
		varReadings[index] = valst;
		update();
	}

	public static void setChosenOption(int choice) {
		currentOptionId = choice;
		update();
	}
	//below are methods to display each menus

	public static void welcomeScreen() {
		reset();
		header = new String[6];
		userInputInfo = new String[1];
		currentOptionId = 0;
		header[0] = "Hello";
		header[1] = "I am Plotbot.";
		header[2] = "";
		header[3] = "";
		header[4] = "";
		header[5] = "";
		userInputInfo[0] = "ENT: Continue";
		update();		
	}
	
	//setup pen 
	
	public static void setupPen() {
		reset();
		header = new String[3];
		varNames = new String[1];
		varReadings = new String[1];
		userInputInfo = new String[3];
		header[0] = "Setup Pen";
		header[1] = "Calibrate Zero Pos";
		header[2] = "";
		varNames[0] = "tacho";
		userInputInfo[0] = "";
		userInputInfo[1] = "";
		userInputInfo[2] = "Please Wait...";
		update();
	}
	
	//setup arm
	
	public static void setupArm() {
		reset();
		header = new String[3];
		varNames = new String[1];
		varReadings = new String[1];
		userInputInfo = new String[4];
		header[0] = "Setup Arm";
		header[1] = "";
		header[2] = "Set Zero Pos";
		varNames[0] = "tacho";
		userInputInfo[0] = "";
		userInputInfo[1] = "";
		userInputInfo[2] = "ENT: Calibrate";
		userInputInfo[3] = "ESC: Skip";
		update();
	}
	
	public static void setupArmWait() {
		userInputInfo[2] = "Please Wait...";
		userInputInfo[3] = "";
		update();
	}
	
	//setup light
	
	public static void setupLight() {
		reset();
		header = new String[3];
		varNames = new String[1];
		varReadings = new String[1];
		userInputInfo = new String[4];
		header[0] = "Setup Y Pos";
		header[1] = "";
		header[2] = "";
		varNames[0] = "read";
		userInputInfo[0] = "";
		userInputInfo[1] = "";
		userInputInfo[2] = "ENT: Calibrate";
		userInputInfo[3] = "";
		update();
	}

	public static void plotMenu() {
		reset();
		header = new String[1];
		options = new String[2];
		userInputInfo = new String[2];
		header[0] = "Plot Menu";
		options[0] = "plot Rect";
		options[1] = "plot TUHH";
		userInputInfo[0] = "L,R: Scroll";
		userInputInfo[1] = "ENT: Plot";
		update();
	}
}
