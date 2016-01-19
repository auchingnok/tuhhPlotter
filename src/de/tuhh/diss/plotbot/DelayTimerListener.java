package de.tuhh.diss.plotbot;

import lejos.util.TimerListener;

public class DelayTimerListener implements TimerListener {
	private Runnable block;
	public DelayTimerListener(Runnable refBlock) {
		block = refBlock;
	}
	
	public void timedOut() {
		block.run();
	}
}
