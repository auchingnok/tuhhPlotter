package de.tuhh.diss.plotbot;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.util.Timer;

//set response when pressing a button
//it is possible to invoke 2 more effects upon shorter and longer hold period
public class ButtonResponse implements ButtonListener {
	private static Runnable emptyRunnable = new Runnable() {public void run(){}};
	private Runnable blockPressed = emptyRunnable;
	private Runnable blockShort = emptyRunnable;
	private Runnable blockLong = emptyRunnable;
	private Runnable blockReleased = emptyRunnable;

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
		Listeners.shortListener.setResponse(blockShort);
	}
	public void setLongHoldResponse(Runnable longH) {
		blockLong = longH;
		Listeners.longListener.setResponse(blockLong);
	}
	
	public void buttonPressed(Button b) {
		blockPressed.run();
		Listeners.shortTimer.start();
		//longTimer.start();
	}
	public void buttonReleased(Button b) {
		blockReleased.run();
		Listeners.shortTimer.stop();
		Listeners.longTimer.stop();
	}
}
