package Collision;

import Entities.Entity;

public class RectCollider extends Collidable {

	public RectCollider(Entity owner, CollisionGroup group) {
		super(owner, group);
	}

	@Override
	boolean collide(Collidable other) {
		return other.collideRect(this);
	}

	@Override
	boolean collideCircle(CircleCollider other) {
		return collideCircleRect(other, this);
	}

	@Override
	boolean collideRect(RectCollider other) {
		return collideRectRect(this, other);
	}

	@Override
	boolean collidePoint(PointCollider other) {
		return collideRectPoint(this, other);
	}

}
