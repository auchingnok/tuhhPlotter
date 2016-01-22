package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.NXTRegulatedMotor;

public class MotorController {
	//motor controller have the following functions:
	public NXTRegulatedMotor motor; //motor being controlled
	private int reduction =0; //gear reduction ratio
	private int maxTacho=0; //limit value 1
	private int minTacho=0; //limit value 2
	
	private boolean hasRange = false;
	private boolean reverse = false;
	
	public MotorController(
			boolean ranged,
			NXTRegulatedMotor refMotor,
			int refReduction,
			boolean refReverse) {
		hasRange = ranged;
		motor = refMotor; 
		reduction = refReduction;
		reverse = refReverse;
		motor.stop();
		motor.resetTachoCount();
	}
	
	public double getTotalAngle() {
		return motor.getTachoCount()/reduction;
	}
	
	public void calibrateZero() {
		motor.resetTachoCount();
	}
	
	public void calibrateMin() {
		minTacho = motor.getTachoCount();
		rotateTo(0,false);
	}
	
	public void calibrateMax() {
		Button.ENTER.waitForPressAndRelease();
		maxTacho = motor.getTachoCount();
		rotateTo(0,false);
	}
	
	public void checkRange() { //make sure minTacho <= 0 <= maxTacho
		if (minTacho*maxTacho > 0) { 
			minTacho = 0;
			maxTacho = 0;
		} else {
			if (minTacho > maxTacho) { //swap the values
				int temp = minTacho;
				minTacho = maxTacho;
				maxTacho = temp;
			}
		}
	}
	
	public void enableManualMode(int speed) {
		motor.setSpeed(speed);
		final Runnable leftDown= new Runnable() {public void run() {
			if (reverse) {motor.backward();} else {motor.forward();}
		}};
		final Runnable leftUp= new Runnable() {public void run() {
			motor.stop();
		}};
		final Runnable rightDown= new Runnable() {public void run() {
			if (reverse) {motor.forward();} else {motor.backward();}
		}};
		final Runnable rightUp= new Runnable() {public void run() {
			motor.stop();
		}};
		Listeners.left.setResponse(leftDown, leftUp);
		Listeners.right.setResponse(rightDown,rightUp);
	}

	public boolean isInRange(int target) {
		if (hasRange) {
			return true;
		}
		else if (target < minTacho) {
			return false;
		} else if (target >maxTacho) {
			return false;
		} else {
			return true;
		}
	}
	
	public void setSpeed(int r) {
		motor.setSpeed(r*reduction);
	}
	
	public void rotateTo(int target, boolean quickReturn) {
		//calculate actual rotation needed
		int actualTarget = (int) (target * reduction);
		if (isInRange(actualTarget)) {motor.rotateTo(actualTarget, quickReturn);};
	}
	
	public NXTRegulatedMotor getMotor() {
		return motor;
	}
}
	
