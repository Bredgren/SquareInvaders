package Entities;

import Game.GamePanel;
import Geometry.Vector;

public class MouseEntity extends Entity {
	private GamePanel _gp;
	
	public MouseEntity(GamePanel gp, int width, int height, String name) {
		super(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, width, height,
				name);
		_gp = gp;
	}
	
	@Override
	public Vector position() {
		return new Vector(_gp.get_mouse_x(), _gp.get_mouse_y());
	}
}
