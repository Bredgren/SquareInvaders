package Bosses;

import java.awt.Color;
import java.awt.Graphics;

import Collision.RectCollider;
import Entities.Entity;
import Game.Game;
import Geometry.Vector;
import Movers.EntityFollowerMover;
import Movers.Mover;
import ParticleEmitters.GunEmitter;

public class BasicBoss extends Boss {
	private static int _width = 100;
	private static int _height = 100;
	private static int _hp = 1000;
	private static int _weapon_interval = 5;
	private static int _weapon_speed = 10;
	private static final Color DEFAULT_COLOR = new Color(200, 0, 0);
	private static final Color HIT_COLOR = new Color(255, 100, 100);
	private Entity _body;
	private Entity _extra1;
	private Entity _extra2;
	private Entity _extra3;
	private Entity _extra4;
	private GunEmitter _gun1;
	private GunEmitter _gun2;
	private GunEmitter _gun3;
	private GunEmitter _gun4;
	private int _gun1_rotate = 1;
	private int _gun2_rotate = 1;
	private int _gun3_rotate = 1;
	private int _gun4_rotate = 1;
	private int _pattern_counter = 0;

	public BasicBoss(Game game, String name) {
		super(game, null, _hp, name);
		Vector init_pos = new Vector(Game.WIDTH / 2, -200);
		Vector target_pos = new Vector(Game.WIDTH / 2, _height + 20);
		_body = new Entity(init_pos, Vector.ZERO_VECTOR, _width, _height, -1, name + " Body");
		_target = new Entity(target_pos, Vector.ZERO_VECTOR, 0, 0, -1, name + " Target");
		_pos = new EntityFollowerMover(_target, EntityFollowerMover.Style.LinearPercent, 0.01);
		setupCollision();
		setupWeapons();
	}
	
	private void setupCollision() {
		_colliders.add(new RectCollider(_body, _game.enemy_group));
		
		_extra1 = new Entity(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, _width / 2, _height / 2, -1, _name + " Extra");
		_colliders.add(new RectCollider(_extra1, _game.enemy_group));
		
		_extra2 = new Entity(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, _width / 2, _height / 2, -1, _name + " Extra");
		_colliders.add(new RectCollider(_extra2, _game.enemy_group));
		
		_extra3 = new Entity(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, _width / 2, _height / 2, -1, _name + " Extra");
		_colliders.add(new RectCollider(_extra3, _game.enemy_group));
		
		_extra4 = new Entity(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, _width / 2, _height / 2, -1, _name + " Extra");
		_colliders.add(new RectCollider(_extra4, _game.enemy_group));
	}
	
	private void setupWeapons() {
		Vector init_dir = new Vector(0, _weapon_speed);
				
		Mover m1 = new EntityFollowerMover(_extra1);
		_gun1 = new GunEmitter(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR,
				m1, _weapon_interval, init_dir, Game.basic_enemy_bullet_pp,
				Game.enemyBulletManager, _game.enemy_group, _name + " Gun");
		
		Mover m2 = new EntityFollowerMover(_extra2);
		_gun2 = new GunEmitter(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR,
				m2, _weapon_interval, init_dir, Game.basic_enemy_bullet_pp,
				Game.enemyBulletManager, _game.enemy_group, _name + " Gun");
		
		Mover m3 = new EntityFollowerMover(_extra3);
		_gun3 = new GunEmitter(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR,
				m3, _weapon_interval, init_dir, Game.basic_enemy_bullet_pp,
				Game.enemyBulletManager, _game.enemy_group, _name + " Gun");
		
		Mover m4 = new EntityFollowerMover(_extra4);
		_gun4 = new GunEmitter(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR,
				m4, _weapon_interval, init_dir, Game.basic_enemy_bullet_pp,
				Game.enemyBulletManager, _game.enemy_group, _name + " Gun");
		
		_gun1.active(false);
		_gun2.active(false);
		_gun3.active(false);
		_gun4.active(false);
	}

	@Override
	public void update(int time) {
		move(time);
		updateWeapons(time);
	}
	
	private void move(int time) {
		_pos.move(_body, time);
		
		_extra1.position(new Vector(left(), top()));
		_extra2.position(new Vector(left(), bottom()));
		_extra3.position(new Vector(right(), top()));
		_extra4.position(new Vector(right(), bottom()));
	}
	
	private void updateWeapons(int time) {
		if (_pattern_counter == 0) {
			selectNewFirePattern();
		}
		_pattern_counter--;
		
		_gun1.update(time);
		_gun1.aim(_gun1.aim() + _gun1_rotate, _weapon_speed);
		_gun2.update(time);
		_gun2.aim(_gun2.aim() + _gun2_rotate, _weapon_speed);
		_gun3.update(time);
		_gun3.aim(_gun3.aim() + _gun3_rotate, _weapon_speed);
		_gun4.update(time);
		_gun4.aim(_gun4.aim() + _gun4_rotate, _weapon_speed);
	}
	
	private void selectNewFirePattern() {
		int fire_pattern = (int) (Math.random() * 5);
		switch (fire_pattern) {
		case 0:
			startFirePattern0();
			break;
		case 1:
			startFirePattern1();
			break;
		case 2:
			startFirePattern2();
			break;
		case 3:
			startFirePattern3();
			break;
		case 4:
			startFirePattern4();
			break;
		default:
			break;
		}
		
		if (Math.random() < 0.5) {
			_gun1_rotate = 1;
			_gun1.aim(0, _weapon_speed);
		} else {
			_gun1_rotate = -1;
			_gun1.aim(180, _weapon_speed);
		}
			
		if (Math.random() < 0.5) {
			_gun2_rotate = 1;
			_gun2.aim(0, _weapon_speed);
		} else {
			_gun2_rotate = -1;
			_gun2.aim(180, _weapon_speed);
		}
		
		if (Math.random() < 0.5) {
			_gun3_rotate = 1;
			_gun3.aim(0, _weapon_speed);
		} else {
			_gun3_rotate = -1;
			_gun3.aim(180, _weapon_speed);
		}
		
		if (Math.random() < 0.5) {
			_gun4_rotate = 1;
			_gun4.aim(0, _weapon_speed);
		} else {
			_gun4_rotate = -1;
			_gun4.aim(180, _weapon_speed);
		}
	}
	
	private void startFirePattern0() {
		_gun1.active(false);
		_gun2.active(false);
		_gun3.active(false);
		_gun4.active(false);
		_pattern_counter = 10;
	}
	
	private void startFirePattern1() {
		_gun1.active(false);
		_gun2.active(true);
		_gun3.active(false);
		_gun4.active(false);
		_pattern_counter = 200;
	}

	private void startFirePattern2() {
		_gun1.active(false);
		_gun2.active(true);
		_gun3.active(false);
		_gun4.active(true);
		_pattern_counter = 200;
	}

	private void startFirePattern3() {
		_gun1.active(false);
		_gun2.active(true);
		_gun3.active(true);
		_gun4.active(true);
		_pattern_counter = 200;
	}

	private void startFirePattern4() {
		_gun1.active(true);
		_gun2.active(true);
		_gun3.active(true);
		_gun4.active(true);
		_pattern_counter = 200;
	}
	
	@Override
	protected void _kill() {
		_body.kill(true);
		_game.newNovaExplosion(_body.position());
		_game.killBoss();
	}

	@Override
	public void draw(Graphics g, int time) {
		int half_width = _width / 2;
		int half_height = _height / 2;
		int q_width = half_width / 2;
		int q_height = half_height / 2;
		
		if (_recently_hit) {
			_recently_hit = false;
			g.setColor(HIT_COLOR);
		} else {
			g.setColor(DEFAULT_COLOR);
		}
		g.fillRect(left(), top(), _width, _height);
		
		g.setColor(DEFAULT_COLOR);
		g.fillRect(left() - q_width , top() - q_height, half_width, half_height);
		g.fillRect(left() - q_width , bottom() - q_height, half_width, half_height);
		g.fillRect(right() - q_width , top() - q_height, half_width, half_height);
		g.fillRect(right() - q_width , bottom() - q_height, half_width, half_height);
	}
	
	private int left() {
		return (int) (_body.position().x() - _width / 2);
	}
	
	private int right() {
		return (int) (_body.position().x() + _width / 2);
	}
	
	private int top() {
		return (int) (_body.position().y() - _height / 2);
	}
	
	private int bottom() {
		return (int) (_body.position().y() + _height / 2);
	}

	@Override
	public Vector position() {
		return _body.position();
	}
}
