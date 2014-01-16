package Particles;

import Drawers.ColorChanger;
import Drawers.EmptyOvalDrawer;
import Entities.Entity;
import Movers.EntityFollowerMover;
import Scalers.LinearScaler;

/**
 * Expands/contracts unevenly to/from a maximum circle.
 */
public class ShieldParticle extends ParticleProperties {
	private int _size;
	private int _rate_min;
	private int _rate_max;
	private Entity _target;
	private final static int MIN = 5;
	
	public ShieldParticle(int size, int rate_min, int rate_max, int life, ColorChanger c) {
		super(null, null, null, MIN, MIN, life);
		_size = size;
		_rate_min = rate_min;
		_rate_max = rate_max;
		int width_rate = (int) ((Math.random() * rate_max) + rate_min);
		int height_rate = (int) ((Math.random() * rate_max) + rate_min);
		if (Math.random() < 0.5) {
			_s = new LinearScaler(width_rate, height_rate, size, size);
		} else {
			_s = new LinearScaler(-width_rate, -height_rate, MIN, MIN, true);
			_width = size;
			_height = size;
		}
		_d = new EmptyOvalDrawer(c);
	}
	
	/**
	 * Must call before the update is called on the Particles you make.
	 * @param target
	 */
	public void target(Entity target) {
		_target = target;
		_m = new EntityFollowerMover(target, 0);
	}
	
	public Entity target() {
		return _target;
	}
	
	public int size() {
		return _size;
	}
	
	public int minRate() {
		return _rate_min;
	}
	
	public int maxRate() {
		return _rate_max;
	}
}
