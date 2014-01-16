package Collision;

import Entities.Entity;
import Entities.Particle;
import Events.CollideEvent;
import Game.Game;

public class CollisionHandler implements CollideEvent {
	private Game _game;
	public CollisionHandler(Game game) {
		_game = game;
	}
	
	@Override
	public void onCollision(Collidable c1, Collidable c2) {
		System.out.println(
				c1.owner().name() + " collided with " + c2.owner().name());
		
		String object1 = c1.owner().name();
		String object2 = c2.owner().name();
		if (object1.equals("EnemyShip")) {
			if (object2.equals("Player")) {
				if (!_game.shieldsUp()) {
					killShip(c1.owner());
					killShip(c2.owner());
					_game.playerHit();
				}
			}else if (object2.equals("PlayerShield") && _game.shieldsUp()) {
				killShip(c1.owner());
				_game.score += 10;
			} else if (object2.equals("PlayerBullet Particle")) {
				killShip(c1.owner());
				destroyBullet(c2.owner());
				_game.score += 100;
			}
		} else if (object1.startsWith("BasicBoss")) {
			if (object2.equals("Player") && !_game.shieldsUp()) {
					destroyBullet(c1.owner());
					killShip(c2.owner());
					_game.playerHit();
			}
			if (object1.endsWith("Body")) {
				if (object2.equals("PlayerBullet Particle")) {
					_game.hitBoss();
					destroyBullet(c2.owner());
					_game.score += 50;
				}
			} else if (object1.endsWith("Extra")) {
				if (object2.equals("PlayerBullet Particle")) {
					destroyBullet(c2.owner());
					_game.score += 1;
				}
			} else if (object1.endsWith("Particle")) {
				if (object2.equals("PlayerShield") && _game.shieldsUp()) {
					System.out.println("destroy");
					destroyBullet(c1.owner());
					_game.score += 1;
				}
			}
		}else if (object1.equals("EnemyShip Particle")) {
			if (object2.equals("PlayerShield") && _game.shieldsUp()) {
				destroyBullet(c1.owner());
				_game.score += 1;
			} else if (object2.equals("Player")) {
				if (!_game.shieldsUp()) {
					destroyBullet(c1.owner());
					//killShip(c2.owner());
					_game.playerHit();
				}
			}
		} else if (object1.equals("ShieldPowerup")) {
			if (object2.equals("Player")) {
				Particle p = (Particle) c1.owner();
				if (p.emitter() != null)
					p.emitter().kill(true);
				c1.owner().kill(true);
				_game.activateShield();
			}
		} else if (object1.equals("RapidFirePowerup") ||
				object1.equals("RapidFirePowerup Particle")) {
			if (object2.equals("Player")) {
				Particle p = (Particle) c1.owner();
				if (p.emitter() != null)
					p.emitter().kill(true);
				c1.owner().kill(true);
				_game.activateRapidFire();
			}
		}
	}
	
	private void killShip(Entity ship) {
		_game.newDrop(ship.position());
		ship.kill(false);
		_game.newMediumExplosion(ship.position());
	}
	
	private void destroyBullet(Entity bullet) {
		bullet.kill(true);
		_game.newSmallExplosion(bullet.position());
	}

}
