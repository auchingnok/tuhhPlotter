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
	private static PositioningSystem GPS = new PositioningSystem();
	private static LightSensor lightSensor = new LightSensor(SensorPort.S1,false); //flood-light off
	private static TouchSensor touchSensor = new TouchSensor(SensorPort.S4);
	public static Listeners listeners = new Listeners(); //global listeners
	
	public static void main(String[] args)
	{
		// Some example code to check if the build process works
		LCD.drawString("Hello", 0, 0);
		LCD.drawString("I am Plotterbot", 0, 1);
		Button.ESCAPE.waitForPressAndRelease();
		LCD.clear();
		//display menu to choose
		
		//calibration
		//test 
		
		//calibrate pen motor
		penController.calibrateZeroAndRange();
		//calibrate arm motor
		armController.calibrateZeroAndRange();
		//calibrate wheel (not required)
		//calibrate light
		gridScan(10, 10, 10);
		
		//manual control
		
		//plotting task
	}
	
	
}
