package EmissionFunctions;

import java.util.HashSet;
import java.util.Set;

import Geometry.Vector;

/**
 * Several bursts in random directions at each time unit.
 */
public class BurstFunction extends EmissionFunction {
	private int _min_burst;
	private int _max_burst;
	private double _min_speed;
	private double _max_speed;
	
	public BurstFunction(int min_burst, int max_burst,
			double min_speed, double max_speed) {
		_min_burst = min_burst;
		_max_burst = max_burst;
		_min_speed = min_speed;
		_max_speed = max_speed;
	}
	
	@Override
	public Set<Vector> emission(int time) {
		Set<Vector> v = new HashSet<Vector>();
		int count = (int) ((Math.random() * _max_burst) + _min_burst);
		for (int i = 0; i < count; i++) {
			v.add(randomVector());
		}
		return v;
	}

	private Vector randomVector() {
		double angle = Math.random() * (Math.PI * 2);
		double x = Math.cos(angle);
		double y = Math.sin(angle);
		Vector dir = new Vector(x, y);
		double speed = (Math.random() * _max_speed) + _min_speed;
		return dir.scale(speed);
	}
}
