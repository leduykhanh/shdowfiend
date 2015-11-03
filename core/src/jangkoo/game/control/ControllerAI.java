package jangkoo.game.control;


import java.util.ArrayList;

import jangkoo.game.assets.Settings;
import jangkoo.game.control.Controller.GameState;
import jangkoo.game.control.Controller.RazeColor;
import jangkoo.game.model.BaseAI;
import jangkoo.game.model.Creep;
import jangkoo.game.model.Hit;
import jangkoo.game.model.Raze;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.model.Tower;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;

public class ControllerAI implements Disposable {
	public RazeColor razeColor = RazeColor.RED;
	public ArrayList<Raze> razes = new ArrayList<Raze>();
	public ArrayList<Raze> razesAI = new ArrayList<Raze>();
	public ArrayList<Raze> removedRazes = new ArrayList<Raze>();
	public ArrayList<Raze> removedRazesAI = new ArrayList<Raze>();
	public ShadowFiend shadowfiend;
	public BaseAI heroAI;
	public ArrayList<Creep> heroes = new ArrayList<Creep>();
	public ArrayList<Creep> removedHeroes = new ArrayList<Creep>();
	public boolean onBlink = false,onWard = false;
	public ArrayList<Tower> towers = new ArrayList<Tower>();
	public ArrayList<Tower> removedTowers = new ArrayList<Tower>();
	public static int total = 0;
	public static final int numberOfHeroes = 10;
	public Raze click ;
	public float sfStateTime = 0;
	public GameState gameState;
	public float timer = 0;
	public OrthographicCamera camera;
	public OrthographicCamera uiCamera;

	public float raze1CD = 0;
	public float raze2CD = 0;
	public float raze3CD = 0;
	public float blinkCD = 0;
	public float raze1AICD = 0;
	public float raze2AICD = 0;
	public float raze3AICD = 0;
	/** our box2D world **/
	public World world;
	public Body sfBody,wardBody;
	public BodyDef boxBodyDef,wardBodyDef;
	public ControllerAI(ShadowFiend shadowfiend) {
		world = new World(new Vector2(0, 0), true);
		CircleShape ballShape = new CircleShape();
		ballShape.setRadius(1.0f);

		FixtureDef def = new FixtureDef();
		def.restitution = 0.9f;
		def.friction = 0.01f;
		def.shape = ballShape;
		def.density = 1f;
		
		boxBodyDef = new BodyDef();
		boxBodyDef.position.set(shadowfiend.v2Position);
		boxBodyDef.type = BodyType.DynamicBody;
		
		wardBodyDef = new BodyDef();
		boxBodyDef.position.set(shadowfiend.v2Position);
		wardBodyDef.type = BodyType.DynamicBody;
		
		sfBody = world.createBody(boxBodyDef);
		sfBody.createFixture(def);
		
		this.shadowfiend = shadowfiend;
		heroAI = new BaseAI(6*Settings.CAMERA_WIDTH/7,Settings.CAMERA_HEIGHT/2,180);
		heroAI.setHp(200);
		heroAI.setDamage(0);
		towers.add (new Tower(6*Settings.CAMERA_WIDTH/7,Settings.CAMERA_HEIGHT/2));
		
		click = new Raze(0);
		click.aliveTime = 0.1f;
		gameState = GameState.Start;
		camera= new OrthographicCamera(Settings.CAMERA_WIDTH, Settings.CAMERA_HEIGHT);
		uiCamera =new OrthographicCamera(Settings.CAMERA_WIDTH, Settings.CAMERA_HEIGHT);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		uiCamera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void update(float delta) {
		if(gameState == GameState.Buying){
			return;
		}
		else if(gameState == GameState.Pause){
			timer += delta;
			if (timer > 1){
				timer =0;
				gameState = GameState.Run;
			}
		}
		else if(gameState == GameState.Lost){
			timer += delta;
			if (timer > 3){
				timer =0;
				gameState = GameState.Done;
			}
		}
		else if(gameState == GameState.Won){
			timer += delta;
			if (timer > 3){
				timer =0;
				gameState = GameState.Done;
			}
		}
		else {
		shadowfiend.update(delta);

		sfStateTime += delta;
		for(Raze raze : razes){
			raze.v2Position.x = ((float) (shadowfiend.v2Position.x+ raze.distance*shadowfiend.v2Velocity.x/shadowfiend.getSpeed()));
			raze.v2Position.y =((float) (shadowfiend.v2Position.y+ raze.distance*shadowfiend.v2Velocity.y/shadowfiend.getSpeed()));
			raze.update(delta);
			}
		for(Raze raze : razesAI){
			raze.v2Position.x = ((float) (heroAI.v2Position.x+ raze.distance*heroAI.v2Velocity.x/heroAI.getSpeed()));
			raze.v2Position.y =((float) (heroAI.v2Position.y+ raze.distance*heroAI.v2Velocity.y/heroAI.getSpeed()));
			raze.update(delta);
			}
		for(Tower tower:towers){
			for(Hit hit:tower.hits){
				hit.setXy_target(shadowfiend.v2Position.x, shadowfiend.v2Position.y);
				hit.update(delta);
				if(!hit.isMoving){
					tower.removedHits.add(hit);
					shadowfiend.setHp(shadowfiend.getHp() - tower.getDamage());
				}
				if(shadowfiend.getHp()<1){
					gameState = GameState.Lost;
					Settings.rank-=25;
					}
			}
			for(Hit hit:tower.removedHits){
				tower.hits.remove(hit);
			}
		}


		click.update(delta);
		checkCollide();
		checkNearTower();
		checkHit(delta);
		updateAI(delta);
		checkCoolDown(delta);
		updateSight();
		}

	}
	public void updateSight(){
		boxBodyDef.position.set(shadowfiend.v2Position);
		sfBody = world.createBody(boxBodyDef);
	}
	public void checkCoolDown(float delta){
		if(raze1CD > 0) raze1CD -= delta;
		if(raze2CD > 0) raze2CD -= delta;
		if(raze3CD > 0) raze3CD -= delta;
		if(blinkCD > 0) blinkCD -= delta;
		if(raze1AICD > 0) raze1AICD -= delta;
		if(raze2AICD > 0) raze2AICD -= delta;
		if(raze3AICD > 0) raze3AICD -= delta;
	} 
	private void checkHit(float delta) {
		for(Raze raze : razes){
			if(raze.v2Position.dst(heroAI.v2Position)< 120&& raze.aliveTime<2*delta)
			{	heroAI.setHp(heroAI.getHp() - shadowfiend.baseDamage - shadowfiend.getDamage());
				if(heroAI.getHp()<1){
					gameState = GameState.Won;
					Settings.rank+=25;
					}
				}
			if(towers!=null)
				for(Tower tower:towers){
				if(raze.v2Position.dst(tower.v2Position)< 150&& raze.aliveTime<2*delta)
				{
					tower.hp -= shadowfiend.baseDamage + shadowfiend.getDamage();
					if(tower.hp<1){
						Settings.gold+=300;
						removedTowers.add(tower);
					}
					}
				}
			for(Tower tower:removedTowers)
				towers.remove(tower);
		}
		for(Raze raze : razesAI){
			if(raze.v2Position.dst(shadowfiend.v2Position)< 120&& raze.aliveTime<2*delta)
			{	shadowfiend.setHp(shadowfiend.getHp() - heroAI.baseDamage - heroAI.getDamage());
				if(shadowfiend.getHp()<1){
					gameState = GameState.Lost;
					Settings.rank-=25;
					}
				}
		}
	}

	//
	public void checkCollide() {

		if(shadowfiend.getHp()<0) gameState = GameState.Lost;
	}

	public void updateAI(float delta){
		float distance = shadowfiend.v2Position.dst(heroAI.v2Position);
		if(distance> 400) return;
		if(distance> 300){
			heroAI.setXy_target(shadowfiend.v2Position.x,shadowfiend.v2Position.y);

		}
		else if(distance < 300 && distance > 150){
			if(raze3AICD<delta) razesAI.add(new Raze(240));
			raze3AICD = 3;
		}
		else if(distance < 150 && distance > 100){
			if(raze2AICD<delta) razesAI.add(new Raze(120));
			raze2AICD = 3;
		}
		else if(distance < 100){
			if(raze1AICD<delta) razesAI.add(new Raze(600));
			raze1AICD = 3;
		}
		heroAI.update(delta);
	}
	public boolean gameOver() {
		return ((shadowfiend.getHp() < 1 || heroAI.getHp() < 0)&& gameState == GameState.Done);
	}
	public void checkNearTower(){
		if(towers!=null)
			for(Tower tower:towers){
				if(tower.v2Position.dst(shadowfiend.v2Position) < 400 && tower.hits.size() == 0)
					tower.hits.add(new Hit(tower.v2Position.x,tower.v2Position.y + 180,200,300));
			}
			}
	public void updateCamera(){
		
	}
}
