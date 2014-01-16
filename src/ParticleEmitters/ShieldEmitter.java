package ParticleEmitters;

import Collision.CollisionGroup;
import EmissionFunctions.IntervalFunction;
import Entities.Entity;
import Entities.Particle;
import Geometry.Vector;
import Movers.EntityFollowerMover;
import Particles.ParticleManager;
import Particles.ShieldParticle;

public class ShieldEmitter extends ParticleEmitter {	
	public ShieldEmitter(Entity entity, int interval,
			ShieldParticle pp, ParticleManager pm, CollisionGroup cg, String name) {
		super(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, null, null, pp, pm, cg, name);
		_mover = new EntityFollowerMover(entity);
		_ef = new IntervalFunction(interval, Vector.ZERO_VECTOR);
		pp.target(entity);
		_width = pp.size();
		_height = pp.size();
	}
	
	@Override
	protected Particle newParticle(Vector dir) {
		ShieldParticle s = (ShieldParticle) _pp;
		ShieldParticle sp = new ShieldParticle(
				s.size(), s.minRate(), s.maxRate(), _pp.life(), _pp.drawer().color());
		EntityFollowerMover m = (EntityFollowerMover) _pp.mover();
		sp.target(m.target());
		Particle p = new Particle(position(), dir, sp, _name + " Particle", this);
		//new CircleCollider(p, _cg);
		return p;
	}
}