package de.tuhh.diss.plotbot;

//import lejos.util.Timer;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;

//moves a motor manually by pressing left or right button
//for short presses, motor will move 3 deg
//for long press, i.e. duration > 0.8s, motor speed = 10 deg/s
//for extra long press, i.e. duration > 2s, motor speed = 15 deg/s
//button release will stop the motor

public class PressToDriveController {
	private NXTRegulatedMotor motor;
	//private DelayTimerListener longPressListener;
	//private DelayTimerListener extraLongPressListener;
	//private Timer longPressTimer;
	//private Timer extraLongPressTimer;
	//private boolean forward;
	
	public PressToDriveListener(NXTRegulatedMotor refMotor, boolean refForward) {
		motor = refMotor;
		//Listeners.pressLeftToDrive = new PressToDriveListener(refMotor,true);
		//Listeners.pressRightToDrive = new PressToDriveListener(refMotor, false);
		
		/*Runnable changeSpeed = new Runnable() {
			public void run() {
				motor.setSpeed(30);
				if (forward) {motor.forward();} else {motor.backward();}
			}
		};*/
		//longPressListener = new DelayTimerListener(changeSpeed);
		//longPressTimer = new Timer(800, longPressListener);
		
		//initialize
		motor.stop();
		
	}
	
	public void buttonPressed(Button button) {
		motor.forward();
		//motor.rotate(10);
		//longPressTimer.start();
	}
	
	public void buttonReleased(Button button) {
		//longPressTimer.stop();
		motor.stop();
	}
}
