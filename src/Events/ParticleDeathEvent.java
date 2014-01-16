package Events;

import Entities.Particle;

public interface ParticleDeathEvent {
	public void onParticleDeath(Particle p);
}
