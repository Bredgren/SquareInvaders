package Collision;

import Entities.Entity;

/**
 * Collides with nothing.
 */
public class NullCollider extends Collidable {
	public NullCollider(Entity owner, CollisionGroup group) {
		super(owner, group);
	}

	@Override
	public boolean collide(Collidable other) {
		return false;
	}

	@Override
	boolean collideCircle(CircleCollider other) {
		return false;
	}

	@Override
	boolean collideRect(RectCollider other) {
		return false;
	}

	@Override
	boolean collidePoint(PointCollider other) {
		return false;
	}

}
