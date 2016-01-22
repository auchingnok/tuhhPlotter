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
	
	public static double distance(Point3D pt1, Point3D pt2) {
		double sum = (pt1.x- pt2.x)*(pt1.x- pt2.x) + (pt1.y- pt2.y)*(pt1.y- pt2.y) + (pt1.z- pt2.z)*(pt1.z- pt2.z);
		return Math.sqrt(sum);
	}
}
