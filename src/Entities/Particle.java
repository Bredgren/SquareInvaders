package Entities;
import java.awt.Graphics;

import Events.ParticleDeathEvent;
import Geometry.Vector;
import ParticleEmitters.ParticleEmitter;
import Particles.ParticleProperties;

public class Particle extends Entity {
	protected ParticleProperties _properties;
	protected ParticleEmitter _emitter;
	
	private ParticleDeathEvent _pde = null;
	
	/**
	 * 
	 * @param life How many calls to update until it dies
	 * @param death_watcher Who to call when it dies
	 */
	public Particle(Vector init_pos, Vector init_vel,
					ParticleProperties properties, String name,
					ParticleEmitter emitter) {
		super(init_pos, init_vel,
				properties.width(), properties.height(), properties.life(),
				name);
		_properties = properties;
		_emitter = emitter;
	}
	
	public Particle(Vector init_pos, Vector init_vel,
			ParticleProperties properties, String name) {
		this(init_pos, init_vel, properties, name, null);
	}
	
	public void changeDeathWatcher(ParticleDeathEvent death_watcher) {
		_pde = death_watcher;
	}
	
	public ParticleEmitter emitter() {
		return _emitter;
	}
	
	public ParticleProperties properties() {
		return _properties;
	}
	
	public void update(int time) {
		_properties.applyProperties(this, time);
		super.update(time);
		if (_current_life >= _max_life && _pde != null) {
			_pde.onParticleDeath(this);
		}
	}
	
	public void draw(Graphics g, int time) {
		_properties.draw(this, g, time);
	}
}
