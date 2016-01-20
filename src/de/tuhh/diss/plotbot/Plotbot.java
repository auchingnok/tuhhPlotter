package de.tuhh.diss.plotbot;

import javax.microedition.sensor.SensorListener;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class Plotbot {
	public static MainDisplay display = new MainDisplay();
	private static MotorController pen = new MotorController("pen",Motor.B,0,false);
	private static MotorController arm= new MotorController("arm",Motor.A,0.0166221,false);
	private static MotorController wheel= new MotorController("wheel",Motor.C,0.0977384,false);
	private static PositioningSystem GPS = new PositioningSystem(pen, arm, wheel);
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
		

		//mainDisplay example
		Listeners.resetButtons();
		display.showSampleMenu();
		Button.ENTER.waitForPressAndRelease();
		switch (display.getChoice()) { //do something according to input
		case 0: {
			//display sensor output to the display
			Listeners.penEncoder.setStartResponse(new Runnable() {public void run(){
				display.setReadings(0, "read", Listeners.penEncoder.getReading());
			}});
			//start calibration
			pen.calibrateZeroAndRange();
		}
		case 1: {arm.calibrateZeroAndRange();
			//display sensor output to the display
			Listeners.penEncoder.setStartResponse(new Runnable() {public void run(){
				display.setReadings(0, "read", Listeners.armEncoder.getReading());
			}});
			//start calibration
			pen.calibrateZeroAndRange();
		}
		default: {}
		}
		//end of example

		
		
		
		
		/*LCD.drawString("Hello", 0, 0);
		LCD.drawString("I am Plotterbot", 0, 1);
		LCD.drawString("Press Enter: ",0,2);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		//display menu to choose
		LCD.drawString(" -Select a Menu-", 0, 0);
		LCD.drawString("Calibrtion Menu", 0, 3);
		LCD.drawString("Select: Enter",0,6);
		LCD.drawString("Next menu: Left",0,7);*/
		
		LCD.clear();
	}
	
	
}
