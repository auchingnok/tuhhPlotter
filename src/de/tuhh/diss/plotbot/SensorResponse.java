package de.tuhh.diss.plotbot;

import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;

//executes runnable base on different situations
public class SensorResponse implements SensorPortListener {	
	private static Runnable emptyRunnable = new Runnable() {public void run(){}};
	private Runnable blockResponse = emptyRunnable;
	private int oldValue;
	private int newValue;

	public SensorResponse() {}
	
	public SensorResponse(Runnable refBlock) {
		blockResponse = refBlock;
	}
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		oldValue = aOldValue;
		newValue = aNewValue;
		blockResponse.run();
	}
	
	public void setResponse(Runnable block) {
		blockResponse = block;
	}
	
	public void reset() {
		blockResponse = emptyRunnable;
	}
	
	public int getOld() {
		return oldValue;
	}
	
	public int getNew() {
		return newValue;
	}
}
