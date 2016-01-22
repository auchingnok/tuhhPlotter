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

	private void reset() {
		//remove all buttons listeners
		//set all properties to a zero length array;
		currentOptionId = 0;
		header = new String[0];
		options = new String[0];
		varNames = new String[0];
		varReadings = new String[0];
		userInputInfo = new String[0];
	}

	public void selectPreviousOption() {
		//select previous option, update option variable
		//if no previous option, select the last option
		//update the screen
		currentOptionId--;
		if(currentOptionId < 0)
			currentOptionId=options.length-1;
		update();
	}

	public void selectNextOption() {
		//select next option, update option variable
		//if no next option, select the first option
		//update the screen
		currentOptionId++;
		if(currentOptionId > options.length-1)
			currentOptionId=0;
		update();
					
	}

	public static void setReadings(int index, int val) {
		//set the corresponding values and names
		//update screen
		String valst=Integer.toString(val);
		varReadings[index] = valst;
		update();
	}

	//below are methods to display each menus

	public void welcomeScreen() {
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

	public void mainMenu() {
		reset();
		header = new String[1];
		options = new String[4];
		userInputInfo = new String[3];
		header[0] = "Main Menu";
		options[0] ="Setup";
		options[1] = "Manual Control";
		options[2] = "Plot";
		options[3] = "Reset";
		userInputInfo[0] = ""; //blank row example
		userInputInfo[1] = "L,R: Scroll";
		userInputInfo[2] = "ENT: Continue";
		update();
	}
	public void setupMenu() {
		reset();
		header = new String[1];
		options = new String[3];
		userInputInfo = new String[3];
		currentOptionId = 0;
		header[0] = "Setup Menu";
		options[0] ="Pen";
		options[1] = "Arm";
		options[2] = "Light Sensor";
		userInputInfo[0] = ""; //blank row example
		userInputInfo[1] = "L,R: Scroll";
		userInputInfo[2] = "ENT: Continue";
		update();
	}
	
	//setup pen 
	
	public void setupPen1_3() {
		reset();
		header = new String[3];
		varNames = new String[1];
		varReadings = new String[1];
		userInputInfo = new String[4];
		header[0] = "Setup Pen";
		header[1] = "Step 1/3";
		header[2] = "Set zero pos";
		varNames[0] = "tacho";
		userInputInfo[0] = "";
		userInputInfo[1] = "L: Move Down";
		userInputInfo[2] = "R: Move Up";
		userInputInfo[3] = "ENT: Accept";
		update();
	}
	public void setupPen2_3() {
		header[1] = "Step 2/3";
		header[2] = "Set Touch pos";
		update();
	}
	public void setupPen3_3() {
		header[1] = "Step 3/3";
		header[2] = "Set Free pos";
		update();
	}
	
	//setup arm
	
	public void setupArm1_3() {
		reset();
		header = new String[3];
		varNames = new String[1];
		varReadings = new String[1];
		userInputInfo = new String[4];
		header[0] = "Setup Arm";
		header[1] = "Step 1/3";
		header[2] = "Set zero pos";
		varNames[0] = "tacho";
		userInputInfo[0] = "";
		userInputInfo[1] = "L: Move Left";
		userInputInfo[2] = "R: Move Right";
		userInputInfo[3] = "ENT: Accept";
		update();
	}
	public void setupArm2_3() {
		header[1] = "Step 2/3";
		header[2] = "Set Limit 1";
		update();
	}
	public void setupArm3_3() {
		header[1] = "Step 3/3";
		header[2] = "Set Limit 2";
		update();
	}
	
	//setup light
	
	public void setupLight1_2() {
		reset();
		header = new String[3];
		varNames = new String[1];
		varReadings = new String[1];
		userInputInfo = new String[4];
		header[0] = "Setup Light";
		header[1] = "Step 1/2";
		header[2] = "Set Limit 1";
		varNames[0] = "read";
		userInputInfo[0] = "ESC: ToggleLight";
		userInputInfo[1] = "L: Forward";
		userInputInfo[2] = "R: Backward";
		userInputInfo[3] = "ENT: Accept";
		update();
	}
	public void setupLight2_2() {
		header[1] = "Step 2/2";
		header[2] = "Set Limit 2";
		update();
	}
	public void manualControl() {
		reset();
		header = new String[1];
		options = new String[3];
		userInputInfo = new String[4];
		header[0] = "Manual Control";
		options[0] = "Move X";
		options[1] = "Move Y";
		options[2] = "Move Z";
		userInputInfo[0] = "L: -";
		userInputInfo[1] = "R: +";
		userInputInfo[2] = "ENT: Scroll";
		userInputInfo[3] = "ESC: Finish";
		update();
		Listeners.enter.setPressedResponse(new Runnable() {public void run() {
			selectNextOption();
			switch (currentOptionId) {
			case 0: Plotbot.pen.enableManualMode();
			case 1: Plotbot.arm.enableManualMode();
			case 2: Plotbot.wheel.enableManualMode();
			}
		}});
	}
	public void plotMenu() {
		reset();
		header = new String[1];
		options = new String[4];
		userInputInfo = new String[3];
		header[0] = "Plot Menu";
		options[0] = "goto Pos1";
		options[1] = "goto Pos2";
		options[2] = "goto Pos3";
		options[3] = "goto Pos4";
		userInputInfo[0] = "L,R: Scroll";
		userInputInfo[1] = "ENT: Plot";
		userInputInfo[2] = "ECS: Cancel";
		update();
	}
	public void plot() {
		reset();
		header = new String[1];
		varNames = new String[3];
		varReadings = new String[3];
		userInputInfo = new String[4];
		currentOptionId = 0;
		header[0] = "Plotting Line";
		varNames[0] = "X";
		varNames[1] = "Y";
		varNames[2] = "Z";
		userInputInfo[0] = "L: Step Back";
		userInputInfo[1] = "R: Step Forth";
		userInputInfo[2] = "ENT:Pause";
		userInputInfo[3] = "ESC: Abort";
		update();
	}
	
	public void resetting() {
		reset();
		header = new String[1];
		header[0] = "Resetting...";
		update();
	}


}
