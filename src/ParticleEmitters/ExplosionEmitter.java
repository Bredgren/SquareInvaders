package ParticleEmitters;

import java.awt.Color;

import Collision.CollisionGroup;
import Drawers.ColorChanger;
import Drawers.Drawer;
import Drawers.EmptyOvalDrawer;
import Drawers.GradientColorChanger;
import EmissionFunctions.BurstFunction;
import Entities.Particle;
import Geometry.Vector;
import Movers.AcceleratedMover;
import Movers.Mover;
import Movers.StraightMover;
import Particles.ParticleManager;
import Particles.ParticleProperties;
import Scalers.LinearScaler;
import Scalers.Scaler;

public class ExplosionEmitter extends ParticleEmitter {
	private Params _params;
	private int _emitted = 0;
	
	public ExplosionEmitter(Vector position, Params params, ParticleManager pm,
			CollisionGroup cg, String name) {
		super(position, Vector.ZERO_VECTOR, null, null, null, pm, cg, name);
		_mover = new StraightMover(); // Not moving because zero vel
		_params = params;
		_ef = params.burst_function();
		Mover m = new AcceleratedMover(params.slow_factor());
		Scaler s = new LinearScaler(params.scale_rate(), params.scale_max());
		ColorChanger c = new GradientColorChanger(
				params.start_color(), params.end_color(), params.life());
		Drawer d = new EmptyOvalDrawer(c);
		_pp = new ParticleProperties(m, s, d, 1, 1, params.life());
	}
	
	public ExplosionEmitter(Vector position, ParticleManager pm,
			CollisionGroup cg, String name) {
		this(position, Params.DEFAULT, pm, cg, name);
	}

	@Override
	protected Particle newParticle(Vector dir) {
		_emitted++;
		if (_emitted >= _params.emission_limit()) {
			_emitted = 0;
			active(false);
		}
		return new Particle(position(), dir, _pp, _name + " Particle");
	}
	
	public static class Params {
		public final static Params DEFAULT = new Params();
		
		private int _scale_rate = 10;
		private int _scale_max = 10;
		private int _life = 12;
		private double _slow_factor = 0.8;
		private int _emission_limit = 20;
		
		private Color _start = new Color(255, 0, 0);
		private Color _end = new Color(255, 200, 0);
		
		private BurstFunction _bf = new BurstFunction(1, 4, 1, 5);
		
		public int scale_rate() {
			return _scale_rate;
		}
		public void scale_rate(int new_rate) {
			_scale_rate = new_rate;
		}
		
		public int scale_max() {
			return _scale_max;
		}
		public void scale_max(int new_max) {
			_scale_max = new_max;
		}
		
		public int life() {
			return _life;
		}
		public void life(int new_life) {
			_life = new_life;
		}
		
		public double slow_factor() {
			return _slow_factor;
		}
		public void slow_factor(double new_slow_factor) {
			_slow_factor = new_slow_factor;
		}
		
		public int emission_limit() {
			return _emission_limit;
		}
		public void emission_limit(int new_limit) {
			_emission_limit = new_limit;
		}
		
		public Color start_color() {
			return _start;
		}
		public void start_color(Color new_color) {
			_start = new_color;
		}
		
		public Color end_color() {
			return _end;
		}
		public void end_color(Color new_color) {
			_end = new_color;
		}
		
		public BurstFunction burst_function() {
			return _bf;
		}
		public void burst_function(BurstFunction new_function) {
			_bf = new_function;
		}
	}
}
