package de.tuhh.diss.plotbot;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;

public class PlotFigures {

	public static NXTRegulatedMotor pen = Motor.B;
	public static NXTRegulatedMotor arm= Motor.A;
	public static NXTRegulatedMotor wheel= Motor.C;


	public static int penDown = -300; //has to be defined after trying
	public static int penUp = 0; //has to be defined after trying

	public static int wheelSpeed;
	public static int armSpeed = 300;

	//draws a vertical line from point (x,y) with the length "length"
	//the parameter "length" is positive if it shows in positive y direction
	public static void drawVLine(double x, double y,double length){
		pen.rotateTo(penUp);
		arm.rotateTo((int) Calculations.calcAngleArm(x));
		wheel.rotateTo((int) Calculations.calcAngleWheel(y-Math.abs(Calculations.calcDeltaY(x))));
		pen.rotateTo(penDown);
		wheel.rotate((int) Calculations.calcAngleWheel(length));
		pen.rotateTo(penUp);
	}

	//draws a horizontal line at position Y with the length "length"
	public static void drawHLine(double y, double length){
		int armAngle = (int) Calculations.calcAngleArm(-length/2);
		int deltaY = (int) Math.abs(Calculations.calcDeltaY(length/2));
		int wheelAngleStart = (int) Calculations.calcAngleWheel(y+deltaY);
		int wheelAngleMiddle = (int) Calculations.calcAngleWheel(y);
		//int wheelAngleEnd = (int) Calculations.calcAngleWheel(y+deltaY);
		pen.rotateTo(penUp);
		arm.rotateTo(-armAngle);
		wheel.rotateTo(wheelAngleStart);
		//at start position
		
		pen.rotateTo(penDown);
		arm.setSpeed(PlotFigures.armSpeed);
		wheel.setSpeed(PlotFigures.wheelMotorSpeed());
		wheel.rotateTo(wheelAngleMiddle, true);

		while (arm.getTachoCount()<0){
			wheel.setSpeed(PlotFigures.wheelMotorSpeed());
			arm.forward();
			wheel.backward();
			Delay.msDelay(1000);
		}
		
		//Button.ENTER.waitForPressAndRelease();

		wheel.rotateTo((int) Calculations.calcAngleWheel(y+Math.abs(Calculations.calcDeltaY(length/2))), true);
		while(wheel.isMoving()){
			wheel.setSpeed(wheelMotorSpeed());
		}
	}

	/*
	 * calculates the motor speed of the wheels for compensating deltaY while arm is moving
	 * calculated from the following equation:
	 * deltaY_dot (caused by arm) != wheelSpeed
	 */
	public static int wheelMotorSpeed(){
		wheelSpeed = (int)(Calculations.Kw*(Calculations.La)/(Calculations.Rw *3.1415)*Math.sin(arm.getTachoCount()*3.1415/(180*Calculations.Ka))*armSpeed); 
		return wheelSpeed;
	}
	
	public static void reset() {
		pen.setSpeed(500);
		arm.setSpeed(500);
		pen.rotateTo(penUp);
		arm.rotateTo(0);
		wheel.rotateTo(0);
	}
}

