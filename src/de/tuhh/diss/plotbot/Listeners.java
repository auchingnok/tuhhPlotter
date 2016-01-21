package de.tuhh.diss.plotbot;

import lejos.util.Timer;

//global listeners
public class Listeners {
	public static ButtonResponse left = new ButtonResponse();
	public static ButtonResponse right = new ButtonResponse();
	public static ButtonResponse escape = new ButtonResponse();
	public static ButtonResponse enter = new ButtonResponse();
	public static SensorResponse penTouch= new SensorResponse();
	public static SensorResponse armTouch = new SensorResponse();
	public static SensorResponse penLight = new SensorResponse();
	public static EncoderResponse penEncoder = new EncoderResponse();
	public static EncoderResponse armEncoder = new EncoderResponse();
	public static EncoderResponse wheelEncoder = new EncoderResponse();
	public static Timer shortTimer;
	public static Timer longTimer;
	public static DelayTimerListener shortListener = new DelayTimerListener();
	public static DelayTimerListener longListener = new DelayTimerListener();
	
	public static void resetButtons() {
		left.reset();
		right.reset();
		escape.reset();
		enter.reset();
	}
}
