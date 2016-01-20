package de.tuhh.diss.plotbot;

//global listeners
public class Listeners {
	public static ButtonResponse left = new ButtonResponse();
	public static ButtonResponse right = new ButtonResponse();
	public static ButtonResponse escape = new ButtonResponse();
	public static ButtonResponse enter = new ButtonResponse();
	public static SensorResponse pen = new SensorResponse();
	public static SensorResponse arm = new SensorResponse();
	public static SensorResponse light = new SensorResponse();
	public static void resetButtons() {
		left.reset();
		right.reset();
		escape.reset();
		enter.reset();
	}
}
