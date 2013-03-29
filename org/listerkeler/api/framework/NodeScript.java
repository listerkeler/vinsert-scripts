package org.listerkeler.api.framework;

import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.vinsert.bot.script.Script;

public abstract class NodeScript extends Script {

	private final Set<Node> nodes = new HashSet<Node>();
	private Iterator<Node> it = null;

	private boolean running = false;

	public abstract boolean onStart();
	public abstract void onRepaint(Graphics2D g);

	public void provide(Node... n) {
		for(Node node : n) {
			nodes.add(node);
		}
	}

	public void revoke(Node... n) {
		for(Node node : n) {
			if(nodes.contains(node)) {
				nodes.remove(node);
			}
		}
	}

	@Override
	public void render(Graphics2D arg0) {
		onRepaint(arg0);
	}

	@Override
	public void close() {
		running = false;
	}

	@Override
	public boolean init() {
		if(onStart()) {
			running = true;
		}
		return running;
	}

	@Override
	public int pulse() {
		try {
			if(running) {
				if(it == null || !it.hasNext()) {
					it = nodes.iterator();
				} else {
					final Node n = it.next();
					if(n.activate()) {
						n.execute();
					}
				}
			}
		} catch(Exception a) {
			a.printStackTrace();
		}
		return 50;
	}

}
