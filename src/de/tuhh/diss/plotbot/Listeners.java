package de.tuhh.diss.plotbot;

import lejos.robotics.RegulatedMotorListener;

//global listeners
public class Listeners {
	public static ButtonResponse left = new ButtonResponse();
	public static ButtonResponse right = new ButtonResponse();
	public static ButtonResponse escape = new ButtonResponse();
	public static ButtonResponse enter = new ButtonResponse();
	public static SensorResponse penTouch= new SensorResponse();
	public static SensorResponse armTouch = new SensorResponse();
	public static SensorResponse penLight = new SensorResponse();
	public static RegulatedMotorListener penEncoder = new EncoderResponse();
	public static RegulatedMotorListener armEncoder = new EncoderResponse();
	public static RegulatedMotorListener wheelEncoder = new EncoderResponse();
	public static void resetButtons() {
		left.reset();
		right.reset();
		escape.reset();
		enter.reset();
	}
}
