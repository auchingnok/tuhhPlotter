package de.tuhh.diss.plotbot;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;

public class EncoderResponse implements RegulatedMotorListener {	
	private static Runnable emptyRunnable = new Runnable() {public void run(){}};
	private Runnable startResponse = emptyRunnable;
	private Runnable stopResponse = emptyRunnable;
	private int reading=0;

	public EncoderResponse() {}
	
	public void rotationStarted(RegulatedMotor motor,int tachoCount,boolean stalled,long timeStamp) {
		reading = tachoCount;
		startResponse.run();
	}
	
	public void rotationStopped(RegulatedMotor motor,int tachoCount,boolean stalled,long timeStamp) {
		reading = tachoCount;
		stopResponse.run();
	}
	
	public void setStartResponse(Runnable block) {
		startResponse = block;
	}
	
	public void setStopResponse(Runnable block) {
		stopResponse = block;
	}
	
	public void reset() {
		startResponse = emptyRunnable;
		stopResponse = emptyRunnable;
	}
	
	public int getReading() {
		return reading;
	}
}