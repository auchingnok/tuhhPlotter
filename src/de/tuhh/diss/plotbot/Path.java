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
			double pBeforeReduction = p*Kp;
			double aBeforeReduction = a*Ka;
			double wBeforeReduction = w*Kw;
			pawPath[i] = new Point3D(pBeforeReduction,aBeforeReduction,wBeforeReduction);
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
		return new Point3D(oldPosition.x, oldPosition.y, penFreeTacho);
	}
	
	public static Point3D penDown(Point3D oldPosition) {
		return new Point3D(oldPosition.x, oldPosition.y, penTouchTacho);
	}
	
	public static Point3D[] rect(Point3D startPoint, Point3D cornerLowerLeft, double width, double height,int spacing) {
		Point3D cornerUpperLeft = cornerLowerLeft.translate(0, height, 0);
		Point3D cornerUpperRight = cornerLowerLeft.translate(width, height, 0);
		Point3D cornerLowerRight = cornerLowerLeft.translate(width, 0, 0);
		
		Point3D[] preparePath1 	= Path.straightLine(startPoint, penUp(startPoint), spacing);
		Point3D[] preparePath2 	= Path.straightLine(penUp(startPoint), cornerLowerLeft, spacing);
		Point3D[] leftPath 		= Path.straightLine(penDown(cornerLowerLeft), penDown(cornerUpperLeft), spacing);
		Point3D[] upPath 		= Path.straightLine(penDown(cornerUpperLeft), penDown(cornerUpperRight), spacing);
		Point3D[] rightPath 	= Path.straightLine(penDown(cornerUpperRight), penDown(cornerLowerRight), spacing);
		Point3D[] lowerPath 	= Path.straightLine(penDown(cornerLowerRight), penDown(cornerLowerLeft), spacing);
		
		int totalSize = preparePath1.length+preparePath2.length+leftPath.length+upPath.length+rightPath.length+lowerPath.length;
		Point3D[] totalPath = new Point3D[totalSize];
		System.arraycopy(preparePath1, 	0, totalPath, 0, 		preparePath1.length); //source,source start,target,target start, copy length
		System.arraycopy(preparePath2, 	0, totalPath, preparePath1.length, 		preparePath2.length);
		System.arraycopy(leftPath, 		0, totalPath, preparePath1.length+preparePath2.length, 		leftPath.length);
		System.arraycopy(upPath, 		0, totalPath, preparePath1.length+preparePath2.length+leftPath.length, 		upPath.length);
		System.arraycopy(rightPath, 	0, totalPath, preparePath1.length+preparePath2.length+leftPath.length+upPath.length, 		rightPath.length);
		System.arraycopy(lowerPath, 	0, totalPath, preparePath1.length+preparePath2.length+leftPath.length+upPath.length+rightPath.length, 		lowerPath.length);
		
		return totalPath;
	}


}
