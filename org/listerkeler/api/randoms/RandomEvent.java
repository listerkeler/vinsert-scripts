package org.listerkeler.api.randoms;

import org.vinsert.bot.script.ScriptContext;

public class RandomEvent {
	
	private String randomName;
	public ScriptContext context;
	
	public RandomEvent(ScriptContext context, String randomName) {
		this.context = context;
		this.randomName = randomName;
	}
	
	public boolean contains(int[] y, int i) {
		for (int x : y) {
			if (x == i) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isValid(int id) {
		return context.widgets.get(id).length != 0;
	}
	
	public void log(String string) {
		this.context.getBot().log(randomName, string);
	}

}
