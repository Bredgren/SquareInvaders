package Particles;

import Drawers.ColorChanger;
import Drawers.EmptyOvalDrawer;
import Drawers.FilledOvalDrawer;
import Movers.StraightMover;
import Scalers.Scaler;

public class BulletParticle extends ParticleProperties {

	public BulletParticle(int size, int life, ColorChanger c, boolean filled) {
		super(null, null, null, size, size, life);
		_m = new StraightMover();
		_s = Scaler.NULL_SCALER;
		if (filled) {
			_d = new FilledOvalDrawer(c);
		} else {
			_d = new EmptyOvalDrawer(c);
		}
	}
}
