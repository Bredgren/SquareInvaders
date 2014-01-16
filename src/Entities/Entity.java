package Entities;
import Collision.Collidable;
import Geometry.Vector;



public class Entity {
	protected Vector _position;
	protected Vector _velocity;
	protected int _width;
	protected int _height;
	protected int _max_life;
	protected int _current_life = 0;
	protected String _name;
	protected Collidable _collider;
	
	public Entity(Vector position, Vector velocity,
			      int width, int height, int life,
			      String name) {
		_position = position;
		_velocity = velocity;
		_width = width;
		_height = height;
		_max_life = life;
		_name = name;
		
		if (life < 0)
			_current_life = life-1;
	}
	
	public Entity(Vector position, Vector velocity,
			int width, int height, String name) {
		this(position, velocity, width, height, -1, name);
	}
	
	public String name() {
		return _name;
	}
	
	public Collidable collider() {
		return _collider;
	}
	
	public void collider(Collidable collider) {
		_collider = collider;
	}
	
	public void update(int time) {
		if (_max_life >= 0)
			_current_life++;
	}
	
	public void move() {
		_position = _position.plus(_velocity);
	}
	
	public void position(Vector new_pos) {
		_position = new_pos;
	}
	
	public Vector position() {
		return _position;
	}
	
	public void velocity(Vector new_vel) {
		_velocity = new_vel;
	}
	
	public Vector velocity() {
		return _velocity;
	}
	
	public void width(int new_width) {
		_width = new_width;
	}
	
	public int width() {
		return _width;
	}
	
	public void height(int new_height) {
		_height = new_height;
	}
	
	public int height() {
		return _height;
	}
	
	public boolean dead() {
		if (_max_life < 0) return false;
		return lifeLeft() == 0;
	}
	
	/**
	 * @return The number of calls update remaining until death
	 */
	public int lifeLeft() {
		return _max_life - _current_life;
	}
	
	/**
	 * Useful for creating functions over the particle's life
	 * @return The current life value (goes from 0 to maxLife)
	 */
	public int currentLife() {
		return _current_life;
	}
	
	public int maxLife() {
		return _max_life;
	}
	
	public void kill(boolean kill_creations) {
		_current_life = _max_life;
	}	
}
