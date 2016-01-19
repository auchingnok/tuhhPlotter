package de.tuhh.diss.plotbot;

import javax.microedition.sensor.SensorListener;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;

public class Plotbot {
	private static MotorController penController = new MotorController(Motor.B,"pen",0,false);
	private static MotorController armController = new MotorController(Motor.A, "arm",0.0166221,false);
	private static MotorController wheelController = new MotorController(Motor.C, "wheel",0.0977384,false);
	private static PositioningSystem GPS = new PositioningSystem(penController, armController, wheelController);
	private static TouchSensor penSensor = new TouchSensor(SensorPort.S2);
	private static TouchSensor armSensor = new TouchSensor(SensorPort.S1);
	private static LightSensor lightSensor = new LightSensor(SensorPort.S3,false); //flood-light off
	
	public static void main(String[] args)
	{
		//initialization
		Button.LEFT.addButtonListener(Listeners.left);
		Button.RIGHT.addButtonListener(Listeners.right);
		Button.ENTER.addButtonListener(Listeners.enter);
		Button.ESCAPE.addButtonListener(Listeners.escape);
		SensorPort.S2.addSensorPortListener(Listeners.pen);
		SensorPort.S1.addSensorPortListener(Listeners.arm);
		SensorPort.S3.addSensorPortListener(Listeners.light);
		//calibration
		
		
		Button.ENTER.waitForPressAndRelease();
		
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
	}
	
	
}
