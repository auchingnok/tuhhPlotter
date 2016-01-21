package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.NXTRegulatedMotor;

public class MotorController {
	//motor controller have the following functions:
		//define forward direction
		//store max, min and zero position
		//make sure the movement is within range
		//enable 3 speed level in manual drive mode
		
	private NXTRegulatedMotor motor; //motor being controlled
	private double reduction =0; //gear reduction ratio
	private int maxTacho=0; //limit value 1
	private int minTacho=0; //limit value 2
	
	private int speedFactor = 1;
	private int manualStep = 10; //step deg for manual drive mode
	private int[] manualSpeedLevel = {60,120};
	
	private boolean reverse = false;
	
	public MotorController(NXTRegulatedMotor refMotor, int refSpeedFactor, double refReduction, boolean refReverse) {
		motor = refMotor; 
		speedFactor = refSpeedFactor;
		manualStep = manualStep * speedFactor;
		manualSpeedLevel[0] = manualSpeedLevel[0] * speedFactor;
		manualSpeedLevel[1] = manualSpeedLevel[1] * speedFactor;
		reduction = refReduction;
		reverse = refReverse;
		motor.stop();
		motor.resetTachoCount();
	}
	
	public double getTotalAngle() {
		return motor.getTachoCount()/reduction;
	}
	
	
	
	public void calibrateZero() {
		Button.ENTER.waitForPressAndRelease();
		motor.resetTachoCount();
	}
	
	public void calibrateMin() {
		Button.ENTER.waitForPressAndRelease();
		minTacho = motor.getTachoCount();
		rotateTo(0);
	}
	
	public void calibrateMax() {
		Button.ENTER.waitForPressAndRelease();
		maxTacho = motor.getTachoCount();
		rotateTo(0);
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
	
	public void enableManualMode() {
		final Runnable leftDown= new Runnable() {public void run() {
			if (reverse) {motor.rotate(-manualStep);} else {motor.rotate(manualStep);}
			//if (reverse) {motor.backward();} else {motor.forward();}
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
			//if (reverse) {motor.forward();} else {motor.backward();}
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
		Listeners.left.setAllResponse(leftDown,leftShort,leftLong,leftUp);
		Listeners.right.setAllResponse(rightDown,rightShort,rightLong,rightUp);
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
	
	public void setSpeed(int r) {
		motor.setSpeed(r);
	}
	
	public void rotateTo(int target) {
		//calculate actual rotation needed
		int actualTarget = (int) (target * reduction);
		if (isInRange(actualTarget)) {motor.rotateTo(actualTarget);};
	}
	
	public NXTRegulatedMotor getMotor() {
		return motor;
	}
}
	
