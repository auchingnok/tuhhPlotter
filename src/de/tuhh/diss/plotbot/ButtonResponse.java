package de.tuhh.diss.plotbot;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.util.Timer;

//set response when pressing a button
//it is possible to invoke 2 more effects upon shorter and longer hold period
public class ButtonResponse implements ButtonListener {
	private static Runnable emptyRunnable = new Runnable() {public void run(){}};
	private Runnable blockPressed = emptyRunnable;
	private Runnable blockShort = emptyRunnable;
	private Runnable blockLong = emptyRunnable;
	private Runnable blockReleased = emptyRunnable;
	
	private int shortHoldMs = 800; //default short hold timeout
	private int longHoldMs = 1600; //default long hold timeout
	
	private DelayTimerListener shortListener = new DelayTimerListener();
	private DelayTimerListener longListener = new DelayTimerListener();
	private Timer shortTimer = new Timer(shortHoldMs,shortListener);
	private Timer longTimer = new Timer(longHoldMs, longListener);
	
	public ButtonResponse() {}
	
	public void reset() {
		blockPressed = emptyRunnable;
		blockReleased = emptyRunnable;
		blockShort = emptyRunnable;
		blockLong = emptyRunnable;
	}
	
	public void setResponse(Runnable pressed,Runnable released) {
		blockPressed = pressed;
		blockReleased = released;
	}
	public void setAllResponse(Runnable pressed, Runnable shortH,Runnable longH,Runnable released) {
		blockPressed = pressed;
		blockReleased = released;
		blockShort = shortH;
		blockLong = longH;
	}
	
	public void setPressedResponse(Runnable pressed) {blockPressed = pressed;}
	public void setReleasedResponse(Runnable released) {blockReleased = released;}
	public void setShortHoldResponse(Runnable shortH) {
		blockShort = shortH;
		shortListener.setResponse(blockShort);
	}
	public void setLongHoldResponse(Runnable longH) {
		blockLong = longH;
		longListener.setResponse(blockLong);
	}
	
	public void buttonPressed(Button b) {
		blockPressed.run();
		shortTimer.start();
		longTimer.start();
	}
	public void buttonReleased(Button b) {
		blockReleased.run();
		shortTimer.stop();
		longTimer.stop();
	}
}
