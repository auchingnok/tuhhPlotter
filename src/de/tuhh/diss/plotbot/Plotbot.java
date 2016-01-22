package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.robotics.RegulatedMotorListener;
import lejos.util.Timer;


public class Plotbot {
	public static MainDisplay display = new MainDisplay();
	public static MotorController pen = new MotorController	(true,Motor.B, 1,false);
	public static MotorController arm= new MotorController	(true,Motor.A, 84,false);
	public static MotorController wheel= new MotorController(false,Motor.C, 5,false);
	public static PlotbotDriver driver = new PlotbotDriver(pen,arm,wheel);
	private static LightSensor lightSensor = new LightSensor(SensorPort.S3,false); //flood-light off
	
	private static int penTouchTacho = -100;
	private static int armLeftLimitTacho = -4500;
	private static int armRightLimitTacho = 3800;
	
	private static int menuChoice = 0;
	
	static void initialization() {
		//initialization
		/*Button.LEFT.addButtonListener(Listeners.left);
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
		//end of initialization*/
	}
	
	public static void main(String[] args) {
		initialization();
		
		//welcome screen
		//display.welcomeScreen();
		//Button.ENTER.waitForPressAndRelease();
		
		
		//main menu
		//display.mainMenu();
		//display.selectOption(0);
		//Button.ENTER.waitForPressAndRelease();
		
		
		//setup menu 1
		//display.setupMenu();
		//display.selectOption(0);
		//Button.ENTER.waitForPressAndRelease();
		
		
		//setup pen
		
		/*
		display.setupPen1_3();
		pen.motor.setSpeed(3);
		pen.motor.backward();
		SensorPort.S2.addSensorPortListener(new SensorPortListener(){
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
				if (Math.abs(aNewValue-aOldValue)>=1) {
					pen.motor.stop();
					pen.motor.resetTachoCount();
					pen.motor.setSpeed(10);
					pen.motor.rotateTo(10);
				}
			}
		});
		*/
		
		//pen.motor.forward();
		//pen.enableManualMode(30); //link left right button to motor
		//Button.ENTER.waitForPressAndRelease();
		//pen.calibrateZero();
		//display.setupPen2_3();
		//Button.ENTER.waitForPressAndRelease();
		//pen.calibrateMin();
		//Button.ENTER.waitForPressAndRelease();
		//display.setupPen3_3();
		//Button.ENTER.waitForPressAndRelease();
		//pen.calibrateMax();
		//pen.checkRange();
		//pen.rotateTo(0,false);
		//Listeners.resetButtons();
		
		
		//setup menu 2
		//display.setupMenu();
		//display.selectOption(1);
		//Button.ENTER.waitForPressAndRelease();
		
		
		//setup arm
		display.setupArm1_3();
		arm.motor.setSpeed(100);
		arm.motor.backward();
		SensorPort.S1.addSensorPortListener(new SensorPortListener(){
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
				arm.motor.stop();
				arm.motor.resetTachoCount();
				arm.motor.setSpeed(120);
				arm.motor.rotateTo(-armRightLimitTacho);
				arm.motor.resetTachoCount();
			}
		});
//		Listeners.armEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
//			display.setReadings(0, Listeners.armEncoder.getReading());
//		}});
		//arm.enableManualMode(300); //link left right button to motor
		//Button.ENTER.waitForPressAndRelease();
		//arm.calibrateZero();
		//Button.ENTER.waitForPressAndRelease();
		//display.setupArm2_3();
		//Button.ENTER.waitForPressAndRelease();
		//arm.calibrateMin();
		//display.setupArm3_3();
		//Button.ENTER.waitForPressAndRelease();
		//arm.calibrateMax();
		//arm.checkRange();
		//arm.rotateTo(0,false);
		//Listeners.resetButtons();

		
		//setup menu 3
		//display.setupMenu();
		//display.selectOption(2);
		//Button.ENTER.waitForPressAndRelease();
				
		
		//setup light and test wheel
		
		display.setupLight1_2();
		lightSensor.setFloodlight(true);
		wheel.motor.setSpeed(100);
		wheel.motor.forward();
		SensorPort.S3.addSensorPortListener(new SensorPortListener(){
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
				if (Math.abs(aOldValue-aNewValue) >= 30)
				wheel.motor.stop();
				lightSensor.setFloodlight(false);
				wheel.motor.setSpeed(120);
				wheel.motor.rotateTo(720);
			}
		});
		//wheel.enableManualMode(80);
		//Listeners.penLight.setResponse(new Runnable() {public void run(){ //link sensor output to the display
		//	display.setReadings(0, Listeners.penLight.getNew());
		//}});
		//Button.ENTER.waitForPressAndRelease();
		//lightSensor.calibrateLow();
		//display.setupLight2_2();
		//Button.ENTER.waitForPressAndRelease();
		//lightSensor.calibrateHigh();
		
		//main menu
		//display.mainMenu();
		//display.selectOption(1);
		//Button.ENTER.waitForPressAndRelease();
		
		//manual control
		//display.manualControl();
		//Button.ENTER.waitForPressAndRelease();
			
		//reset in main menu
		//display.mainMenu();
		//display.selectOption(3);
		//driver.moveTo(new Point3D(0, 0, 0), new Point3D(30,30,30));
		//display.selectOption(2);
		//Button.ENTER.waitForPressAndRelease();
				
		
		//plot menu
		display.plotMenu();
		do {
			int buttonID = Button.waitForAnyPress();
			switch (buttonID) {
			case Button.ID_LEFT: {
				menuChoice=menuChoice - 1;
				if(menuChoice < 0)
					menuChoice = 3;
				display.selectOption(menuChoice);
			}
			case Button.ID_RIGHT: {
				menuChoice = menuChoice + 1;
				if(menuChoice > 3)
					menuChoice = 0;
				display.selectOption(menuChoice);
			}
			case Button.ID_ENTER : {
				display.plot();
				//displayTachos();
				switch (display.getChoice()) {
				//case 0: driver.moveTo(new Point3D(0, 230, 0), new Point3D(30,30,30));
				//case 1: driver.moveTo(new Point3D(5, 110, -20),new Point3D(10,10,10));
				//case 2: driver.moveTo(new Point3D(10, 110, 20), new Point3D(10,10,10));
				//case 3: driver.moveTo(new Point3D(-5, -50, 50), new Point3D(10,10,10));
				}
			}
			}
		} while (true);
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
