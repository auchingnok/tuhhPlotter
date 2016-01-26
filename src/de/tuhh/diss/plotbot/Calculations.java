package de.tuhh.diss.plotbot;

//import de.tuhh.diss.plotbot.Point3D;

public class Calculations {
	public static final double La = 80; //arm span
	public static final double Rw = 28; //wheel radius

	public static final double Ka = 84; //gear ratio arm motor
	public static final double Kw = 5; //gear ratio wheel motor

	public static double angleArm;
	public static double angleWheel;
	public static double deltaY;
	public static double distance;

	//calculates the needed arm motor angle for a certain x-position
	public static double calcAngleArm(double x){ 
		angleArm = Math.asin(x/La)*Ka*180/3.1415; //changed
		return angleArm;
	}

	//calculates the difference in y directions 
	public static double calcDeltaY(double x){
		deltaY = La*(1-Math.cos(Math.asin(x/La)));
		return deltaY;
	}

	// calculates angle of wheel motor for a wished distance to travel
	public static double calcAngleWheel(double distance){
		angleWheel = ((distance*180)/(Rw*3.1415)*Kw);
		return angleWheel;
	}
}