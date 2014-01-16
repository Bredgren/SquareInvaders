package ParticleEmitters;

import Collision.CollisionGroup;
import Collision.RectCollider;
import Entities.Particle;
import Geometry.Vector;
import Movers.Mover;
import Particles.ParticleManager;
import Particles.ParticleProperties;

public class LazerEmitter extends GunEmitter {

	public LazerEmitter(Vector position, Vector velocity, Mover mover,
			int interval, Vector aim_dir, ParticleProperties pp,
			ParticleManager pm, CollisionGroup cg, String name) {
		super(position, velocity, mover, interval, aim_dir, pp, pm, cg, name);
	}

	@Override
	protected Particle newParticle(Vector dir) {
		Particle p = new Particle(position(), dir, _pp, _name + " Particle");
		new RectCollider(p, _cg);
		return p;
	}
}
