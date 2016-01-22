package de.tuhh.diss.plotbot;

//coordinate movement of motors
//calculates worldSpace vector
//from current worldSpace vector, calculates current jointSpace vector
//from target worldSpace vector, calculates destination jointSpace vector

public class PlotbotDriver {
	private static MotorController pen;
	private static MotorController arm;
	private static MotorController wheel;
	private static int p=0;
	private static int a=0;
	private static int w=0;
	private static int x=0;
	private static int y=0;
	private static int z=0;
	private int La = 80; //arm span
	private int Rw = 28; //wheel radius
	private int Rp = 5; //pen motor radius
	private static Point3D[] pawPath;
	private static Point3D[] speedPath;
	private static int nextI;
	private static boolean penStopped;
	private static boolean armStopped;
	private static boolean wheelStopped;
	
	public PlotbotDriver(MotorController refPen, MotorController refArm, MotorController refWheel) {
		pen = refPen;
		arm = refArm;
		wheel = refWheel;
	}
	
	private void getPAW() { //get resultant angles of all motors
		p = (int) pen.getTotalAngle();
		a = (int) arm.getTotalAngle();
		w = (int) wheel.getTotalAngle();
	}
	
	public void calXYZ() {
		x = (int) (La* Math.sin(arm.getTotalAngle()) );
		y = (int) (Rw*w - La*Math.cos(a) + La);
		y = (int) (Rp*p);
	}
	
	public static void moveTo(Point3D targetPoint, Point3D speedVector) {
		pen.setSpeed(speedVector.X);
		arm.setSpeed(speedVector.Y);
		wheel.setSpeed(speedVector.Z);
		pen.rotateTo(targetPoint.X,true);
		arm.rotateTo(targetPoint.Y,true);
		wheel.rotateTo(targetPoint.Z,true);
	}
	
//	public void followPath(Point3D[] pawPath, Point3D[] speedPath) {
//		for (int i=0 ; i<pawPath.length;i++) {
//			moveTo(pawPath[i], speedPath[i]);
//		}
//	}
	
	public void allMotorMoving() {
		penStopped = false;
		armStopped = false;
		wheelStopped = false;
	}
	
	public static boolean allMotorHasStopped() {
		if ((penStopped) && (armStopped) && (wheelStopped)) {
			return true;
		} else {return false;}
	}
	
	public void followPath(Point3D[] ipawPath, Point3D[] ispeedPath) {
		pawPath = ipawPath;
		speedPath = ispeedPath;
		nextI =1;
		allMotorMoving();
		Listeners.penEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
			MainDisplay.setReadings(0, Listeners.penEncoder.getReading());
		}});
		Listeners.armEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
			MainDisplay.setReadings(0, Listeners.penEncoder.getReading());
		}});
		Listeners.wheelEncoder.setStopResponse(new Runnable() {public void run(){ //link sensor output to the display
			MainDisplay.setReadings(0, Listeners.penEncoder.getReading());
		}});
		
		moveTo(pawPath[0], speedPath[0]);
	}
	
	public static void continuePath() {
		if (allMotorHasStopped()) {
			if (nextI<pawPath.length) {
			moveTo(pawPath[nextI], speedPath[nextI]);
			nextI=nextI+1;
			}
		}
	}
}
