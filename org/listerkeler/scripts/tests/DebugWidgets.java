package org.listerkeler.scripts.tests;

import java.awt.Graphics2D;

import org.vinsert.bot.script.Script;
import org.vinsert.bot.script.ScriptManifest;
import org.vinsert.bot.script.api.Widget;


@ScriptManifest(name = "Debug Widgets", authors = "SpK", version = 1.5D)
public class DebugWidgets extends Script {

	@Override
	public boolean init() {
		return true;
	}

	@Override
	public int pulse() {
		return 1000;
	}

	@Override
	public void close() {

	}

	@Override
	public void render(Graphics2D g) {
		Widget[] validWidgets = widgets.get(190);
        System.out.println("Valids length is: " + validWidgets.length);
		for (Widget valid : validWidgets) {
			if (valid != null) {
                System.out.println("Valid id " + valid.getId() + ": " + valid.getText());
                System.out.println("Valid id " + valid.getId() + ": " + valid.getTooltip());
                System.out.println("Valid id " + valid.getId() + ": " + valid.getSelectedAction());
                System.out.println("Valid id " + valid.getId() + ": " + valid.getSpellName());


                for (int i = 0; i < 3; i++) {
                    System.out.println("For #2 get(190, " + i + ") getParentId: " + widgets.get(190, i).getParentId());
                    System.out.println("For #2 get(190, " + i + ") getId: " + (widgets.get(190, i).getId()));
                    System.out.println("For #2 get(190, " + i + ") getName: " + widgets.get(190, i).getName());
                }

                for (int i = 3; i < 6; i++) {
                    System.out.println("For #3 get(190, " + i + ") getParentId: " + widgets.get(190, i).getParentId());
                    System.out.println("For #2 get(190, " + i + ") getId: " + (widgets.get(190, i).getId()));
                    System.out.println("For #3 get(190, " + i + ") getName: " + widgets.get(190, i).getName());
                }
			}
		}
	}

}