package Drawers;

import java.awt.Graphics;

import Entities.Entity;
import Geometry.Vector;

public class FilledRectDrawer extends Drawer {

	public FilledRectDrawer(ColorChanger color) {
		super(color);
	}

	@Override
	public void draw(Entity entity, Graphics g, int time) {
		g.setColor(color(entity, time));
		Vector pos = entity.position();
		int x = (int) (pos.x() - entity.width() / 2);
		int y = (int) (pos.y() - entity.height() / 2);
		g.fillRect(x, y, entity.width(), entity.height());
	}

}
