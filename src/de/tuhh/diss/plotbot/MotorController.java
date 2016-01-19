package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.RegulatedMotorListener;

public class MotorController {
	//motor controller have the following functions:
	//setup max, min and zero position
	//make sure the movement is within range
	//enable multiple speed in manual drive mode
	//
	private String name = "";
	private NXTRegulatedMotor motor;
	private int maxTacho=0;
	private int minTacho=0;
	private int fineStep = 10;
	private int coarseStep = 30;
	private double jacobian =0; //ratio of linear displacement to angular displacement
	private boolean reverse = false;
	private boolean lockMode = false;
	private boolean active = true; //become false if touch sensor pressed
	
	public MotorController(NXTRegulatedMotor refMotor, String refName, double refJacobian, boolean refReverse) {
		motor = refMotor; 
		name = refName;
		jacobian = refJacobian;
		reverse = refReverse;
		motor.stop();
		motor.resetTachoCount();
	}
	
	public void calibrateZeroAndRange() {
		//to calibrate the zero and range of motor
		//the motor will first move to original zero position, in lock mode
		
		//press escape to toggle the motor float mode
		//if in lock mode:
			//press left or right to fine tune the position (1deg/5deg/continuous per 0.5s depend of press time)
			//press enter to accept the current position as zero and proceed
		
		//same procedure for choosing the min and max value
		//if zero is not between min and max, all values are set to zero
		
		motor.rotateTo(0);
		motor.stop();
		
		tunePosition(); //set zero
		int minAngle = tunePosition();
		int maxAngle = tunePosition();
		
		if (minAngle*maxAngle > 0) { 
			minTacho = 0;
			maxTacho = 0;
		} else {
			minTacho = Math.min(minAngle,maxAngle);
			maxTacho = Math.max(minAngle,maxAngle);
		}

	}
	
	private int tunePosition() {
		motor.stop();
		final Runnable leftDown= new Runnable() {public void run() {
			if (reverse) {motor.backward();} else {motor.forward();}
		}};
		final Runnable rightDown= new Runnable() {public void run() {
			if (reverse) {motor.forward();} else {motor.backward();}
		}};
		final Runnable leftUp= new Runnable() {public void run() {
			motor.stop();
		}};
		final Runnable rightUp= new Runnable() {public void run() {
			motor.stop();
		}};
		final Runnable toggleFloat = new Runnable() {public void run() {
			if (lockMode) {
				Listeners.left.reset();
				Listeners.right.reset();
				motor.flt();
			} else {
				motor.stop();
				Listeners.left.setButtonResponse(leftDown,leftUp);
				Listeners.right.setButtonResponse(rightDown,rightUp);
			}
			lockMode = !lockMode;
		}};
		Listeners.left.setButtonResponse(leftDown,leftUp);
		Listeners.right.setButtonResponse(rightDown,rightUp);
		Listeners.escape.reset();
		Listeners.escape.setButtonPressedResponse(toggleFloat);
		do {
			Button.ENTER.waitForPressAndRelease();
		} while (!lockMode);	
		return motor.getTachoCount();
	}
	
	public double getJacob() {
		return jacobian;
	}

	public boolean isInRange(int target) {
		if (target < minTacho) {
			return false;
		} else if (target >maxTacho) {
			return false;
		} else {
			return true;
		}
	}
	
	public void rotateTo(int target) {
		if (isInRange(target)) {motor.rotateTo(target);};
	}
	
	public void deactivate() 
		active = false;
	}
	
	public void activate() {
		active = true;
	}
}
	
