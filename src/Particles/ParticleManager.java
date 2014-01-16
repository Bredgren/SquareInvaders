package Particles;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Entities.Particle;
import Events.ParticleDeathEvent;


public class ParticleManager implements ParticleDeathEvent {
	private Set<Particle> _particles = new HashSet<Particle>();
	private Set<Particle> _deadParticles = new HashSet<Particle>();
	
	public ParticleManager() {
		
	}
	
	public Iterator<Particle> particles() {
		return _particles.iterator();
	}
	
	/**
	 * Once added you should forget about it, the manager will
	 * take care of the details.
	 * 
	 * @param p Any subclass of Particle
	 */
	public void addParticle(Particle p) {
		p.changeDeathWatcher(this);
		_particles.add(p);
	}
	
	public void clear() {
		_particles.clear();
	}
	
	public void update(int time) {
		for (Particle p : _particles) {
			p.update(time);
		}
		for (Particle p : _deadParticles) {
			if (p.collider() != null)
				p.collider().destroy();
			_particles.remove(p);
		}
		_deadParticles.clear();
	}
	
	public void draw(Graphics g, int time) {
		for (Particle p : _particles) {
			p.draw(g, time);
		}
	}

	@Override
	public void onParticleDeath(Particle p) {
		_deadParticles.add(p);
	}
}
