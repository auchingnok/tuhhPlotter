package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
//import lejos.nxt.TouchSensor;

public class Plotbot {
	public static MainDisplay display = new MainDisplay();
	public static MotorController pen = new MotorController(Motor.B,0,false);
	public static MotorController arm= new MotorController(Motor.A,0.0166221,false);
	public static MotorController wheel= new MotorController(Motor.C,0.0977384,false);
	//private static TouchSensor penSensor = new TouchSensor(SensorPort.S2);
	//private static TouchSensor armSensor = new TouchSensor(SensorPort.S1);
	private static LightSensor lightSensor = new LightSensor(SensorPort.S3,false); //flood-light off
	
	public static void main(String[] args)
	{
		initialization();
		display.welcomeScreen();
		Button.ENTER.waitForPressAndRelease();
		mainMenu();
	}
	
	static void initialization() {
		//initialization
		Button.LEFT.addButtonListener(Listeners.left);
		Button.RIGHT.addButtonListener(Listeners.right);
		Button.ENTER.addButtonListener(Listeners.enter);
		Button.ESCAPE.addButtonListener(Listeners.escape);
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
	
	static void mainMenu() {
		display.mainMenu();
		Button.ENTER.waitForPressAndRelease();
		switch (display.getChoice()) {
		case 0: setupMenu();
		case 1: manualControl();
		case 2: plotMenu();
		case 3: resetRobot();
		}
	}
	
	static void setupMenu() {
		display.setupMenu();
		Listeners.escape.setPressedResponse(new Runnable(){public void run() {mainMenu();}});
		Button.ENTER.waitForPressAndRelease();
		//react to input
		switch (display.getChoice()) {
		case 0: setupPen();
		case 1: setupArm();
		case 2: setupLight();
		}
	}
	
	static void setupPen() {
		display.setupPen1_3();
		pen.enableManualMode(); //link left right button to motor
		Listeners.penEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
			display.setReadings(0, Listeners.penEncoder.getReading());
		}});
		pen.calibrateZero();
		display.setupPen2_3();
		pen.calibrateMin();
		display.setupPen3_3();
		pen.calibrateMax();
		pen.checkRange();
		pen.rotateTo(0);
		setupMenu();
	}
	
	static void setupArm() {
		display.setupArm1_3();
		arm.enableManualMode(); //link left right button to motor
		Listeners.armEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
			display.setReadings(0, Listeners.armEncoder.getReading());
		}});
		arm.calibrateZero();
		display.setupArm2_3();
		arm.calibrateMin();
		display.setupArm3_3();
		arm.calibrateMax();
		arm.checkRange();
		arm.rotateTo(0);
		setupMenu();
	}
	
	static void setupLight() {
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
		setupMenu();
	}
	
	static void manualControl() {
		display.manualControl();
		Button.ESCAPE.waitForPressAndRelease();
		mainMenu();
	}
	
	static void plotMenu() {
		
	}
	
	static void plot() {
		display.plot();
		Listeners.penEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
			display.setReadings(0, Listeners.penEncoder.getReading());
		}});		
		Listeners.armEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
			display.setReadings(1, Listeners.armEncoder.getReading());
		}});		
		Listeners.wheelEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
			display.setReadings(2, Listeners.wheelEncoder.getReading());
		}});
		Listeners.escape.setPressedResponse(new Runnable(){public void run() {mainMenu();}});
	}
	
	static void resetRobot() {
		
	}
}
