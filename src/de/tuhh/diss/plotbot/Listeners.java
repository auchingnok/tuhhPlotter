package de.tuhh.diss.plotbot;

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
	public static void resetButtons() {
		left.reset();
		right.reset();
		escape.reset();
		enter.reset();
	}
}
