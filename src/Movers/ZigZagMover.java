package Movers;


import Entities.Entity;
import Geometry.Vector;

public class ZigZagMover extends Mover {
	private int _interval;
	private int _next_change;
	
	public ZigZagMover(int change_interval) {
		_interval = change_interval;
		_next_change = _interval;
	}
	
	@Override
	public void move(Entity entity, int time) {
		if (_next_change == 0) {
			_next_change = _interval;
			Vector old_vel = entity.velocity();
			Vector new_vel = new Vector(-old_vel.x(), old_vel.y());
			entity.velocity(new_vel);
		}
		_next_change--;
		entity.move();
	}

}
