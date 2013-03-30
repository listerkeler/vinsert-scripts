package org.listerkeler.scripts.tests;

import org.vinsert.bot.script.Script;
import org.vinsert.bot.script.ScriptManifest;
import org.vinsert.bot.script.api.Npc;
import org.vinsert.bot.util.Filter;

import java.awt.*;


@ScriptManifest(name = "Interacting Test", authors = "SpK", version = 1.0D)
public class InteractingTest extends Script {

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public int pulse() {
        for (Npc npc : npcs.getNpcs(new Filter<Npc>() {
            @Override
            public boolean accept(Npc npc) {
                return npc.isInteracting() && (localPlayer.getLocation().distanceTo(npc.getLocation()) <= 2);
            }
        })) {
            if ((npc.getInteracting() != null) && (localPlayer.getInteracting() != null)) {
                System.out.println(npc.getName() + " is interacting with " + npc.getInteracting().getName());
            }
        }
        return 1000;
    }

    @Override
    public void close() {

    }

    @Override
    public void render(Graphics2D g) {

    }

}