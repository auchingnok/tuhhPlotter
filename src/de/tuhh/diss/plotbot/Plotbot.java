package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.util.Delay;


public class Plotbot {
	public static MainDisplay display = new MainDisplay();
	public static NXTRegulatedMotor pen = Motor.B;
	public static NXTRegulatedMotor arm= Motor.A;
	public static NXTRegulatedMotor wheel= Motor.C;
	private static LightSensor lightSensor = new LightSensor(SensorPort.S3,false); //flood-light off
	//private static TouchSensor penTouch  = new TouchSensor(SensorPort.S2);
	private static TouchSensor armTouch = new TouchSensor(SensorPort.S1); 

	private static int armRightLimitTacho = 3800;
	private static boolean armIsTouched = false;
	//private static boolean penIsTouched = false;
	
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
					if (penIsTouched || !penTouch.isPressed()) {return;} 
					penIsTouched = true;
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
		arm.setSpeed(100);
		arm.backward();
		SensorPort.S1.addSensorPortListener(new SensorPortListener(){
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
				if (armIsTouched || !armTouch.isPressed()) {return;} 
				armIsTouched = true;
				arm.stop();
				arm.resetTachoCount();
				arm.setSpeed(120);
				arm.rotateTo(-armRightLimitTacho);
				arm.resetTachoCount();
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
		wheel.setSpeed(100);
		wheel.forward();
		SensorPort.S3.addSensorPortListener(new SensorPortListener(){
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
				if (Math.abs(aOldValue-aNewValue) >= 30)
				wheel.stop();
				lightSensor.setFloodlight(false);
				wheel.setSpeed(120);
				wheel.rotateTo(720);
			}
		});
		
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
				//switch (display.getChoice()) {
				//case 0: { //draw rect
				Point3D[] xyzPath = Path.rect(Point3D.zero(), new Point3D(-10,10), 50, 50, 10); //generate total path
				Point3D[] pawPath = Path.xyz2paw(xyzPath); //translate into joint space
				Point3D[] pawSpeedPath = Path.pawSpeed(pawPath, 150); //plan speed trajectory; 150ms between each point
				followPath(pawPath, pawSpeedPath, 300); //300 between each operation
				//}
				//case 2: driver.moveTo(new Point3D(10, 110, 20), new Point3D(10,10,10));
				//case 3: driver.moveTo(new Point3D(-5, -50, 50), new Point3D(10,10,10));
				}
			//}
			}
		} while (true);
	}
	
	static void followPath(Point3D[] pawPath, Point3D[] speedPath, int millisecondInterval) {
		for (int i=0;i<pawPath.length;i++) {
			pen.setSpeed(speedPath[i].X);
			arm.setSpeed(speedPath[i].Y);
			wheel.setSpeed(speedPath[i].Z);
			pen.rotateTo(pawPath[i].X,true);
			arm.rotateTo(pawPath[i].Y,true);
			wheel.rotateTo(pawPath[i].Z,true);
			Delay.msDelay(millisecondInterval);
		}
	}
	
}
