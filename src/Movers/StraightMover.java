package Movers;

import Entities.Entity;

/**
 * Maintains the Particle's initial velocity.
 */
public class StraightMover extends Mover {
	public StraightMover() {
	}
	
	@Override
	public void move(Entity entity, int time) {
		entity.move();
	}

}
