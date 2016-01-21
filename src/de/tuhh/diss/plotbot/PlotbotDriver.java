package de.tuhh.diss.plotbot;

//coordinate movement of motors
//calculates worldSpace vector
//from current worldSpace vector, calculates current jointSpace vector
//from target worldSpace vector, calculates destination jointSpace vector

public class PlotbotDriver {
	private MotorController pen;
	private MotorController arm;
	private MotorController wheel;
	private int p=0;
	private int a=0;
	private int w=0;
	private int x=0; //current
	private int y=0; //current
	private int z=0; //current
	private int La = 80; //arm span
	private int Rw = 28; //wheel radius
	private int Rp = 5; //pen motor radius
	
	public PlotbotDriver(MotorController refPen, MotorController refArm, MotorController refWheel) {
		pen = refPen;
		arm = refArm;
		wheel = refWheel;
	}
	
	public void reset() {
		int p=0;
		int a=0;
		int w=0;
		int x=0;
		int y=0;
		int z=0;
	}
	
	public void getPAW() { //get resultant angles of all motors
		p = (int) pen.getTotalAngle();
		a = (int) arm.getTotalAngle();
		w = (int) wheel.getTotalAngle();
	}
	
	public void calX() {
		x = (int) (La* Math.sin(arm.getTotalAngle()) );
	}
	
	public void calY() {
		y = (int) (Rw*w - La*Math.cos(a) + La);
	}
	
	public void calZ() {
		y = (int) (Rp*p);
	}
	
	private void goToPAW(int pTarget, int aTarget, int wTarget) {
		pen.setSpeed(10);
		arm.setSpeed(80);
		wheel.setSpeed(30);
		pen.rotateTo(pTarget);
		arm.rotateTo(aTarget);
		wheel.rotateTo(wTarget);
	}
	
	//move to XYZ in straight line as possible;
	public void goToXYZ(int xTarget,int yTarget,int zTarget) {
		int pTarget = zTarget/Rp;
		int aTarget = (int) Math.asin(x/La);
		int wTarget = (int) ((yTarget-La+La*Math.cos(aTarget))/Rw);
		goToPAW(pTarget,aTarget,wTarget);
	}
	
	public void plotLine() {
		
	}
}
