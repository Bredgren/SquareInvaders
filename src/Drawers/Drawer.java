package Drawers;

import java.awt.Color;
import java.awt.Graphics;

import Entities.Entity;

public abstract class Drawer {
	private ColorChanger _color;
	
	public Drawer(ColorChanger color) {
		_color = color;
	}
	
	public abstract void draw(Entity entity, Graphics g, int time);
	
	public ColorChanger color() {
		return _color;
	}
	
	public Color color(Entity entity, int time) {
		return _color.color(entity, time);
	}
	
	public void color(ColorChanger new_color) {
		_color = new_color;
	}
}
