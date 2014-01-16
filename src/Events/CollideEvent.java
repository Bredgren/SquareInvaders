package Events;

import Collision.Collidable;

public interface CollideEvent {
	public void onCollision(Collidable c1, Collidable c2);
}
