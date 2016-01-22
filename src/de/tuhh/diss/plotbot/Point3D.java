package de.tuhh.diss.plotbot;

public class Point3D {
	public double x;
	public double y;
	public double z;
	public int X;
	public int Y;
	public int Z;

	public Point3D(double ix,double iy,double iz) {
		x=ix;
		y=iy;
		z=iz;
		X=(int) x;
		Y=(int) y;
		Z=(int) z;
	}
	
	public Point3D(double ix,double iy) {
		x=ix;
		y=iy;
		z=0;
		X=(int) x;
		Y=(int) y;
		Z=(int) z;
	}
	
	public Point3D translate(double addX,double addY,double addZ) {
		return new Point3D(x+addX,y+addY,z+addZ);
	}
	
	public static double distance(Point3D pt1, Point3D pt2) {
		double sum = (pt1.x- pt2.x)*(pt1.x- pt2.x) + (pt1.y- pt2.y)*(pt1.y- pt2.y) + (pt1.z- pt2.z)*(pt1.z- pt2.z);
		return Math.sqrt(sum);
	}
	
	public static Point3D zero() {
		return new Point3D(0,0,0);
	}
}
