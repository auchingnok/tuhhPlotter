package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class Plotbot {
	private static MotorController penController = new MotorController(Motor.B,"pen",0,false);
	private static MotorController armController = new MotorController(Motor.A, "arm",0.0166221,false);
	private static MotorController wheelController = new MotorController(Motor.C, "wheel",0.0977384,false);
	private static PositioningSystem GPS = new PositioningSystem(penController, armController, wheelController);
	private static LightSensor lightSensor = new LightSensor(SensorPort.S3,false); //flood-light off
	private static TouchSensor touchSensor = new TouchSensor(SensorPort.S1);
	private static TouchSensor penSensor = new TouchSensor(SensorPort.S2);
	
	public static void main(String[] args)
	{
		//initialization
		Button.LEFT.addButtonListener(Listeners.left);
		Button.RIGHT.addButtonListener(Listeners.right);
		Button.ENTER.addButtonListener(Listeners.enter);
		Button.ESCAPE.addButtonListener(Listeners.escape);
		
		// Some example code to check if the build process works
		LCD.drawString("Hello", 0, 0);
		LCD.drawString("I am Plotterbot", 0, 1);
		LCD.drawString("Press Enter: ",0,2);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		//display menu to choose
		LCD.drawString(" -Select a Menu-", 0, 0);
		LCD.drawString("Calibrtion Menu", 0, 3);
		LCD.drawString("Select: Enter",0,6);
		LCD.drawString("Next menu: Left",0,7);
		
		LCD.clear();
		//LCD show be divided into:
		//header
		//content
		
		//calibration
		//calibrate pen motor
		//penController.calibrateZeroAndRange();
		//calibrate arm motor
		armController.calibrateZeroAndRange();
		//calibrate wheel (not required)
		//calibrate light
		
		//manual control
		
		//plotting task

	}
	
	
}
