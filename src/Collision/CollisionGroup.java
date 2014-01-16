package Collision;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Events.CollideEvent;

/**
 * Manages a group of Collidable objects. Objects within the same group will
 * not collide with each other (unless you put them in more than one group).
 */
public class CollisionGroup {
	private Set<Collidable> _objects;
	private CollideEvent _handler;
	
	public CollisionGroup(CollideEvent handler) {
		_objects = new HashSet<Collidable>();
		_handler = handler;
	}
	
	public Iterator<Collidable> objects() {
		return _objects.iterator();
	}
	
	public void addObject(Collidable new_object) {
		_objects.add(new_object);
	}
	
	public void removeObject(Collidable object) {
		_objects.remove(object);
	}
	
	public void clear() {
		_objects.clear();
	}
	
	public boolean isInGroup(Collidable c) {
		return _objects.contains(c);
	}
	
	public void checkCollisions(CollisionGroup g) {
		for (Iterator<Collidable> i = g.objects(); i.hasNext();) {
			Collidable other = i.next();
			checkCollisions(other);
		}
	}
	
	/**
	 * Call once per update after checking collisions.
	 */
	public void update() {
		Set<Collidable> dead = new HashSet<Collidable>();
		for (Collidable c : _objects) {
			if (c.remove())
				dead.add(c);
		}
		for (Collidable c : dead) {
			_objects.remove(c);
		}
	}
	
	private void checkCollisions(Collidable other) {
		for (Collidable mine : _objects) {
			if (mine.collide(other))
				_handler.onCollision(mine, other);
		}
	}
}
