package Collision;

import Entities.Entity;
import Geometry.Vector;

public abstract class Collidable {
	protected Entity _owner;
	protected CollisionGroup _group;
	protected boolean _remove = false;
	
	public Collidable(Entity owner, CollisionGroup group) {
		_owner = owner;
		owner.collider(this);
		_group = group;
		_group.addObject(this);
	}
	
	public Entity owner() {
		return _owner;
	}
	
	public CollisionGroup group() {
		return _group;
	}
	
	public void destroy() {
		_remove = true;
	}
	
	public boolean remove() {
		return _remove;
	}

	abstract boolean collide(Collidable other);
	abstract boolean collideCircle(CircleCollider other);
	abstract boolean collideRect(RectCollider other);
	abstract boolean collidePoint(PointCollider other);
	
	protected boolean collideCircleRect(CircleCollider c, RectCollider r) {
		Entity ec = c.owner();
		Entity er = r.owner();
		Vector c_pos = ec.position();
		Vector r_pos = er.position();
		double radius = ec.width() / 2;
		
		double dist_x = Math.abs(c_pos.x() - r_pos.x());
		double dist_y = Math.abs(c_pos.y() - r_pos.y());
		
		if (dist_x > (er.width() / 2 + radius)) return false;
		if (dist_y > (er.height() / 2 + radius)) return false;
		
		if (dist_x <= (er.width() / 2)) return true;
		if (dist_y <= (er.height() / 2)) return true;
		
		double corner_dist_sq = Math.pow((dist_x - er.width() / 2), 2) +
								Math.pow((dist_y - er.height() / 2), 2);
		
		return corner_dist_sq <= Math.pow(radius, 2);
	}
	
	protected boolean collideCirclePoint(CircleCollider c, PointCollider p) {
		Entity ec = c.owner();
		Entity ep = p.owner();
		double dist = Math.abs(ec.position().minus(ep.position()).length());
		return dist < ec.width() / 2;
	}
	
	protected boolean collideRectPoint(RectCollider r, PointCollider p) {
		Entity er = r.owner();
		Entity ep = p.owner();
		Vector p_pos = ep.position();
		return entity_left(er) < p_pos.x() &&
				entity_right(er) > p_pos.x() &&
				entity_top(er) < p_pos.y() &&
				entity_bottom(er) > p_pos.y();
	}
	
	protected boolean collideCircleCircle(CircleCollider c1, CircleCollider c2) {
		Vector c1_pos = c1.owner().position();
		Vector c2_pos = c2.owner().position();
		double dist = Math.abs(c1_pos.minus(c2_pos).length());
		double r = c1.owner().width() / 2 + c2.owner().width() / 2;
		return dist < r;
	}
	
	protected boolean collideRectRect(RectCollider r1, RectCollider r2) {
		Entity e1 = r1.owner();
		Entity e2 = r2.owner();
		return !(entity_left(e2) > entity_right(e1) ||
				 entity_right(e2) < entity_left(e1) ||
				 entity_top(e2) > entity_bottom(e1) ||
				 entity_bottom(e2) < entity_top(e1));
	}
	
	protected boolean collidePointPoint(PointCollider p1, PointCollider p2) {
		Entity e1 = p1.owner();
		Entity e2 = p2.owner();
		return e1.position().x() == e2.position().x() &&
				e1.position().y() == e2.position().y();
	}
	
	private int entity_left(Entity e) {
		return (int) e.position().x() - (e.width() / 2);
	}
	
	private int entity_right(Entity e) {
		return (int) e.position().x() + (e.width() / 2);
	}
	
	private int entity_top(Entity e) {
		return (int) e.position().y() - (e.height() / 2);
	}
	
	private int entity_bottom(Entity e) {
		return (int) e.position().y() + (e.height() / 2);
	}
}
