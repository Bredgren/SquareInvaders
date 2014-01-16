package Collision;

import Entities.Entity;

public class PointCollider extends Collidable {

	public PointCollider(Entity owner, CollisionGroup group) {
		super(owner, group);
	}

	@Override
	boolean collide(Collidable other) {
		return other.collidePoint(this);
	}

	@Override
	boolean collideCircle(CircleCollider other) {
		return collideCirclePoint(other, this);
	}

	@Override
	boolean collideRect(RectCollider other) {
		return collideRectPoint(other, this);
	}

	@Override
	boolean collidePoint(PointCollider other) {
		return collidePointPoint(other, this);
	}

}
