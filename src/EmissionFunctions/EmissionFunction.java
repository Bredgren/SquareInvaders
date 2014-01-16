package EmissionFunctions;

import java.util.Set;

import Geometry.Vector;

public abstract class EmissionFunction {
	/**
	 * Returns a set of Vectors, each of which will serve as the
	 * initial velocity for a new Particle.
	 * @param time
	 * @return
	 */
	public abstract Set<Vector> emission(int time);
}
