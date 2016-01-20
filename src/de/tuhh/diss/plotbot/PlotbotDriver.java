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
	private int x=0;
	private int y=0;
	private int z=0;
	
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
	
	//move to XYZ in straight line as possible;
	public void goToXYZ(int xTarget,int yTarget,int zTarget) {
		
	}
	
}
