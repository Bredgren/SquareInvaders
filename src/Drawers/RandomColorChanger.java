package Drawers;

import java.awt.Color;

import Entities.Entity;

public class RandomColorChanger extends ColorChanger {
	private int _interval;
	private int _current_count = 0;
	private Color _color;
	
	public RandomColorChanger(int interval) {
		_interval = interval;
		_color = randomColor();
	}
	
	@Override
	public Color color(Entity entity, int time) {
		if (_current_count >= _interval) {
			_color = randomColor();
			_current_count = 0;
		}
		_current_count++;
		return _color;
	}

	private Color randomColor() {
		int r = randomRGB();
		int g = randomRGB();
		int b = randomRGB();
		return new Color(r, g, b);
	}
	
	private int randomRGB() {
		return (int) (Math.random() * 255);
	}
}
