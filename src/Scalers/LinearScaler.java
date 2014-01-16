package Scalers;

import Entities.Entity;

public class LinearScaler extends Scaler {
	private int _width_rate;
	private int _height_rate;
	private int _max_width;
	private int _max_height;
	private boolean _use_min;

	/**
	 * @param width_rate
	 * @param height_rate
	 * @param max_width -1 to not stop scaling
	 * @param max_height -1 to not stop scaling
	 * @param use_min_instead If true the maxs will be treated as mins instead
	 */
	public LinearScaler(int width_rate, int height_rate,
			int max_width, int max_height, boolean use_min_instead) {
		_width_rate = width_rate;
		_height_rate = height_rate;
		_max_width = max_width;
		_max_height = max_height;
		_use_min = use_min_instead;
	}
	
	/**
	 * @param rate
	 * @param max -1 to not stop scaling
	 */
	public LinearScaler(int rate, int max, boolean use_min_instead) {
		this(rate, rate, max, max, use_min_instead);
	}
	
	public LinearScaler(int width_rate, int height_rate,
			int max_width, int max_height) {
		this(width_rate, height_rate, max_width, max_height, false);
	}
	
	public LinearScaler(int rate, int max) {
		this(rate, rate, max, max, false);
	}

	@Override
	public void scale(Entity entity, int time) {
//		if (entity.width() > _max_width || entity.width() < 1)
//			_width_rate = -_width_rate;
//		
//		if (entity.height() > _max_height || entity.height() < 1)
//			_height_rate = -_height_rate;
//		
//		entity.width(entity.width() + _width_rate);
//		entity.height(entity.height() + _height_rate);
		
		if ((_use_min && entity.width() > _max_width) ||
			(!_use_min && entity.width() < _max_width))
			entity.width(entity.width() + _width_rate);
		else
			entity.width(_max_width);
		
		if ((_use_min && entity.height() > _max_height) ||
			(!_use_min && entity.height() < _max_height))
			entity.height(entity.height() + _height_rate);
		else
			entity.height(_max_height);
	}

}
