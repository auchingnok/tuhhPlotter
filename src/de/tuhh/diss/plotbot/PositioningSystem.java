package de.tuhh.diss.plotbot;

public class PositioningSystem {
	//global coordinates
	private int x=0;
	private int y=0;
	private int z=0;
	
	private static MotorController pen;
	private static MotorController arm;
	private static MotorController wheel;
	
	//act as coordinate translator
	//converts 3 tacho readings into global coordinates
	//converts global coordinates into tacho targets
	
	public PositioningSystem(MotorController refPen,MotorController refArm,MotorController refWheel) {
		pen = refPen;
		arm = refArm;
		wheel = refWheel;
	}
	/*
	//to be optimized
	public static Point3D getXYZ(int anglePen,int angleArm,int angleWheel) {
		double x= - 1/arm.getJacob() * Math.sin(angleArm);
		double y= wheel.getJacob() * angleWheel + (1 - Math.cos(angleArm))/arm.getJacob();
		double z= pen.getJacob() * anglePen;
		return new Point3D(x,y,z);
	}
	
	public static Point3D getPAW(int x, int y, int z) {
		double p= z/pen.getJacob();
		double a= Math.asin(-x*arm.getJacob());
		double w= (y-1/arm.getJacob())/wheel.getJacob();
		return new Point3D(p,a,w);
	}
	*/
}
