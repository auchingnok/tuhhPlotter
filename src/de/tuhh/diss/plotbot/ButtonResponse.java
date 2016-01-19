package de.tuhh.diss.plotbot;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;

public class ButtonResponse implements ButtonListener {
	private static Runnable emptyRunnable = new Runnable() {public void run(){}};
	private Runnable blockPressed = emptyRunnable;
	private Runnable blockReleased = emptyRunnable;
	
	public ButtonResponse() {}
	public ButtonResponse(Runnable pressed, Runnable released) {
		blockPressed = pressed;
		blockReleased = released;
	}
	
	public void reset() {
		blockPressed = emptyRunnable;
		blockReleased = emptyRunnable;
	}
	
	public void setButtonResponse(Runnable pressed,Runnable released) {
		blockPressed = pressed;
		blockReleased = released;
	}
	
	public void setButtonPressedResponse(Runnable pressed) {
		blockPressed = pressed;
	}
	
	public void setButtonReleasedResponse(Runnable released) {
		blockReleased = released;
	}
	
	public void buttonPressed(Button b) {
		blockPressed.run();
	}
	
	public void buttonReleased(Button b) {
		blockReleased.run();
	}
}
