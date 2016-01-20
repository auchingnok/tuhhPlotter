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

	private void update() { //output all information to the screen
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

	private void resetProperties() {
		//set all properties to a zero length array;
		currentOptionId = 0;
		header = new String[0];
		options = new String[0];
		userInputInfo = new String[0];
		varNames = new String[0];
		varReadings = new String[0];

	}

	private void selectPreviousOption() {
		//select previous option, update option variable
		//if no previous option, select the last option
		//update the screen
		currentOptionId--;
		if(currentOptionId < 0)
			currentOptionId=options.length-1;
		update();
	}

	private void selectNextOption() {
		//select next option, update option variable
		//if no next option, select the first option
		//update the screen
		currentOptionId++;
		if(currentOptionId > options.length-1)
			currentOptionId=0;
		update();
					
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

	public void setReadings(int index, int val) {
		//set the corresponding values and names
		//update screen
		String valst=Integer.toString(val);
		varReadings[index] = valst;
		
	}


	//below are methods to display each menus
	//an example of showing a menu
	public void welcomeScreen() {
		resetProperties();
		header = new String[2];
		userInputInfo = new String[1];
		currentOptionId = 0;
		header[0] = "Hello";
		header[1] = "I am Plotbot.";
		userInputInfo[0] = "ENT: Continue";
		update();		
	}

	public void mainMenu() {
		resetProperties();
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
		enableLeftRightScroll();
	}
	public void setupMenu() {
		resetProperties();
		header = new String[1];
		options = new String[4];
		userInputInfo = new String[3];
		currentOptionId = 0;
		header[0] = "Setup Menu";
		options[0] ="Pen";
		options[1] = "Arm";
		options[2] = "Wheel";
		options[3] = "Light Sensor";
		userInputInfo[0] = ""; //blank row example
		userInputInfo[1] = "L,R: Scroll";
		userInputInfo[2] = "ENT: Continue";
		update();
		enableLeftRightScroll();
	}
	public void setupMotor1_3() {
		resetProperties();
		header = new String[4];
		userInputInfo = new String[3];
		varNames = new String[1];
		varReadings = new String[1];
		header[0] = "Setup Pen";
		header[1] = "Step 1/3";
		header[2] = "Set zero pos";
		varNames[0] = "";
		varReadings[0] = "";
		userInputInfo[0] = "L: Move Up";
		userInputInfo[1] = "R: Move Down";
		userInputInfo[2] = "ENT: Accept";
		update();
	}
	public void setupMotor2_3() {
		resetProperties();
		header = new String[4];
		userInputInfo = new String[3];
		varNames = new String[1];
		varReadings = new String[1];
		header[0] = "Setup Pen";
		header[1] = "Step 2/3";
		header[2] = "Set Touch pos";
		varNames[0] = "";
		varReadings[0] = "";
		userInputInfo[0] = "L: Move Up";
		userInputInfo[1] = "R: Move Down";
		userInputInfo[2] = "ENT: Accept";
		update();
		enableLeftRightScroll();
	}
	public void setupMotor3_3() {
		resetProperties();
		header = new String[1];
		options = new String[4];
		userInputInfo = new String[3];
		currentOptionId = 0;
		header[0] = "Setup Pen";
		options[0] = "Step 3/3";
		options[1] = "Set Free pos";
		String read=varNames.toString();
		options[2] = read;
		String xxx=varReadings.toString();
		options[3] = xxx;
		userInputInfo[0] = "L: Move Up";
		userInputInfo[1] = "R: Move Down";
		userInputInfo[2] = "ENT: Accept";
		update();
		enableLeftRightScroll();
	}
	public void setupLight1_2() {
		resetProperties();
		header = new String[1];
		options = new String[4];
		userInputInfo = new String[4];
		currentOptionId = 0;
		header[0] = "Setup Light";
		options[0] = "Step 1/2";
		options[1] = "Set Limit 1";
		String read=varNames.toString();
		options[2] = read;
		String xxx=varReadings.toString();
		options[3] = xxx;
		userInputInfo[0] = "L: Forward";
		userInputInfo[1] = "R: Backward";
		userInputInfo[2] = "ENT: Accept";
		userInputInfo[3] = "ESC: ToggleLight";
		update();
		enableLeftRightScroll();
	}
	public void setupLight2_2() {
		resetProperties();
		header = new String[1];
		options = new String[4];
		userInputInfo = new String[4];
		currentOptionId = 0;
		header[0] = "Setup Light";
		options[0] = "Step 2/2";
		options[1] = "Set Limit 2";
		String read=varNames.toString();
		options[2] = read;
		String xxx=varReadings.toString();
		options[3] = xxx;
		userInputInfo[0] = "L: Forward";
		userInputInfo[1] = "R: Backward";
		userInputInfo[2] = "ENT: Accept";
		userInputInfo[3] = "ESC: ToggleLight";
		update();
		enableLeftRightScroll();
	}
	public void manualControl() {
		resetProperties();
		header = new String[1];
		options = new String[3];
		userInputInfo = new String[4];
		currentOptionId = 0;
		header[0] = "Manual Control";
		options[0] = "Move X";
		options[1] = "Move Y";
		options[2] = "Move Z";
		userInputInfo[0] = "L: -";
		userInputInfo[1] = "R: +";
		userInputInfo[2] = "ENT: Scroll";
		userInputInfo[3] = "ESC: Finish";
		update();
		enableLeftRightScroll();
	}
	public void plotMenu() {
		resetProperties();
		header = new String[1];
		options = new String[4];
		userInputInfo = new String[3];
		currentOptionId = 0;
		header[0] = "Plot Menu";
		options[0] = "Dot";
		options[1] = "Line";
		options[2] = "Rectangle";
		options[3] = "TUHH";
		userInputInfo[0] = "L,R: Scroll";
		userInputInfo[1] = "ENT: Plot";
		userInputInfo[2] = "ECS: Cancel";
		update();
		enableLeftRightScroll();
	}
	public void plot() {
		resetProperties();
		header = new String[1];
		options = new String[3];
		userInputInfo = new String[4];
		currentOptionId = 0;
		header[0] = "Plotting Line";
		options[0] = "X=";
		options[1] = "Move Y";
		options[2] = "Move Z";
		userInputInfo[0] = "L: -";
		userInputInfo[1] = "R: +";
		userInputInfo[2] = "ENT: Scroll";
		userInputInfo[3] = "ESC: Finish";
		update();
		enableLeftRightScroll();
	}



}
