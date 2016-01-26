package de.tuhh.diss.plotbot;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.util.Delay;


public class Plotbot {
	public static MainDisplay display = new MainDisplay();
	public static NXTRegulatedMotor pen = Motor.B;
	public static NXTRegulatedMotor arm= Motor.A;
	public static NXTRegulatedMotor wheel= Motor.C;
	private static LightSensor lightSensor = new LightSensor(SensorPort.S3,false); //flood-light off
	private static TouchSensor penTouch  = new TouchSensor(SensorPort.S2);
	private static TouchSensor armTouch = new TouchSensor(SensorPort.S1); 

	private static int armRightLimitTacho = 4900;
	private static boolean armIsTouched = false;
	private static boolean penIsTouched = false;
	
	public static void main(String[] args) {
		pen.setSpeed(60);
		pen.rotate(-100);
		pen.forward();
		SensorPort.S2.addSensorPortListener(new SensorPortListener(){
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
				if (Math.abs(aNewValue-aOldValue)>=1) {
					if (penIsTouched || !penTouch.isPressed()) {return;} 
					penIsTouched = true;
					pen.stop();
					pen.resetTachoCount();
				}
			}
		});
		pen.setSpeed(100);
		
		arm.resetTachoCount();
		Button.ENTER.waitForPressAndRelease();
		arm.setSpeed(1000);
		arm.backward();
		SensorPort.S1.addSensorPortListener(new SensorPortListener(){
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
				if (armIsTouched || !armTouch.isPressed()) {return;} 
				armIsTouched = true;
				arm.stop();
				arm.resetTachoCount();
				arm.setSpeed(1000);
				arm.rotateTo(armRightLimitTacho);
				arm.resetTachoCount();
			}
		}); 

		/*Button.ENTER.waitForPressAndRelease();
		lightSensor.setFloodlight(true);
		lightSensor.calibrateHigh(); //calibrate black
		wheel.setSpeed(100);
		wheel.rotateTo(300);
		wheel.stop();
		Delay.msDelay(500);
		lightSensor.calibrateLow(); //calibrate white
		wheel.backward();
		SensorPort.S3.addSensorPortListener(new SensorPortListener(){
			public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
				if (lightSensor.readNormalizedValue() < 10)
				wheel.stop();
				wheel.setSpeed(120);
				wheel.rotateTo(500);
			}
		});*/
		
		//ANDY
		
		Button.ENTER.waitForPressAndRelease();
		//Point3D[] straightPath = Path.straightLine(new Point3D(0,0), new  Point3D(50,0), 1);
		Point3D[] rectPath = Path.rect(Point3D.zero(), Point3D.zero(), 70, 80, 1);
		Point3D[] pawPath = Path.xyz2paw(rectPath);
		Point3D[] speedPath = Path.pawSpeed(pawPath, 100);
		pen.rotateTo(-400);
		followPath(pawPath,speedPath,200);
		
		//MAX
		//Button.ENTER.waitForPressAndRelease();
		//PlotFigures.drawVLine(0, 0, 50);
		//Button.ENTER.waitForPressAndRelease();
		//PlotFigures.drawHLine(0, 100);
		Button.ENTER.waitForPressAndRelease();
		PlotFigures.reset();
	}
	
	
	static Point3D getPAW() {
		return new Point3D(pen.getTachoCount(),arm.getTachoCount(),wheel.getTachoCount());
	}
	
	static void moveTo(Point3D target) {
		//pen.setSpeed(100);
		arm.setSpeed(100);
		wheel.setSpeed(100);
		//pen.rotateTo(target.X);
		arm.rotateTo(target.Y);
		wheel.rotateTo(target.Z);
	}
	
	static void followPath(Point3D[] pawPath, Point3D[] speedPath, int millisecondInterval) {
		for (int i=0;i<pawPath.length;i++) {
			if (speedPath[i].x == 0) {
				pen.stop();
			}
			if (speedPath[i].y == 0) {
				arm.stop();
			}
			if (speedPath[i].z == 0) {
				wheel.stop();
			}
			//pen.setSpeed(speedPath[i].X);
			arm.setSpeed(speedPath[i].Y);
			wheel.setSpeed(speedPath[i].Z);
			//pen.rotateTo(pawPath[i].X,true);
			arm.rotateTo(pawPath[i].Y,true);
			wheel.rotateTo(pawPath[i].Z,true);
			Delay.msDelay(millisecondInterval);
			pen.rotateTo(100, false);
			arm.rotateTo(100, false);
		}
	}
	
}
