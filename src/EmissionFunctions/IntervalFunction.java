package EmissionFunctions;

import java.util.HashSet;
import java.util.Set;

import Geometry.Vector;

/**
 * Emits a new particle every interval number of updates. The
 * particle is emitted in the specified direction.
 */
public class IntervalFunction extends EmissionFunction  {
	private int _interval;
	private int _current_count = 0;
	private Vector _direction;

	public IntervalFunction(int interval, Vector init_direction) {
		_interval = interval;
		_direction = init_direction;
	}

	@Override
	public Set<Vector> emission(int time) {
		if (_current_count >= _interval) {
			_current_count = 0;
			Set<Vector> s = new HashSet<Vector>();
			s.add(_direction);
			return s;
		}
		_current_count++;
		return null;
	}
	
	public int interval() {
		return _interval;
	}
	
	public void interval(int new_interval) {
		_interval = new_interval;
	}
	
	public Vector direction() {
		return _direction;
	}
	
	public void direction(Vector new_direction) {
		_direction = new_direction;
	}

}
