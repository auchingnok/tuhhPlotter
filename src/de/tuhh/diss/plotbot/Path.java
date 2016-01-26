package de.tuhh.diss.plotbot;

public class Path { //collection of Point3D
	
	private static final double La = 80; //arm span
	private static final double Rw = 28; //wheel radius
	private static final double Rp = 2; //pen motor radius
	
	private static final double Kp = 3; //gear reduction
	private static final double Ka = 84; //gear reduction
	private static final double Kw = 5; //gear reduction
	
	//map path from world space to joint space
	public static Point2D[] xy2aw(Point2D[] xyPath) {
		int numOfPoints = xyPath.length;
		Point2D[] awPath = new Point2D[numOfPoints];
		for (int i =0; i<numOfPoints; i++) {
			Point2D point = xyPath[i];
			double a = -Math.asin(point.x/La);
			double w = (point.y+La-La*Math.cos(a)) /Rw;
			double aDegBeforeReduction = 180*a*Ka/3.1415;
			double wDegBeforeReduction = 180*w*Kw/3.1415;
			awPath[i] = new Point2D(aDegBeforeReduction,wDegBeforeReduction);
		}
		return awPath;
	}
	
	//generate joint space velocity from path (same start same stop algorithm)
	public static Point2D[] awSpeed(Point2D[] awPath, int millisecond) { 
		int pathSize = awPath.length;
		Point2D[] speedPath = new Point2D[pathSize];
		for (int i =0; i<pathSize-1; i++) {
			Point2D point1 = awPath[i];
			Point2D point2 = awPath[i+1];
			int aSpeed = (int) Math.abs(point1.x-point2.x)*1000/millisecond;
			int wSpeed = (int) Math.abs(point1.y-point2.y)*1000/millisecond;
			speedPath[i] = new Point2D(aSpeed,wSpeed);
		}
		speedPath[pathSize-1] = speedPath[pathSize-2];
		return speedPath;
	}
	
	//generate straight line path}
	public static Point2D[] straightLine(Point2D ptStart, Point2D ptEnd, int spacing) { //generate straight path
		int numOfSections = (int) Math.ceil(Point2D.distance(ptStart, ptEnd) /spacing);
		int numOfPoints = numOfSections + 1;
		Point2D[] points = new Point2D[numOfPoints]; 
		double xStep = (ptEnd.x - ptStart.x)/numOfSections;
		double yStep = (ptEnd.y - ptStart.y)/numOfSections;
		for (int i =0; i<numOfPoints-1;i++) {
			double xi = ptStart.x + i * xStep;
			double yi = ptStart.y + i * yStep;
			points[i] = new Point2D(xi,yi);
		}
		points[numOfPoints-1] = ptEnd;
		return points;
	}

	public static Point2D[] mergePath(Point2D[][] paths) { //merge several paths into one
		int totalPathSize = 0;
		for (int i=0; i<paths.length; i++) {
			totalPathSize = totalPathSize + paths[i].length;
		}
		Point2D[] finalPath = new Point2D[totalPathSize];
		int k =0; //index
		for (int i=0; i<paths.length; i++) {
			for (int j=0;j<paths[i].length; j++) {
				finalPath[k] = paths[i][j];
				k=k+1;
			}
		}
		return finalPath;
	}
	
	public static Point2D[] rect(Point2D startPoint, Point2D cornerLowerLeft, double width, double height,int spacing) {
		Point2D cornerUpperLeft = cornerLowerLeft.translate(0, height);
		Point2D cornerUpperRight = cornerLowerLeft.translate(width, height);
		Point2D cornerLowerRight = cornerLowerLeft.translate(width, 0);
		
		Point2D[] leftPath 		= Path.straightLine(cornerLowerLeft, cornerUpperLeft, spacing);
		Point2D[] upPath 		= Path.straightLine(cornerUpperLeft, cornerUpperRight, spacing);
		Point2D[] rightPath 	= Path.straightLine(cornerUpperRight, cornerLowerRight, spacing);
		Point2D[] lowerPath 	= Path.straightLine(cornerLowerRight, cornerLowerLeft, spacing);
		
		return mergePath(new Point2D[][]{leftPath,upPath,rightPath,lowerPath});
	}


}
