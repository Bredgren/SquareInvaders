package Drawers;

import java.awt.Color;

import Entities.Entity;

public class GradientColorChanger extends ColorChanger {
	private Color _start;
	private Color _end;
	private int _end_time;
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @param time How long to go from start to end
	 */
	public GradientColorChanger(Color start, Color end, int time) {
		_start = start;
		_end = end;
		_end_time = time;
	}
	
	@Override
	public Color color(Entity entity, int time) {
		int current_time = Math.min(entity.currentLife(), _end_time);
		double p = ((double) current_time) / _end_time;
		int r = (int) (_end.getRed() * p + _start.getRed() * (1 - p));
		int g = (int) (_end.getGreen() * p + _start.getGreen() * (1 - p));
		int b = (int) (_end.getBlue() * p + _start.getBlue() * (1 - p));
		return new Color(r, g, b);
	}
}
