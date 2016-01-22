package de.tuhh.diss.plotbot;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;

public class MotorListener implements RegulatedMotorListener{
	private Runnable blockStart;
	private Runnable blockStop;
	public MotorListener(Runnable blockStarted, Runnable blockStopped, boolean showOutput) {
		blockStart = blockStarted;
		blockStop = blockStopped;
	}
	
	public void rotationStarted(RegulatedMotor motor,int i,boolean b,long l) {
		blockStart.run();
	}
	
	public void rotationStopped(RegulatedMotor motor,int i,boolean b,long l) {
		blockStop.run();
	}
}
