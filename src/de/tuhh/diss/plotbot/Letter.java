package de.tuhh.diss.plotbot;

public class Letter {
	
	public static Point2D[] T(double width, double height) {
		double w=width/2;
		double h=height/2;
		Point2D line1Start = new Point2D(-w,h);
		Point2D line1End = new Point2D(w,h);
		Point2D line2Start = new Point2D(0,h);
		Point2D line2End = new Point2D(0,-h);
		return new Point2D[] {line1Start,line1End,line2Start,line2End};
	}
	
	public static Point2D[] U(double width, double height) {
		double w=width/2;
		double h=height/2;
		Point2D line1Start = new Point2D(-w,h);
		Point2D line1End = new Point2D(-w,-h);
		Point2D line2Start = new Point2D(-w,-h);
		Point2D line2End = new Point2D(w,-h);
		Point2D line3Start = new Point2D(w,-h);
		Point2D line3End = new Point2D(w,h);
		return new Point2D[] {line1Start,line1End,line2Start,line2End,line3Start,line3End};
	}
	
	public static Point2D[] H(double width, double height) {
		double w=width/2;
		double h=height/2;
		Point2D line1Start = new Point2D(-w,h);
		Point2D line1End = new Point2D(-w,-h);
		Point2D line2Start = new Point2D(-w,0);
		Point2D line2End = new Point2D(w,0);
		Point2D line3Start = new Point2D(w,h);
		Point2D line3End = new Point2D(w,-h);
		return new Point2D[] {line1Start,line1End,line2Start,line2End,line3Start,line3End};
	}
	
	
}
