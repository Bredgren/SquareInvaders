package Movers;

import Entities.Entity;
import Geometry.Vector;

/**
 * Follows an Entity.
 */
public class EntityFollowerMover extends Mover {
	public enum Style {
		Linear,
		Accelerated,
		LinearPercent,
		AcceleratedPercent
	}
	
	private Entity _target;
	private Style _style;
	private double _factor;
	private Vector _offset;
	
	/**
	 * 
	 * @param target The Entity to follow.
	 * @param style The method of approach to use
	 * 		Linear: Always move straight toward the target
	 * 		Accelerated: Accelerate toward the target
	 * 				    (as if it had gravity)
	 * 		LinearPercent: Moves straight toward the target but
	 * 					   moves slower the closer it is
	 * 		AcceleratedPercent: Accelerates toward the target
	 *  						but moves slower the closer it is
	 * 
	 * @param factor If 0 then it will always be on top of the
	 * 				 target regardless of style, else
	 * 				 If style is Linear:
	 * 				 	The speed of approach.
	 * 				 If style is Accelerated:
	 * 					The magnitude of acceleration
	 * 				 If style is *Percent:
	 * 					The percent of the distance to the target
	 * 					to use as the speed/acceleration
	 */
	public EntityFollowerMover(Entity target, Style style, double factor,
			Vector offset) {
		_target = target;
		_style = style;
		_factor = factor;
		_offset = offset;
	}
	
	public EntityFollowerMover(Entity target, Style style, double factor) {
		this(target, style, factor, Vector.ZERO_VECTOR);
	}
	
	/**
	 * Shortcut for the the Linear style.
	 */
	public EntityFollowerMover(Entity target, double speed) {
		this(target, Style.Linear, speed, Vector.ZERO_VECTOR);
	}
	
	public EntityFollowerMover(Entity target, double speed, Vector offset) {
		this(target, Style.Linear, speed, offset);
	}
	
	/**
	 * Shortcut for sticking to the target.
	 */
	public EntityFollowerMover(Entity target) {
		this(target, 0);
	}
	
	public EntityFollowerMover(Entity target, Vector offset) {
		this(target, 0, offset);
	}

	@Override
	public void move(Entity entity, int time) {
		if (_target.dead())
			entity.kill(true);
		
		Vector target_pos = _target.position().plus(_offset);
		if (_factor == 0) {
			entity.position(target_pos);
			return;
		}
		
		Vector self_pos = entity.position();
		Vector to_target = target_pos.minus(self_pos);
		
		Vector dir_to_target = to_target.normalized();
		Vector linear_velocity = dir_to_target.scale(_factor);
		
		double dist_to_target = to_target.length();
		double mag = dist_to_target * _factor;
		Vector linear_percent_vel = dir_to_target.scale(mag);
		
		switch (_style) {
		case Linear:
			entity.velocity(linear_velocity);
			break;
		case Accelerated:
			entity.velocity(entity.velocity().plus(linear_velocity));
			break;
		case LinearPercent:
			entity.velocity(linear_percent_vel);
			break;
		case AcceleratedPercent:
			entity.velocity(entity.velocity().plus(linear_percent_vel));
			break;
		default:
			break;
		}
		
		entity.move();
	}
	
	public Entity target() {
		return _target;
	}
	
	public void target(Entity new_target) {
		_target = new_target;
	}
	
	public Style style() {
		return _style;
	}
	
	public void style(Style new_style) {
		_style = new_style;
	}
	
	public double factor() {
		return _factor;
	}
	
	public void factor(double new_factor) {
		_factor = new_factor;
	}
}
