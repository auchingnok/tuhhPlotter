package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
//import lejos.nxt.TouchSensor;
import lejos.util.Timer;


public class Plotbot {
	public static MainDisplay display = new MainDisplay();
	public static MotorController pen = new MotorController	(Motor.B, 2, 1,false);
	public static MotorController arm= new MotorController	(Motor.A, 5, 84,false);
	public static MotorController wheel= new MotorController(Motor.C, 1, 5,false);
	public static PlotbotDriver driver = new PlotbotDriver(pen,arm,wheel);
	//private static TouchSensor penSensor = new TouchSensor(SensorPort.S2);
	//private static TouchSensor armSensor = new TouchSensor(SensorPort.S1);
	private static LightSensor lightSensor = new LightSensor(SensorPort.S3,false); //flood-light off
	
	static void initialization() {
		//initialization
		Button.LEFT.addButtonListener(Listeners.left);
		Button.RIGHT.addButtonListener(Listeners.right);
		Button.ENTER.addButtonListener(Listeners.enter);
		Button.ESCAPE.addButtonListener(Listeners.escape);
		Listeners.shortTimer = new Timer(800,Listeners.shortListener);
		Listeners.longTimer = new Timer(1600, Listeners.longListener);
		SensorPort.S2.addSensorPortListener(Listeners.penTouch);
		SensorPort.S1.addSensorPortListener(Listeners.armTouch);
		SensorPort.S3.addSensorPortListener(Listeners.penLight);
		pen.getMotor().addListener(Listeners.penEncoder);
		arm.getMotor().addListener(Listeners.armEncoder);
		wheel.getMotor().addListener(Listeners.wheelEncoder);
		//out of range monitor activates when touch 
			//kill all the motors (restart required)
			//output "fatal error: kill all motors"
		//end of initialization
	}
	
	public static void main(String[] args)
	{
		initialization();
		display.welcomeScreen();
		Button.ENTER.waitForPressAndRelease();
		//begin of program
		int buttonID;
		Menu menu = Menu.MAIN;
		while (true) { //infinite loop
			switch (menu) {
			case MAIN: {
				display.mainMenu(); //show information
				do {
					buttonID = Button.waitForAnyPress(); //get input
					switch (buttonID) { //reactions
					case Button.ID_LEFT: display.selectPreviousOption();
					case Button.ID_RIGHT:display.selectNextOption();
					case Button.ID_ENTER: {
						switch (display.getChoice()) {
						case 0: menu = Menu.SETUP;
						case 1: menu = Menu.MANUAL;
						case 2: menu = Menu.PLOT_MENU;	
						case 3: menu = Menu.RESET;
						}
					}}
				} while (buttonID != Button.ID_ENTER);	
			}
			case SETUP:{
				display.setupMenu();
				do {
					buttonID = Button.waitForAnyPress();
					switch (buttonID) {
					case Button.ID_LEFT: display.selectPreviousOption();
					case Button.ID_RIGHT:display.selectNextOption();
					case Button.ID_ENTER: 
						switch (display.getChoice()) {
						case 0: menu = Menu.SET_PEN;
						case 1: menu = Menu.SET_ARM;
						case 2: menu = Menu.SET_LIGHT;
						}
					case Button.ID_ESCAPE: menu = Menu.MAIN;
					}
				} while (buttonID==Button.ID_LEFT || buttonID==Button.ID_RIGHT);
			}
			case SET_PEN:{
				Listeners.resetButtons();
				display.setupPen1_3();
				Listeners.penEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
					display.setReadings(0, Listeners.penEncoder.getReading());
				}});
				pen.enableManualMode(); //link left right button to motor
				Button.ENTER.waitForPressAndRelease();
				pen.calibrateZero();
				display.setupPen2_3();
				Button.ENTER.waitForPressAndRelease();
				pen.calibrateMin();
				display.setupPen3_3();
				Button.ENTER.waitForPressAndRelease();
				pen.calibrateMax();
				pen.checkRange();
				pen.rotateTo(0,false);
				menu = Menu.SETUP;
			}

			case SET_ARM:{
				Listeners.resetButtons();
				display.setupArm1_3();
				Listeners.armEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
					display.setReadings(0, Listeners.armEncoder.getReading());
				}});
				arm.enableManualMode(); //link left right button to motor
				Button.ENTER.waitForPressAndRelease();
				arm.calibrateZero();
				display.setupArm2_3();
				Button.ENTER.waitForPressAndRelease();
				arm.calibrateMin();
				display.setupArm3_3();
				Button.ENTER.waitForPressAndRelease();
				arm.calibrateMax();
				arm.checkRange();
				arm.rotateTo(0,false);
				menu = Menu.SETUP;
			}
			
			case SET_LIGHT:{
				Listeners.resetButtons();
				display.setupLight1_2();
				wheel.enableManualMode();
				Listeners.penLight.setResponse(new Runnable() {public void run(){ //link sensor output to the display
					display.setReadings(0, Listeners.penLight.getNew());
				}});
				Button.ENTER.waitForPressAndRelease();
				lightSensor.calibrateLow();
				display.setupLight2_2();
				Button.ENTER.waitForPressAndRelease();
				lightSensor.calibrateHigh();
				menu = Menu.SETUP;
			}

			case MANUAL:{
				display.manualControl();
				do {
					buttonID = Button.waitForAnyPress();
					switch (buttonID) {
					case Button.ID_ENTER :
						display.selectNextOption();
						switch (display.getChoice()) {
						case 0: pen.enableManualMode();
						case 1: arm.enableManualMode();
						case 2: wheel.enableManualMode();
						}
					case Button.ID_ESCAPE: menu = Menu.MAIN;
					}
				} while (buttonID != Button.ID_ESCAPE);
				menu = Menu.MAIN;
			}
			
			case PLOT_MENU:{
				display.plotMenu();
				do {
					buttonID = Button.waitForAnyPress();
					switch (buttonID) {
					case Button.ID_LEFT: display.selectPreviousOption();
					case Button.ID_RIGHT:display.selectNextOption();
					case Button.ID_ENTER : {
						display.plot();
						//displayTachos();
						switch (display.getChoice()) {
						case 0: driver.moveTo(new Point3D(0, 230, 0), new Point3D(30,30,30));
						case 1: driver.moveTo(new Point3D(5, 110, -20),new Point3D(10,10,10));
						case 2: driver.moveTo(new Point3D(10, 110, 20), new Point3D(10,10,10));
						case 3: driver.moveTo(new Point3D(-5, -50, 50), new Point3D(10,10,10));
						}
					}
					case Button.ID_ESCAPE: {
						menu = Menu.MAIN;
					}
					}
				} while (buttonID != Button.ID_ESCAPE);
			}
			case PLOT:{
				do {
					buttonID = Button.waitForAnyPress();
					switch (buttonID) {
					case Button.ID_LEFT:;
					case Button.ID_RIGHT:;
					case Button.ID_ENTER:;
					case Button.ID_ESCAPE: menu = Menu.MAIN;
					}
				} while (buttonID != Button.ID_ESCAPE);
			}
			case RESET:{
				driver.moveTo(new Point3D(0, 0, 0), new Point3D(30,30,30));
				menu = Menu.MAIN;
			}
			}
		}
	}
	
//	static void displayTachos() {
//		Listeners.penEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
//			display.setReadings(0, Listeners.penEncoder.getReading());
//		}});		
//		Listeners.armEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
//			display.setReadings(1, Listeners.armEncoder.getReading());
//		}});		
//		Listeners.wheelEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
//			display.setReadings(2, Listeners.wheelEncoder.getReading());
//		}});
//	}
	
}
