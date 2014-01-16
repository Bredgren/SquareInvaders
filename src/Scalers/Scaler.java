package Scalers;

import Entities.Entity;


/**
 * Defines scale behavior that can be applied to any Entity.
 */
public abstract class Scaler {
	public static final Scaler NULL_SCALER = new NullScaler();
	
	/**
	 * Defines scale over time by modifying the Entity's
	 * width and height.
	 * @param entity
	 * @param time
	 */
	public abstract void scale(Entity entity, int time);
}

class NullScaler extends Scaler {
	public void scale(Entity entity, int time) {
		return;
	}
}