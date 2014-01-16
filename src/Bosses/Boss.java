package Bosses;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import Collision.Collidable;
import Entities.Entity;
import Game.Game;
import Geometry.Vector;
import Movers.Mover;

public abstract class Boss {
	protected int _hp;
	protected Game _game;
	protected Mover _pos;
	protected String _name;
	protected Entity _target;
	protected boolean _recently_hit;
	protected Set<Collidable> _colliders = new HashSet<Collidable>();
	
	public Boss(Game game, Mover position, int hp, String name) {
		_game = game;
		_pos = position;
		_hp = hp;
		_name = name;
	}
	
	public abstract void update(int time);
	public abstract void draw(Graphics g, int time);
	public abstract Vector position();
	protected abstract void _kill();
	
	public void kill() {
		for (Collidable c : _colliders) {
			c.destroy();
		}
		_kill();
	}
	
	public void hit() {
		_recently_hit = true;
		_hp--;
		if (dead())
			kill();
	}
	
	public boolean dead() {
		return _hp <= 0;
	}
}
