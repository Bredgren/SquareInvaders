package ParticleEmitters;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import Collision.CircleCollider;
import Collision.CollisionGroup;
import Drawers.Drawer;
import EmissionFunctions.EmissionFunction;
import Entities.Entity;
import Entities.Particle;
import Geometry.Vector;
import Movers.Mover;
import Particles.ParticleManager;
import Particles.ParticleProperties;


/**
 * A thing that emits particles.
 */
public abstract class ParticleEmitter extends Entity {
	protected Mover _mover;
	protected Drawer _drawer;
	protected EmissionFunction _ef;
	protected ParticleProperties _pp;
	protected ParticleManager _pm;
	protected CollisionGroup _cg;
	protected boolean _active = true;
	protected Set<Particle> _particles = new HashSet<Particle>();
	
	public ParticleEmitter(Vector position, Vector velocity,
			Mover mover, Drawer d, EmissionFunction ef,
			ParticleProperties pp, ParticleManager pm,
			CollisionGroup cg, String name) {
		super(position, velocity, 0, 0, name);
		_mover = mover;
		_drawer = d;
		_ef = ef;
		_pp = pp;
		_pm = pm;
		_cg = cg;
	}
	
	public ParticleEmitter(Vector position, Vector velocity,
			Mover mover, EmissionFunction ef,
			ParticleProperties pp, ParticleManager pm,
			CollisionGroup cg, String name) {
		this(position, velocity, mover, null, ef, pp, pm, cg, name);
	}
	
	protected Particle newParticle(Vector dir) {
		Particle p = new Particle(position(), dir, _pp, _name + " Particle");
		new CircleCollider(p, _cg);
		return p;
	}
	
	public EmissionFunction emissionFunction() {
		return _ef;
	}
	
	// Override for extra behavior
	protected void _update(int time) {
		return;
	}
	
	public void update(int time) {
		_mover.move(this, time); // Not sure if this should go somewhere else.
		_update(time);
		if (_active) {
			Set<Vector> new_directions = _ef.emission(time);
			if (new_directions == null) return;
			for (Vector dir : new_directions) {
				Particle p = newParticle(dir);
				_particles.add(p);
				_pm.addParticle(p);
			}
		}
		
		Set<Particle> dead = new HashSet<Particle>();
		for (Particle p : _particles) {
			if (p.lifeLeft() == 0)
				dead.add(p);
		}
		
		for (Particle p : dead) {
			_particles.remove(p);
			if (p.collider() != null)
				p.collider().destroy();
		}
	}
	
	// Override to provide drawing functionality for the emitter itself
	protected void _draw(Graphics g, int time) {
		return;
	}

	public void draw(Graphics g, int time) {
		if (_drawer != null)
			_draw(g, time);
	}
	
	public boolean active() {
		return _active;
	}
	
	public void active(boolean active) {
		_active = active;
	}
	
	public void kill(boolean kill_particles) {
		_active = false;
		if (kill_particles)
			for (Particle p : _particles)
				p.kill(kill_particles);
		if (collider() != null)
			collider().destroy();
	}
}
