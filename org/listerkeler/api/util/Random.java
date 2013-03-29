package org.listerkeler.api.util;



public class Random {
	private static java.util.Random random = new java.util.Random();
	
	public static int random(int min, int max) {
		return random.nextInt(max - min) + min;
	}
	
	public <T> T random(T[] array) {
		return array[random(0, array.length)];
	}
	
	public static void sleep(int time) {
		try
		{
			Thread.sleep(time);
		}
		catch (InterruptedException localInterruptedException)
		{
			
		}
	}
}