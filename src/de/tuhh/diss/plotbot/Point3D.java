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
	
	public static Point3D[] interpolateStraightLine(Point3D ptStart, Point3D ptEnd, int spacing) {
		int numOfSections = (int) Math.ceil(Point3D.distance(ptStart, ptEnd) /spacing);
		int numOfPoints = numOfSections + 1;
		Point3D[] points = new Point3D[numOfPoints]; 
		double xStep = (ptStart.x - ptEnd.x)/numOfSections;
		double yStep = (ptStart.y - ptEnd.y)/numOfSections;
		double zStep = (ptStart.z - ptEnd.z)/numOfSections;
		for (int i =0; i<numOfPoints-1;i++) {
			double xi = ptStart.x + i * xStep;
			double yi = ptStart.y + i * yStep;
			double zi = ptStart.z + i * zStep;
			points[i] = new Point3D(xi,yi,zi);
		}
		points[numOfPoints-1] = ptEnd;
		return points;
	}
}
