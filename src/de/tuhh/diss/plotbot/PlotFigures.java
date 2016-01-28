package de.tuhh.diss.plotbot;

public class PlotFigures {
	public static final double boardHeight = 230;

	//places the points of the letters and the rectangle to the correct position
	public static Point2D[] logoTUHH(double size, double space) {
		double charWidth = 0.5 * size;

		Point2D[] T = Letter.T(charWidth,size);
		Point2D[] correctedT = shiftFigure(rotateFigure(T, -90), boardHeight-(1*space+0.5*charWidth));

		Point2D[] U = Letter.U(charWidth,size);
		Point2D[] correctedU = shiftFigure(rotateFigure(U, -90), boardHeight-(2*space+1.5*charWidth));

		Point2D[] H1 = Letter.H(charWidth,size);
		Point2D[] correctedH1 = shiftFigure(rotateFigure(H1, -90), boardHeight-(3*space+2.5*charWidth));

		Point2D[] H2 = Letter.H(charWidth,size);
		Point2D[] correctedH2 = shiftFigure(rotateFigure(H2, -90), boardHeight-(4*space+3.5*charWidth));

		double borderWidth = 2*space + size;
		double borderHeight = 4*charWidth + 5*space;
		Point2D[] border = rect(borderWidth, borderHeight);

		int numOfTotalPoints = T.length + U.length + 2* H1.length + border.length;
		Point2D[] logoTUHH = new Point2D[numOfTotalPoints];
		System.arraycopy(correctedT, 0, logoTUHH, 0, T.length);
		System.arraycopy(correctedU, 0, logoTUHH, T.length, U.length);
		System.arraycopy(correctedH1, 0, logoTUHH, T.length+U.length, H1.length);
		System.arraycopy(correctedH2, 0, logoTUHH, T.length+U.length+H1.length, H2.length);
		System.arraycopy(border, 0, logoTUHH, T.length+U.length+H1.length+H2.length, border.length);

		return logoTUHH;
	}

	public static Point2D[] plotT(double size, double charWidth) {
		return new Point2D[]{Point2D.zero()};
	}
	
	public static Point2D[] plotU(double size, double charWidth) {
		return new Point2D[]{Point2D.zero()};
	}
	
	public static Point2D[] plotH(double size, double charWidth) {
		return new Point2D[]{Point2D.zero()};
	}
	
	public static Point2D[] rect(double width, double height) {
		Point2D cornerUpperLeft = new Point2D (-width/2, 230);
		Point2D cornerUpperRight = cornerUpperLeft.translate(width,0);
		Point2D cornerLowerRight = cornerUpperLeft.translate(width, -height);
		Point2D cornerLowerLeft = cornerUpperLeft.translate(0, -height);
		return new Point2D[]{
				cornerUpperLeft,cornerUpperRight,
				cornerUpperRight,cornerLowerRight,
				cornerLowerRight,cornerLowerLeft,
				cornerLowerLeft,cornerUpperLeft
		};
	}
	
	// rotates all points of a figure anti-clockwise around the origin
	public static Point2D[] rotateFigure(Point2D[] pointsOfFigure, double degree) {
		for (int i = 0; i < pointsOfFigure.length; i++) {
			double x = pointsOfFigure[i].x;
			double y = pointsOfFigure[i].y;
			pointsOfFigure[i].x = Math.cos(degree * 3.1415 / 180) * x - Math.sin(degree * 3.1415 / 180) * y;
			pointsOfFigure[i].y = Math.sin(degree * 3.1415 / 180) * x + Math.cos(degree * 3.1415 / 180) * y;
			pointsOfFigure[i].updateX();
			pointsOfFigure[i].updateY();
		}
		return pointsOfFigure; //rotated 
	}

	//shifts all points of one figure for a certain displacement of y
	public static Point2D[] shiftFigure(Point2D[] pointsOfFigure, double displacementY){
		for (int i=0; i < pointsOfFigure.length; i++){
			//pointsOfFigure[i].x = pointsOfFigure[i].x;
			pointsOfFigure[i].y = pointsOfFigure[i].y + displacementY;
			//pointsOfFigure[i].updateX();
			pointsOfFigure[i].updateY();
		}
		return pointsOfFigure; //shifted
	}

}



