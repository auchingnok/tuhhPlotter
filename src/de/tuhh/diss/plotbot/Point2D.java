package de.tuhh.diss.plotbot;

public class Point2D {
	public double x;
	public double y;
	public int X;
	public int Y;
	
	public Point2D(double ix,double iy) {
		x=ix;
		y=iy;
		X=(int) x;
		Y=(int) y;
	}
	
	public Point2D translate(double addX,double addY) {
		return new Point2D(x+addX,y+addY);
	}
	
	public static double distance(Point2D pt1, Point2D pt2) {
		double sum = (pt1.x- pt2.x)*(pt1.x- pt2.x) + (pt1.y- pt2.y)*(pt1.y- pt2.y);
		return Math.sqrt(sum);
	}
	
	public static Point2D zero() {
		return new Point2D(0,0);
	}
}
