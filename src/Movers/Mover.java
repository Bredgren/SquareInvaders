package Movers;

import Entities.Entity;


/**
 * Defines movement behavior that can be applied to any Entity.
 */
public abstract class Mover {
	public static final Mover NULL_MOVER = new NullMover();
	
	/**
	 * Defines movement over time by modifying the Entity's
	 * velocity.
	 * @param entity
	 * @param time
	 */
	public abstract void move(Entity entity, int time);
}

class NullMover extends Mover {
	public void move(Entity entity, int time) {
		return;
	}
}