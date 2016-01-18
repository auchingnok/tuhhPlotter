package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class Plotbot {
	
	private MotorController penController = new MotorController(Motor.A);
	private MotorController armController = new MotorController(Motor.B);
	private MotorController wheelController = new MotorController(Motor.C);
	private LightSensor lightSensor = new LightSensor(SensorPort.S1,false);
	private TouchSensor touchSensor = new TouchSensor(SensorPort.S4);
	
	public static void main(String[] args)
	{
		// Some example code to check if the build process works
		LCD.drawString("Hello", 0, 0);
		LCD.drawString("I am Plotterbot", 0, 1);
		Button.ESCAPE.waitForPressAndRelease();
		LCD.clear();
		//display menu to choose
		
		//calibration
		//calibrate pen motor
		//calibrate arm motor
		//calibrate wheel
		//calibrate light
		
		//manual control
		
		//plotting task
		
	}

}
