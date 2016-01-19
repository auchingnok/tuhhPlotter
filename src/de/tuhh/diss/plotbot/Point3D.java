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
}
