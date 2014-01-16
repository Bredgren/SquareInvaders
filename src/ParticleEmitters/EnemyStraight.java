package ParticleEmitters;

import java.awt.Graphics;

import Collision.CollisionGroup;
import Drawers.ColorChanger;
import Drawers.FilledRectDrawer;
import EmissionFunctions.IntervalFunction;
import Geometry.Vector;
import Movers.Mover;
import Particles.ParticleManager;
import Particles.ParticleProperties;

public class EnemyStraight extends ParticleEmitter {
	protected int _y_limit;
	
	public EnemyStraight(Vector position, Vector velocity,
			int bullet_speed, int bullet_interval, int width, int height,
			int y_limit, ColorChanger color, Mover mover,
			ParticleProperties pp, ParticleManager pm,
			CollisionGroup cg, String name) {
		super(position, velocity, mover, null, null, pp, pm, cg, name);
		_drawer = new FilledRectDrawer(color);
		Vector bullet_vel = velocity.normalized().scale(bullet_speed);
		_ef = new IntervalFunction(bullet_interval, bullet_vel);
		_width = width;
		_height = height;
		_y_limit = y_limit;
	}
	
	protected void _update(int time) {
		if (position().y() > _y_limit)
			kill(false);
	}

	protected void _draw(Graphics g, int time) {
		_drawer.draw(this, g, time);
	}
}
