package Particles;

import java.awt.Graphics;

import Drawers.Drawer;
import Entities.Entity;
import Movers.Mover;
import Scalers.Scaler;

/**
 * Defines a particle. Can be used to generate new particles
 * with the same properties.
 */
public class ParticleProperties {
	protected Mover _m;
	protected Scaler _s;
	protected Drawer _d;
	protected int _width;
	protected int _height;
	protected int _life;
	
	public ParticleProperties(Mover m, Scaler s, Drawer d,
			int init_width, int init_height, int life) {
		_m = m;
		_s = s;
		_d = d;
		_width = init_width;
		_height = init_height;
		_life = life;
	}
	
	public void applyProperties(Entity entity, int time) {
		_m.move(entity, time);
		_s.scale(entity, time);
	}
	
	public void draw(Entity entity, Graphics g, int time) {
		_d.draw(entity, g, time);
	}
	
	public Mover mover() {
		return _m;
	}
	
	public void mover(Mover m) {
		_m = m;
	}
	
	public Scaler scaler() {
		return _s;
	}
	
	public void scaler(Scaler s) {
		_s = s;
	}
	
	public Drawer drawer() {
		return _d;
	}
	
	public void drawer(Drawer d) {
		_d = d;
	}

	public int width() {
		return _width;
	}
	
	public int height() {
		return _height;
	}
	
	public int life() {
		return _life;
	}
}
