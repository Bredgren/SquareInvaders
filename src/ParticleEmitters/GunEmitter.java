package ParticleEmitters;

import Collision.CollisionGroup;
import EmissionFunctions.IntervalFunction;
import Geometry.Vector;
import Movers.Mover;
import Particles.ParticleManager;
import Particles.ParticleProperties;

public class GunEmitter extends ParticleEmitter {
	
	/**
	 * 
	 * @param position This emitters' initial postion
	 * @param velocity This emitter's initial velocity
	 * @param mover How this emitter should move
	 * @param interval Time between shots
	 * @param aim_dir Initial direction of aim and initial velocity of bullets
	 * @param pp The type of particle to shoot
	 * @param pm The manager who should own the new particles
	 */
	public GunEmitter(Vector position, Vector velocity, Mover mover,
			int interval, Vector aim_dir, ParticleProperties pp,
			ParticleManager pm, CollisionGroup cg, String name) {
		super(position, velocity, mover, null, pp, pm, cg, name);
		_ef = new IntervalFunction(interval, aim_dir);
	}

	public void interval(int new_interval) {
		IntervalFunction ie = (IntervalFunction) _ef;
		ie.interval(new_interval);
	}
	
	public int interval() {
		return ((IntervalFunction) _ef).interval();
	}
	
	public void aim(Vector new_direction) {
		IntervalFunction ie = (IntervalFunction) _ef;
		ie.direction(new_direction);
	}
	
	// in degrees
	public void aim(double angle, double speed) {
		angle = (angle / 180) * Math.PI;
		double x = Math.cos(angle);
		double y = Math.sin(angle);
		Vector dir = new Vector(x, y);
		aim(dir.scale(speed));
	}
	
	public double aim() {
		IntervalFunction ie = (IntervalFunction) _ef;
		Vector dir = ie.direction().normalized();
		double angle = Math.atan(dir.y() / dir.x());
		angle = (angle / Math.PI) * 180;
		if (dir.x() < 0)
			angle += 180;
		return angle;
	}

//	@Override
//	protected Particle newParticle(Vector dir) {
//		return  new Particle(position(), dir, _pp);
//	}
}
