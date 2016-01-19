package de.tuhh.diss.plotbot;

import lejos.util.TimerListener;

public class DelayTimerListener implements TimerListener {
	private static Runnable emptyRunnable = new Runnable() {public void run(){}};
	private Runnable block = emptyRunnable;
	
	public DelayTimerListener(){};
	public DelayTimerListener(Runnable refBlock) {
		block = refBlock;
	}
	
	public void setResponse(Runnable response) {
		block = response;
	}
	
	public void timedOut() {
		block.run();
	}
}
