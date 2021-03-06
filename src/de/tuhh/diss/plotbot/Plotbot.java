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
	public static NXTRegulatedMotor pen = Motor.B;
	public static NXTRegulatedMotor arm= Motor.A;
	public static NXTRegulatedMotor wheel= Motor.C;
	private static LightSensor lightSensor = new LightSensor(SensorPort.S3,false); //flood-light off
	private static TouchSensor penTouch  = new TouchSensor(SensorPort.S2);
	private static TouchSensor armTouch = new TouchSensor(SensorPort.S1); 

	private static int armRightLimitTacho = 5000;
	private static boolean armIsTouched = false;
	private static boolean armIsCalibrated = false;
	private static boolean penIsTouched = false;
	private static boolean yIsCalibrated = false;
	
	private static int sizeDefault = 50;
	private static int sizeMin = 10;
	private static int sizeMax = 90;
	private static int sizeStep = 10;
	
	private static int penUp = -100;
	private static int penDown = -350;
	
	private static int movePeriodMs = 120;
	private static int cyclePeriodMs = 150;
	
	public static void main(String[] args) {
		
		MainDisplay.welcomeScreen();
		Button.ENTER.waitForPressAndRelease();
		
		setupPen();
		setupArm();
		setupY();
		
		boolean cont = false;
		MainDisplay.plotMenu();
		int choice = Button.waitForAnyPress();
		Delay.msDelay(800);
		if (choice == Button.ID_LEFT) {
			int size = readSizeOfFigure(0);
			Point2D[] points = PlotFigures.rect(size, size);
			followPoints(points,movePeriodMs,cyclePeriodMs);
		}
		if (choice == Button.ID_RIGHT) {	//plot TUHH
			int letterClearance = 10;
			int size = readSizeOfFigure(letterClearance);
			int newClearance = size/5;
			Point2D[] points = PlotFigures.logoTUHH(size, newClearance);
			followPoints(points,movePeriodMs,cyclePeriodMs);
		}
//		Button.ENTER.waitForPressAndRelease();
		pen.rotateTo(0);
		arm.setSpeed(1000);
		wheel.setSpeed(600);
		arm.rotateTo(0,true);
		wheel.rotateTo(0,true);
		Button.ENTER.waitForPressAndRelease();
	}
	
	static void setupPen() {
		MainDisplay.setupPen(); //notify user
		if (penTouch.isPressed()) {
			penIsTouched = true;
			pen.resetTachoCount();  //if pen already touching, calibration is done
		} else {	
			pen.setSpeed(60); //low speed to minimize damage
			pen.rotate(-100); //pen down
			pen.forward(); //pen up
			SensorPort.S2.addSensorPortListener(new SensorPortListener(){
				public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
					if (penIsTouched || !penTouch.isPressed()) {return;} 
					penIsTouched = true;
					pen.stop();
					pen.resetTachoCount();
				}
			});
		}
		//update tacho readings every 500ms before calibration is done
		while(!penIsTouched) {
			MainDisplay.setReadings(0, pen.getTachoCount());
			Delay.msDelay(500);
		}
	}
	
	static void setupArm() {
		MainDisplay.setupArm();
		int buttonPressed = Button.waitForAnyPress();
		Delay.msDelay(1000);
		if  (buttonPressed == Button.ID_ENTER) {
			//if re-calibration is required
			MainDisplay.setupArmWait();
			arm.setSpeed(1000);
			arm.backward(); //turn right
			SensorPort.S1.addSensorPortListener(new SensorPortListener(){
				public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
					if (armIsTouched || !armTouch.isPressed()) {return;} 
					armIsTouched = true;
					arm.stop();
					arm.resetTachoCount();
					arm.setSpeed(1000);
					arm.rotateTo(armRightLimitTacho);
					arm.resetTachoCount();
					armIsCalibrated = true;
				}
			}); 
		}
		if  (buttonPressed == Button.ID_ESCAPE) { //if the arm is already at zero position, skip calibration
			arm.resetTachoCount();
			armIsCalibrated = true;
		}
		//update tacho readings every 500ms before calibration is done
//		while(!armIsCalibrated) {
//			MainDisplay.setReadings(0, arm.getTachoCount());
//			Delay.msDelay(500);
//		}
	}
	
	static void setupY() {
		MainDisplay.setupLight();
		lightSensor.setFloodlight(true);
		if (Button.waitForAnyPress()==Button.ID_ESCAPE) {return;}
		Delay.msDelay(1000);
		while (Button.ENTER.isUp()){
			MainDisplay.setReadings(0, SensorPort.S3.readValue());
			Delay.msDelay(200);
		}
		Delay.msDelay(200);
		int dark = SensorPort.S3.readValue();
		wheel.setSpeed(50);
		wheel.rotate(300,true);
		while (Button.ENTER.isUp()){
			MainDisplay.setReadings(0, SensorPort.S3.readValue());
			Delay.msDelay(200);
		}
		MainDisplay.setReadings(0, SensorPort.S3.readValue());
		wheel.rotate(-300,true);
		while (!yIsCalibrated) {
			MainDisplay.setReadings(0, SensorPort.S3.readValue());
			if (SensorPort.S3.readValue()-dark<2) {
				wheel.stop();
				
				yIsCalibrated = true;
			}
		}
		lightSensor.setFloodlight(false);
		wheel.rotate(300);
		wheel.resetTachoCount();
	}

	static int readSizeOfFigure(int letterClearance) {
		MainDisplay.sizeOfFigures();
		
		int size = sizeDefault;
		int newSizeMax = sizeMax - 2*letterClearance;
		MainDisplay.setReadings(0, size);
		boolean cont = true;
		do {
			int choice = Button.waitForAnyPress();
			Delay.msDelay(1000);
			if (choice == Button.ID_ENTER) {
				cont = false;
				MainDisplay.setReadingsString(1, "");
			}
			if (choice == Button.ID_LEFT) {
				size = size - sizeStep;
				if (size <= sizeMin) {
					size = sizeMin;
					MainDisplay.setReadingsString(1, "(min size)");
				}
				MainDisplay.setReadings(0, size);
			}
			if (choice == Button.ID_RIGHT) {
				size = size + sizeStep;
				if (size >= newSizeMax) {
					size = newSizeMax;
					MainDisplay.setReadingsString(1, "(max size)");
				}
				MainDisplay.setReadings(0, size);
			}
		} while (cont);
		return size;
	}
	
	static void followPoints(Point2D[] points,int movePeriodMs,int cyclePeriodMs) {
		for (int i=0;i<points.length;i=i+2) {
			Point2D startPoint = points[i];
			Point2D endPoint = points[i+1];
			Point2D[] xyPath = Path.straightLine(startPoint, endPoint, 1);
			Point2D[] awPath = Path.xy2aw(xyPath);
			Point2D[] speedPath = Path.awSpeed(awPath, movePeriodMs);
			if ( (arm.getTachoCount() != awPath[0].X) || (wheel.getTachoCount() != awPath[0].Y)) {
				pen.setSpeed(600);
				pen.rotateTo(penUp);
				arm.setSpeed(1000);
				wheel.setSpeed(500);
				arm.rotateTo(awPath[0].X);
				wheel.rotateTo(awPath[0].Y);	
			}
			arm.rotateTo(awPath[0].X);
			wheel.rotateTo(awPath[0].Y);
			pen.rotateTo(penDown);
			followPath(awPath,speedPath,cyclePeriodMs);
		}
	}
	
	static void followPath(Point2D[] awPath, Point2D[] speedPath, int millisecondInterval) {
		for (int i=0;i< awPath.length;i++) {
			arm.setSpeed(speedPath[i].X);
			wheel.setSpeed(speedPath[i].Y);
			arm.rotateTo( awPath[i].X,true);
			wheel.rotateTo( awPath[i].Y,true);
			Delay.msDelay(millisecondInterval);
		}
	}
	
//	MainDisplay.sizeOfFigures();
//	int size=sizeDefault;
//	MainDisplay.setReadings(0, size);
//	boolean cont = true;
//	do {
//		int choice = Button.waitForAnyPress();
//		Delay.msDelay(1000);
//		if (choice == Button.ID_ENTER) {
//			cont = false;
//		}
//		if (choice == Button.ID_LEFT) {
//			size = size - sizeStep;
//			if (size < sizeMin) {size = sizeMin;}
//			MainDisplay.setReadings(0, size);
//		}
//		if (choice == Button.ID_RIGHT) {
//			size = size + sizeStep;
//			if (size > sizeMax) {size = sizeMax;}
//			MainDisplay.setReadings(0, size);
//		} 
//	} while (cont);
//	return size;
}
