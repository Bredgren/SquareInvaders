package Game;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;

import maryb.player.Player;
import Bosses.BasicBoss;
import Bosses.Boss;
import Collision.CircleCollider;
import Collision.CollisionGroup;
import Collision.CollisionHandler;
import Collision.PointCollider;
import Collision.RectCollider;
import Drawers.ColorChanger;
import Drawers.ConstantColor;
import Drawers.Drawer;
import Drawers.EmptyOvalDrawer;
import Drawers.FilledOvalDrawer;
import Drawers.FilledRectDrawer;
import EmissionFunctions.BurstFunction;
import Entities.Entity;
import Entities.MouseEntity;
import Entities.Particle;
import Geometry.Vector;
import Movers.EntityFollowerMover;
import Movers.Mover;
import Movers.StraightMover;
import Movers.ZigZagMover;
import ParticleEmitters.EnemyStraight;
import ParticleEmitters.ExplosionEmitter;
import ParticleEmitters.LazerEmitter;
import ParticleEmitters.ParticleEmitter;
import ParticleEmitters.ShieldEmitter;
import Particles.BulletParticle;
import Particles.ParticleManager;
import Particles.ParticleProperties;
import Particles.ShieldParticle;
import Scalers.Scaler;

// TODO Add bar indicaters for boss health, time until next boss, powerup times
// TODO smoke, fire, explosion
// TODO Awesome death ray powerup
// TODO Store scores in file
// TODO Effect when grabbing powerup

public class Game extends GamePanel {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	
	public static final String DEFAULT_TRACK = "./sound/Cut and Run.mp3";
	public static final String BOSS_TRACK = "./sound/Mechanolith.mp3";
	private Player _music_player = new Player();
	private String _current_track = "";
	private String _requested_track = DEFAULT_TRACK;
	
	private static final Color PLAYER_COLOR = Color.CYAN;
	private static final int PLAYER_SIZE = 15;
	
	private static final int GUN_1_SPEED = 15;
	private static final int GUN_1_INTERVAL = 5;
	private static final int GUN_1_SIZE = 3;
	
	private static final Vector POWERUP_VEL = new Vector(0, 3);
	private static final int POWERUP_SIZE = 20;
	private static final int SHIELD_TIME = 300; // TODO fix double collide
	private static final int RAPID_TIME = 200;
	
	private int _shield_timer = 0;
	private int _rapid_timer = 0;
	
	public static final double DROP_CHANCE = 0.05;
	public static final double GIVE_CHANCE = 0.0001;
	public static final double SHEILD_DROP_CHANCE = 0.2;
	public static final double RAPID_DROP_CHANCE = 0.8;
	
	private static final int SPAWN_Y = -100;
	private static final int BULLET_LIFE = 200;
	
	private static Entity _mouseEntity;
	private ParticleEmitter _shield;
	private ParticleEmitter _player_gun_1;
	private Set<ParticleEmitter> _player_guns = new HashSet<ParticleEmitter>();;
	
	private static final Color ENEMY_COLOR = Color.RED;
	
	public static ParticleManager enemyBulletManager;
	public static ParticleManager playerBulletManager;
	public static ParticleManager shipManager;
	public static ParticleManager powerupManager;
	public static ParticleManager effectManager;
	public static ParticleManager explosionManager;
	public static ArrayList<ParticleManager> _managers = new ArrayList<ParticleManager>();
	
	private CollisionHandler _ch = new CollisionHandler(this);
	
	public CollisionGroup enemy_group;
	public CollisionGroup player_group;
	public CollisionGroup powerup_group;
	public CollisionGroup effect_group;
	public ArrayList<CollisionGroup> _groups = new ArrayList<CollisionGroup>();
	
	private Set<ParticleEmitter> _enemies = new HashSet<ParticleEmitter>();
	private Set<ParticleEmitter> _powerups = new HashSet<ParticleEmitter>();
	private Set<ParticleEmitter> _effects = new HashSet<ParticleEmitter>();
	
	private int _time;
	private boolean _reset = false;
	private EnemySpawner _enemy_spawner;
	
	private static ParticleProperties _shield_poweup_pp;
	private static ShieldParticle _shield_powerup_sp;
	private static ParticleProperties _rapid_powerup_pp;
	private static ParticleProperties _rapid_powerup_bullet_pp;
	public static ParticleProperties basic_enemy_bullet_pp;
		
	private ColorChanger _enemy_color = new ConstantColor(ENEMY_COLOR);
	private ColorChanger _player_color = new ConstantColor(PLAYER_COLOR);
	
	private Boss _b;
	
	public int score = 0;
	private int _best_score = 0;
	
	private int _credit_time = 100;
	
	public Game() {
		super(WIDTH, HEIGHT);
		hideCursor();
        
		setup();
	}
	
	private void hideCursor() {
		ImageIcon emptyIcon = new ImageIcon(new byte[0]); 
        Cursor invisibleCursor = frame.getToolkit().createCustomCursor(  
                emptyIcon.getImage(), new Point(0,0), "Invisible");  
        frame.setCursor(invisibleCursor);
	}
	
//	@SuppressWarnings("deprecation")
//	private void showCursor() {
//        frame.setCursor(Cursor.DEFAULT_CURSOR);
//	}
		
	private ParticleManager newParticleManager() {
		ParticleManager m = new ParticleManager();
		_managers.add(m);
		return m;
	}
	
	private CollisionGroup newCollisionGroup() {
		CollisionGroup cg = new CollisionGroup(_ch);
		_groups.add(cg);
		return cg;
	}
	
	private void reset() {
		_reset = false;
		
		if (score > _best_score)
			newBestScore(score);
				
		_player_guns.clear();
		_enemies.clear();
		_powerups.clear();
		_effects.clear();
		
		for (ParticleManager pm : _managers)
			pm.clear();
		_managers.clear();
		
		for (CollisionGroup cg : _groups)
			cg.clear();
		_groups.clear();
		
		setup();
	}
	
	private void setupProprties() {
		Mover m = new StraightMover();
		Scaler s = Scaler.NULL_SCALER;
//		Drawer d = new EmptyOvalDrawer(_player_color);
//		_shield_poweup_pp = new ParticleProperties(
//				m, s, d, POWERUP_SIZE, POWERUP_SIZE, 400);
//		
//		int shield_life = 100; // individual particle life's
//		int shield_min_rate = 1;
//		int shield_max_rate = 2;
//		_shield_powerup_sp = new ShieldParticle(
//				POWERUP_SIZE, shield_min_rate, shield_max_rate, shield_life,
//				_player_color);
		
		Drawer d2 = new EmptyOvalDrawer(_player_color);
		_rapid_powerup_pp = new ParticleProperties(
				m, s, d2, POWERUP_SIZE, POWERUP_SIZE, 400);
		
		int width = 1;
		int height = 3;
		int life = 4;
		
		Mover bullet_m = new StraightMover();
		Scaler bullet_s = Scaler.NULL_SCALER;
		Drawer bullet_d = new FilledRectDrawer(_player_color);
		_rapid_powerup_bullet_pp = new ParticleProperties(
				bullet_m, bullet_s, bullet_d, width, height, life);
		
		setupBulletProperties();
	}
	
	private void setupBulletProperties() {
		int bullet_size = 10;
		boolean bullet_filled = true;
		basic_enemy_bullet_pp = new BulletParticle(
				bullet_size, BULLET_LIFE, _enemy_color, bullet_filled);
	}
	
	private void setup() {
		score = 0;
		_time = 0;
		
		setupProprties();
		
		_enemy_spawner = new EnemySpawner(this);
		
		enemyBulletManager = newParticleManager();
		playerBulletManager = newParticleManager();
		powerupManager = newParticleManager();
		shipManager = newParticleManager();
		effectManager = newParticleManager();
		explosionManager = newParticleManager();
		
		enemy_group = newCollisionGroup();
		player_group = newCollisionGroup();
		powerup_group = newCollisionGroup();
		effect_group = newCollisionGroup();
		
		setupPlayer();
		
		_music_player.setCurrentVolume(0.5f);
		changeTrack(DEFAULT_TRACK);
		
		_b = null;
	}
	
	private void setupPlayer() {
		_mouseEntity = new MouseEntity(this, 10, 10, "Mouse");
		
		setupPlayerShields();
		setupPlayerWeapons();
		setupPlayerShip();
	}
	
	private void setupPlayerShields() {
		int shield_size = 50;
		int shield_life = 100; // individual particle life's
		int shield_interval = 10;
		int shield_min_rate = 1;
		int shield_max_rate = 2;
		ShieldParticle sp = new ShieldParticle(
				shield_size, shield_min_rate, shield_max_rate, shield_life,
				_player_color);
		_shield = new ShieldEmitter(_mouseEntity, shield_interval, sp,
				effectManager, player_group, "PlayerShield");
		new CircleCollider(_shield, player_group);
		_shield.active(false);
	}
	
	private void setupPlayerShip() {
		Mover m = new EntityFollowerMover(_mouseEntity);
		Scaler s = Scaler.NULL_SCALER;
		Drawer d = new FilledOvalDrawer(_player_color);
		ParticleProperties player_p = new ParticleProperties(
				m, s, d, PLAYER_SIZE, PLAYER_SIZE, -1);
		Particle p = new Particle(Vector.ZERO_VECTOR, Vector.ZERO_VECTOR,
				player_p, "Player");
		new PointCollider(p, player_group);
		shipManager.addParticle(p);
	}
	
	private void setupPlayerWeapons() {
		Mover gun_m = new EntityFollowerMover(_mouseEntity);
		Vector dir = new Vector(0, -GUN_1_SPEED);
		Mover bullet_m = new StraightMover();
		Scaler bullet_s = Scaler.NULL_SCALER;
		Drawer bullet_d = new FilledRectDrawer(_player_color);
		ParticleProperties bullet_p = new ParticleProperties(
				bullet_m, bullet_s, bullet_d, GUN_1_SIZE, GUN_1_SIZE*4+3, //+3 makes it a solid beam at full speed
				BULLET_LIFE);
		_player_gun_1 = new LazerEmitter(
				Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, gun_m,
				GUN_1_INTERVAL, dir, bullet_p, playerBulletManager,
				player_group, "PlayerBullet");
		equipGun1(true);
	}
	
	@Override
	public void update() {
		clear(Color.BLACK);
				
		if (_reset)
			reset();
		
//		if (is_mouse_down()) {
//			showCursor();
//		} else {
//			hideCursor();
//		}
		
		updateMusic();
		
//		if (_time % 20 == 0) {
//			if (_down) {
//				for (Iterator<Particle> p = pManager.particles(); p.hasNext();) {
//					Mover a = new AcceleratedMover(new Vector(0, 1));
//					p.next().properties().mover(a);
//				}
//			} else {
//				for (Iterator<Particle> p = pManager.particles(); p.hasNext();) {
//					Mover a = new AcceleratedMover(new Vector(0.5, 0));
//					p.next().properties().mover(a);
//				}
//			}
//			_down = !_down;
//		}
		
		checkInput();
		_enemy_spawner.update(_time);
		newGive();
		update(_time);
		checkCollisions();
		draw(_g, _time);
				
		_time++;
	}
	private boolean _ready = true;
	private void checkInput() {
		if (is_key_down(KEY_SPACE)) {
			if (_ready) {
//				newShieldPowerup(new Vector(WIDTH/2, HEIGHT/2));
//				newRapidFirePowerup(new Vector(WIDTH/2, HEIGHT/2));
//				newNovaExplosion(new Vector(WIDTH/2, HEIGHT/2));
				_ready = false;
			}
		} else {
			_ready = true;
		}
	}
	
	private void update(int time) {
		updatePowerups(time);
		updateEffects(time);
		updateEnemies(time);
		if (_b != null)
			_b.update(_time);
		updateParticles(time);
		updateEmitter(_player_guns, time, true);
	}
	
	private void updatePowerups(int time) {
		if (_shield_timer == 0)
			deactivateShield();
		else
			_shield_timer--;
		
		if (_rapid_timer == 0)
			deactivateRapidFire();
		else
			_rapid_timer--;
		_shield.update(_time);
	}
	
	private void updateParticles(int time) {
		for (ParticleManager m : _managers) {
			m.update(time);
		}
	}
	
	private void updateEnemies(int time) {
		updateEmitter(_enemies, time);
	}
	
	private void updateEffects(int time) {
		updateEmitter(_effects, time);
		updateEmitter(_powerups, time);
	}
	
	private void updateEmitter(Set<ParticleEmitter> emitters, int time, boolean dont_kill) {
		Set<ParticleEmitter> _dead_emitters = new HashSet<ParticleEmitter>();
		for (ParticleEmitter e : emitters) {
			e.update(_time);
			if (!e.active())
				_dead_emitters.add(e);
		}
		if (!dont_kill)
			for (ParticleEmitter e : _dead_emitters)
				emitters.remove(e);
	}
	
	private void updateEmitter(Set<ParticleEmitter> emitters, int time) {
		updateEmitter(emitters, time, false);
	}
	
	private void checkCollisions() {
		enemy_group.checkCollisions(player_group);
		powerup_group.checkCollisions(player_group);
		
		for (CollisionGroup cg : _groups) {
			cg.update();
		}
	}
	
	private void draw(Graphics g, int time) {
		drawEnemies(g, time);
		if (_b != null)
			_b.draw(g, _time);
		for (ParticleManager m : _managers) {
			m.draw(_g, time);
		}
		drawScore(g);
		
		if (_credit_time > 0) {
			g.setColor(Color.WHITE);
			g.drawString("Music by Kevin MacLeod (incompetech.com)", 5, HEIGHT - 5);
			_credit_time--;
		}
	}
	
	private void drawEnemies(Graphics g, int time) {
		for (ParticleEmitter e : _enemies) {
			e.draw(g, _time);
		}
	}
	
	private void drawScore(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("Best: " + _best_score, 5, 15);
		g.drawString("" + score, 5, 30);
	}
	
	public void activateShield() {
		if (shieldsUp()) {
			Mover m = new EntityFollowerMover(
					_mouseEntity, EntityFollowerMover.Style.Linear, 10);
			basic_enemy_bullet_pp.mover(m);
		}
		_shield.active(true);
		_shield_timer += SHIELD_TIME;
	}
	
	public void deactivateShield() {
		setupBulletProperties();
		_shield.active(false);
	}
	
	public void activateRapidFire() {
		LazerEmitter gun1 = (LazerEmitter) _player_gun_1;
		int g1_interval = gun1.interval();
		gun1.interval(g1_interval / 2);
		
		_rapid_timer += RAPID_TIME;
	}
	
	public void deactivateRapidFire() {
		LazerEmitter gun1 = (LazerEmitter) _player_gun_1;
		gun1.interval(GUN_1_INTERVAL);
	}
	
	public boolean shieldsUp() {
		for (Iterator<Particle> i = effectManager.particles(); i.hasNext();) {
			Particle p = i.next();
			if (p.name().equals("PlayerShield Particle"))
				return true;
		}
		return false;
	}
	
	public void equipGun1(boolean equip) {
		equipGun(_player_gun_1, equip);
	}
	
	private void equipGun(ParticleEmitter gun, boolean equip) {
		if (equip) {
			_player_guns.add(gun);
			gun.active(true);
		} else {
			gun.active(false);
		}
	}
	
	public boolean gunEquipped(ParticleEmitter gun) {
		return _player_guns.contains(gun);
	}
	
	public void playerHit() {
		_reset = true;
	}

	public void newSmallExplosion(Vector position) {
		ExplosionEmitter.Params exp = new ExplosionEmitter.Params();
		exp.scale_rate(1);
		exp.life(10);
		exp.slow_factor(0.5);
		exp.scale_max(1);
		exp.emission_limit(10);
		exp.burst_function(new BurstFunction(5, 10, 5, 15));
		_effects.add(new ExplosionEmitter(position, exp, explosionManager, effect_group, "Explosion"));
	}
	
	public void newMediumExplosion(Vector position) {
		_effects.add(new ExplosionEmitter(position, explosionManager, effect_group, "Explosion"));
	}
	
	public void newLargeExplosion(Vector position) {
		
	}
	
	public void newNovaExplosion(Vector position) {
		ExplosionEmitter.Params exp = new ExplosionEmitter.Params();
		exp.scale_rate(20);
		exp.life(15);
		exp.slow_factor(0.9);
		exp.scale_max(50);
		exp.emission_limit(100);
		exp.burst_function(new BurstFunction(10, 20, 20, 30));
		_effects.add(new ExplosionEmitter(position, exp, explosionManager, effect_group, "Explosion"));
	}
	
	public void newStraightEnemy(int x_position) {
		Vector position = new Vector(x_position, SPAWN_Y);
		Vector enemy_vel = new Vector(0, 3);
		Mover enemy_mover = new StraightMover();
		int bullet_speed = 10;
		int bullet_interval = 20;
		int size = 20;
		ParticleEmitter enemy = new EnemyStraight(
				position, enemy_vel, bullet_speed, bullet_interval,
				size, size, HEIGHT + size, _enemy_color, enemy_mover,
				basic_enemy_bullet_pp,
				enemyBulletManager, enemy_group, "EnemyShip");
		_enemies.add(enemy);
		new RectCollider(enemy, enemy_group);
	}
	
	public void newStraightEnemy() {
		newStraightEnemy(randomSpawnX());
	}
	
	public void newZigZagEnemy(int x_position, Vector vel, int interval) {	
		Vector position = new Vector(x_position, SPAWN_Y);
		Mover enemy_mover = new ZigZagMover(interval);
		int bullet_speed = 10;
		int bullet_interval = 20;
		int size = 20;
		
		ParticleEmitter enemy = new EnemyStraight(
				position, vel, bullet_speed, bullet_interval,
				size, size, HEIGHT + size, _enemy_color, enemy_mover,
				basic_enemy_bullet_pp,
				enemyBulletManager, enemy_group, "EnemyShip");
		_enemies.add(enemy);
		new RectCollider(enemy, enemy_group);
	}
	
	public void newZigZagEnemy(Vector vel, int interval) {
		newZigZagEnemy(randomSpawnX(), vel, interval);
	}
	
	public void newBoss() {
		_b = new BasicBoss(this, "BasicBoss");
	}
	
	public void killBoss() {
		Vector pos = _b.position();
		Vector offset = new Vector(POWERUP_SIZE, 0);
		drop(pos.minus(offset));
		drop(pos.plus(offset));
		_b = null;
		_enemy_spawner.endBossMode();
		score += 10000;
	}
	
	public void hitBoss() {
		if (_b != null)
			_b.hit();
	}
	
	public void newDrop(Vector position) {
		if (Math.random() < DROP_CHANCE) {
			drop(position);
		}
	}
		
	public void newGive() {
		if (Math.random() < GIVE_CHANCE) {
			drop(new Vector(randomSpawnX(), SPAWN_Y));
		}
	}
	
	private int randomSpawnX() {
		int buffer = 20;
		return (int) (Math.random() * (WIDTH - buffer)) + buffer / 2;
	}
	
	private void drop(Vector position) {
		double r = Math.random();
		if (r < SHEILD_DROP_CHANCE) {
			newShieldPowerup(position);
		} else if (r < RAPID_DROP_CHANCE) {
			newRapidFirePowerup(position);
		}
	}
	
	private void newShieldPowerup(Vector position) {
		Mover m = new StraightMover();
		Scaler s = Scaler.NULL_SCALER;
		Drawer d = new EmptyOvalDrawer(_player_color);
		_shield_poweup_pp = new ParticleProperties(
				m, s, d, POWERUP_SIZE, POWERUP_SIZE, 400);
		
		Particle powerup = new Particle(
				position, POWERUP_VEL, _shield_poweup_pp, "ShieldPowerup");
		new CircleCollider(powerup, powerup_group);
		
		int shield_life = 100; // individual particle life's
		int shield_min_rate = 1;
		int shield_max_rate = 2;
		_shield_powerup_sp = new ShieldParticle(
				POWERUP_SIZE, shield_min_rate, shield_max_rate, shield_life,
				_player_color);
		
		int shield_interval = 10;
		_powerups.add(new ShieldEmitter(
				powerup, shield_interval, _shield_powerup_sp,
				effectManager, powerup_group, "ShieldPowerup"));
		
		powerupManager.addParticle(powerup);
	}
	
	private void newRapidFirePowerup(Vector position) {
		Particle powerup = new Particle(
				position, POWERUP_VEL, _rapid_powerup_pp, "RapidFirePowerup");
		new CircleCollider(powerup, powerup_group);
		
		Mover gun_m = new EntityFollowerMover(powerup, new Vector(0, 8));
		Vector dir = new Vector(0, -1);
		ParticleEmitter p = new LazerEmitter(
				Vector.ZERO_VECTOR, Vector.ZERO_VECTOR, gun_m,
				1, dir, _rapid_powerup_bullet_pp, effectManager,
				powerup_group, "RapidFirePowerup");
		
		_powerups.add(p);
		powerupManager.addParticle(powerup);
	}
	
	private void newBestScore(int new_score) {
		_best_score = score;
	}
	
	public void changeTrack(String track) {
		_requested_track = track;
	}
	
	private void updateMusic() {
		if (_requested_track != _current_track) {
			_music_player.stop();
			_music_player = new Player();
			_music_player.setSourceLocation(_requested_track);
			_music_player.play();
			_current_track = _requested_track;
		}
		
		if (_current_track.equals(DEFAULT_TRACK)) {
			if (_music_player.getCurrentPosition() > 213000000) {
				_music_player.seek(0);
			}
		} else if (_current_track.equals(BOSS_TRACK)) {
			if (_music_player.getCurrentPosition() > 49000000) {
				_music_player.seek(0);
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		new Game();
	}
}
