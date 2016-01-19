package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class Plotbot {
	
	private static MotorController penController = new MotorController(Motor.A,"pen",0);
	private static MotorController armController = new MotorController(Motor.B, "arm",0.0166221);
	private static MotorController wheelController = new MotorController(Motor.C, "wheel",0.0977384);
	private static PositioningSystem GPS = new PositioningSystem(penController, armController, wheelController);
	private static LightSensor lightSensor = new LightSensor(SensorPort.S3,false); //flood-light off
	private static TouchSensor touchSensor = new TouchSensor(SensorPort.S1);
	private static TouchSensor penSensor = new TouchSensor(SensorPort.S2);
	public static Listeners listeners = new Listeners(); //global listeners
	
	public static void main(String[] args)
	{
		// Some example code to check if the build process works
		LCD.drawString("Hello", 0, 0);
		LCD.drawString("I am Plotterbot", 0, 1);
		Button.ESCAPE.waitForPressAndRelease();
		LCD.clear();
		//display menu to choose
		
		//LCD show be divided into:
		//header
		//content
		
		//calibration
		//calibrate pen motor
		penController.calibrateZeroAndRange();
		//calibrate arm motor
		armController.calibrateZeroAndRange();
		//calibrate wheel (not required)
		//calibrate light
		
		//manual control
		
		//plotting task
	}
	
	
}
