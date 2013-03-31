package org.listerkeler.api.util;

import java.awt.Point;

public class Methods {

	public static Point getRandomPointNear(Point p, int offset) {
		int randNumber = Random.random(0, 4);
		Point randomPoint = null;
		
		switch (randNumber) {
			case 1:
				randomPoint = new Point(p.x + Random.random(0, offset), p.y + Random.random(0, offset));
				break;
			case 2:
				randomPoint = new Point(p.x - Random.random(0, offset), p.y - Random.random(0, offset));
				break;
			case 3:
				randomPoint = new Point(p.x - Random.random(0, offset), p.y + Random.random(0, offset));
				break;
			default:
				randomPoint = new Point(p.x + Random.random(0, offset), p.y - Random.random(0, offset));
		}
		
		return randomPoint;
	}

    public static boolean waitFor(final Condition condition, final long timeOut) {
        Timer timer = new Timer(timeOut);
        while (timer.isRunning()) {
            if (condition.validate()) {
                return true;
            }
        }
        return false;
    }

}
