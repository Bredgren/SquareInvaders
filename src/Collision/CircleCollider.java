package Collision;

import Entities.Entity;

public class CircleCollider extends Collidable {

	public CircleCollider(Entity owner, CollisionGroup group) {
		super(owner, group);
	}

	@Override
	public boolean collide(Collidable other) {
		return other.collideCircle(this);
	}

	@Override
	public boolean collideCircle(CircleCollider other) {
		return collideCircleCircle(this, other);
	}

	@Override
	boolean collideRect(RectCollider other) {
		return collideCircleRect(this, other);
	}

	@Override
	boolean collidePoint(PointCollider other) {
		return collideCirclePoint(this, other);
	}

}
