package de.tuhh.diss.plotbot;

public class Path { //collection of Point3D
	
	private static final double La = 80; //arm span
	private static final double Rw = 28; //wheel radius
	private static final double Rp = 5; //pen motor radius
	
	private static final double Kp = 1; //gear reduction
	private static final double Ka = 84; //gear reduction
	private static final double Kw = 5; //gear reduction
	
	private static final double penFreeTacho = -50;
	private static final double penTouchTacho = -100; //values for pen to touch board
	private static final double armLeftLimitTacho = -4500;
	private static final double armRightLimitTacho = 3700;
	
	//map path from world space to joint space
	public static Point3D[] xyz2paw(Point3D[] xyzPath) {
		int numOfPoints = xyzPath.length;
		Point3D[] pawPath = new Point3D[numOfPoints];
		for (int i =0; i<numOfPoints; i++) {
			Point3D point = xyzPath[i];
			double p = point.z/Rp;
			double a = Math.asin(point.x/La);
			double w = point.y-La+La*Math.cos(a)/Rw;
			pawPath[i] = new Point3D(p,a,w);
		}
		return pawPath;
	}
	
	//generate joint space velocity from path (same start same stop algorithm)
	public static Point3D[] pawSpeed(Point3D[] pawPath, int millisecond) { 
		int pathSize = pawPath.length;
		Point3D[] speedPath = new Point3D[pathSize];
		for (int i =0; i<pathSize-1; i++) {
			Point3D point1 = pawPath[i];
			Point3D point2 = pawPath[i+1];
			int pSpeed = (int) Math.abs(point1.x-point2.x)*1000/millisecond;
			int aSpeed = (int) Math.abs(point1.y-point2.y)*1000/millisecond;
			int wSpeed = (int) Math.abs(point1.z-point2.z)*1000/millisecond;
			speedPath[i] = new Point3D(pSpeed,aSpeed,wSpeed);
		}
		speedPath[pathSize-1] = speedPath[pathSize-2];
		return speedPath;
	}
	
	//generate straight line path
	public static Point3D[] straightLine(Point3D ptStart, Point3D ptEnd, int spacing) { //generate straight path
		int numOfSections = (int) Math.ceil(Point3D.distance(ptStart, ptEnd) /spacing);
		int numOfPoints = numOfSections + 1;
		Point3D[] points = new Point3D[numOfPoints]; 
		double xStep = (ptEnd.x - ptStart.x)/numOfSections;
		double yStep = (ptEnd.y - ptStart.y)/numOfSections;
		double zStep = (ptEnd.z - ptStart.z)/numOfSections;
		for (int i =0; i<numOfPoints-1;i++) {
			double xi = ptStart.x + i * xStep;
			double yi = ptStart.y + i * yStep;
			double zi = ptStart.z + i * zStep;
			points[i] = new Point3D(xi,yi,zi);
		}
		points[numOfPoints-1] = ptEnd;
		return points;
	}
	
	public static Point3D penUp(Point3D oldPosition) {
		return Point3D(oldPosition.x, oldPosition.y, penFreeTacho);
	}
	
	public static Point3D penDown(Point3D oldPosition) {
		return Point3D(oldPosition.x, oldPosition.y, penTouchTacho);
	}
	
	public static Point3D[] Rect(Point3D cornerLowerLeft, int width, int height) {
		Point3D[] cornerUpperLeft = Point3D(cornerLowerLeft.x,cornerLowerLeft.y+height,)
		Point3D[] path = Path.straightLine(ptStart, ptEnd, spacing);
		
		return ;
	}

}
