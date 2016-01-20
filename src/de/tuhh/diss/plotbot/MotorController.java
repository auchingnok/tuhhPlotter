package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.RegulatedMotorListener;

public class MotorController {
	//motor controller have the following functions:
		//define forward direction
		//store max, min and zero position
		//make sure the movement is within range
		//enable 3 speed level in manual drive mode
		
	private String name = "";
	private NXTRegulatedMotor motor; //motor being controlled
	private double jacobian =0; //ratio of linear displacement to angular displacement
	private int maxTacho=0; //limit value 1
	private int minTacho=0; //limit value 2
	
	private int manualStep = 10; //step deg for manual drive mode
	private int[] manualSpeedLevel = {30,80};
	
	private boolean reverse = false;
	private boolean lockMode = false; //for calibration use
	private boolean active = true; //become false if touch sensor pressed
	
	public MotorController(String refName,NXTRegulatedMotor refMotor, double refJacobian, boolean refReverse) {
		name = refName;
		motor = refMotor; 
		jacobian = refJacobian;
		reverse = refReverse;
		motor.stop();
		motor.resetTachoCount();
	}
	//to calibrate the zero and range of motor
		//the motor will first move to original zero position, in lock mode
		
		//press escape to toggle the motor float mode
		//if in lock mode:
			//press left or right to fine tune the position (1deg/5deg/continuous per 0.5s depend of press time)
			//press enter to accept the current position as zero and proceed
		
		//same procedure for choosing the min and max value
		//if zero is not between min and max, all values are set to zero
	public void calibrateZeroAndRange() {
		
		motor.rotateTo(0);
		motor.stop();
		Listeners.resetButtons();
		
		Plotbot.display.showSetupMotor(name,1); //display name of part and indicate step one
		tunePosition(); //set zero
		Plotbot.display.showSetupMotor(name,2); //display name of part and indicate step one
		int minAngle = tunePosition();
		Plotbot.display.showSetupMotor(name,3); //display name of part and indicate step one
		int maxAngle = tunePosition();
		
		Listeners.resetButtons();
		
		if (minAngle*maxAngle > 0) { 
			minTacho = 0;
			maxTacho = 0;
		} else {
			minTacho = Math.min(minAngle,maxAngle);
			maxTacho = Math.max(minAngle,maxAngle);
		}

	}
	
	private int tunePosition() { //implement variable manual speed control
		motor.stop();
		final Runnable leftDown= new Runnable() {public void run() {
			if (reverse) {motor.rotate(-manualStep);} else {motor.rotate(manualStep);}
		}};
		final Runnable leftShort= new Runnable() {public void run() {
			motor.setSpeed(manualSpeedLevel[0]);
			if (reverse) {motor.backward();} else {motor.forward();}
		}};
		final Runnable leftLong= new Runnable() {public void run() {
			motor.setSpeed(manualSpeedLevel[1]);
		}};
		final Runnable leftUp= new Runnable() {public void run() {
			motor.stop();
		}};
		final Runnable rightDown= new Runnable() {public void run() {
			if (reverse) {motor.rotate(manualStep);} else {motor.rotate(-manualStep);}
		}};
		final Runnable rightShort= new Runnable() {public void run() {
			motor.setSpeed(manualSpeedLevel[0]);
			if (reverse) {motor.forward();} else {motor.backward();}
		}};
		final Runnable rightLong= new Runnable() {public void run() {
			motor.setSpeed(manualSpeedLevel[1]);
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
				Listeners.left.setResponse(leftDown,leftUp);
				Listeners.right.setResponse(rightDown,rightUp);
			}
			lockMode = !lockMode;
		}};
		Listeners.left.setAllResponse(leftDown,leftShort,leftLong,leftUp);
		Listeners.right.setAllResponse(rightDown,rightShort,rightLong,rightUp);
		Listeners.escape.reset();
		Listeners.escape.setPressedResponse(toggleFloat);
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
	
	public void deactivate() {
		active = false;
	}
	
	public void activate() {
		active = true;
	}
	
	public NXTRegulatedMotor getMotor() {
		return motor;
	}
}
	
