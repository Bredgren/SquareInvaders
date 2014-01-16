package Movers;

import Entities.Entity;
import Geometry.Vector;

/**
 * Modifies the Entities initial velocity by the given acceleration.
 */
public class AcceleratedMover extends Mover {
	private Vector _accel = null;
	private double _rate = 0.0;
	
	/**
	 * Accelerates in the specified direction.
	 * @param acceleration
	 */
	public AcceleratedMover(Vector acceleration) {
		_accel = acceleration;
	}
	
	/**
	 * Accelerates the entity without changing its direction (so speeds it up
	 * or slows it down.
	 * @param rate Percent change
	 */
	public AcceleratedMover(double rate) {
		_rate = rate;
	}
	
	@Override
	public void move(Entity entity, int time) {
		Vector prev = entity.velocity();
		Vector new_v;
		if (_accel != null) {
			new_v = prev.plus(_accel);
		} else {
			new_v = prev.scale(_rate);
		}
		entity.velocity(new_v);
		entity.move();
	}
	
	public Vector acceleration() {
		return _accel;
	}
	
	public void acceleration(Vector new_acceleration) {
		_accel = new_acceleration;
	}
}
