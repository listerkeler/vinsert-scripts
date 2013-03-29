package org.listerkeler.api.util;



public class Time {
	public static void sleep(int time) {
		try
		{
			long start = System.currentTimeMillis();
			Thread.sleep(time);
			long now;
			while (start + time > (now = System.currentTimeMillis()))
			Thread.sleep(start + time - now);
		}
		catch (InterruptedException ignored)
		{
			
		}
	}
	
	public static void sleep(int min, int max) {
		sleep(Random.random(min, max));
	}
	
	public static String format(long time) {
		StringBuilder t = new StringBuilder();
		long total_secs = time / 1000L;
		long total_mins = total_secs / 60L;
		long total_hrs = total_mins / 60L;
		int secs = (int)total_secs % 60;
		int mins = (int)total_mins % 60;
		int hrs = (int)total_hrs % 24;
		
		if (hrs < 10) {
			t.append("0");
		}
		
		t.append(hrs);
		t.append(":");
		
		if (mins < 10) {
			t.append("0");
		}
		
		t.append(mins);
		t.append(":");
		
		if (secs < 10) {
			t.append("0");
		}
		
		t.append(secs);
		
		return t.toString();
	}
}