package Drawers;

import java.awt.Color;

import Entities.Entity;

public class ConstantColor extends ColorChanger {
	
	private Color _color;

	public ConstantColor(Color color) {
		_color = color;
	}

	@Override
	public Color color(Entity entity, int time) {
		return _color;
	}

}
