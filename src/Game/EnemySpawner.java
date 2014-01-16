package Game;

import Geometry.Vector;

public class EnemySpawner {
	private Game _game;
	private int _next_straight = 100;
	private int _next_zigzag = 300;
	private int _next_boss = 1500;
	
	private boolean _boss_mode = false;
	
	public EnemySpawner(Game game) {
		_game = game;
	}
	
	public void update(int time) {
		if (_next_straight == 0)
			newStraightEnemy();
		if (_next_zigzag == 0)
			newZigZagFleet();
		if (_next_boss == 0 && !_boss_mode)
			newBoss();
		
		_next_straight--;
		_next_zigzag--;
		_next_boss--;
	}
	
	private void newStraightEnemy() {
		_game.newStraightEnemy();
		if (_boss_mode)
			_next_straight = random(200, 300);
		else
			_next_straight = random(50, 150);
	}
	
	private void newZigZagFleet() {
		double type = Math.random();
		if (type < .4) {
			newZigZagFleet(false);
		} else if (type < 0.8) {
			newZigZagFleet(true);
		} else {
			newZigZagFleet(false);
			newZigZagFleet(true);
		}
	}
	
	private void newZigZagFleet(boolean right) {
		int count = random(2, 5);
		int interval = random(30, 50);
		int spacing = 40;
		Vector vel = new Vector(3, 3);
		int x_pos = random(5, 20);
		if (right) {
			vel = new Vector(-vel.x(), vel.y());
			x_pos = Game.WIDTH - (count * spacing) - x_pos;
		}
		for (int i = 0; i < count; i++) {
			_game.newZigZagEnemy(x_pos, vel, interval);
			x_pos += spacing;
		}
		_next_zigzag = random(200, 300);
	}
	
	public void newBoss() {
		startBossMode();
	}
	
	public void startBossMode() {
		_boss_mode = true;
		_game.changeTrack(Game.BOSS_TRACK);
		_game.newBoss();
	}
	
	public void endBossMode() {
		_boss_mode = false;
		_game.changeTrack(Game.DEFAULT_TRACK);
		
		_next_boss = random(1000, 5000);
	}
	
	private int random(int min, int max) {
		int range = max - min;
		return (int) ((Math.random() * range) + min);
	}
}
