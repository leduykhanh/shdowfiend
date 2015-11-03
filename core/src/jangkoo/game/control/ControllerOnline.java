package jangkoo.game.control;


import java.util.ArrayList;

import untils.Utils;
import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;
import jangkoo.game.control.Controller.GameState;
import jangkoo.game.control.Controller.RazeColor;
import jangkoo.game.model.Creep;
import jangkoo.game.model.Hit;
import jangkoo.game.model.Raze;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.model.Tower;
import jangkoo.game.shadowfiend.ShadowFiendGame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;

public class ControllerOnline implements Disposable {
	public RazeColor razeColor = RazeColor.RED;
	public ArrayList<Raze> razes = new ArrayList<Raze>();
	public ArrayList<Raze> razesAI = new ArrayList<Raze>();
	public ArrayList<Raze> removedRazes = new ArrayList<Raze>();
	public ArrayList<Raze> removedRazesAI = new ArrayList<Raze>();
	public ShadowFiend shadowfiend,shadowfiendAI;
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

	public static int MAX_MAP_WIDTH = 5000;
	public static int NUM_OF_TOWERS = 8;
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
	public ControllerOnline(ShadowFiend shadowfiend) {
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
		if(ShadowFiendGame.side == 1)
			shadowfiendAI = new ShadowFiend(6*Settings.CAMERA_WIDTH/7,Settings.CAMERA_HEIGHT/2,180);
		else shadowfiendAI = new ShadowFiend(1*Settings.CAMERA_WIDTH/7,Settings.CAMERA_HEIGHT/2,180);
		shadowfiendAI.setHp(100);
		shadowfiendAI.setDamage(0);
		for(int i = 0;i<NUM_OF_TOWERS;i++)
		towers.add (new Tower(6*Settings.CAMERA_WIDTH/7,Settings.CAMERA_HEIGHT/2,2));
		towers.add (new Tower(1*Settings.CAMERA_WIDTH/7 - 50,Settings.CAMERA_HEIGHT/2,1));
		
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
			raze.v2Position.x = ((float) (shadowfiendAI.v2Position.x+ raze.distance*shadowfiendAI.v2Velocity.x/shadowfiendAI.getSpeed()));
			raze.v2Position.y =((float) (shadowfiendAI.v2Position.y+ raze.distance*shadowfiendAI.v2Velocity.y/shadowfiendAI.getSpeed()));
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
			if(raze.v2Position.dst(shadowfiendAI.v2Position)< 120&& raze.aliveTime<2*delta)
			{	
				shadowfiendAI.setHp(shadowfiendAI.getHp() - shadowfiend.baseDamage - shadowfiend.getDamage());
				Utils.sendData(0, 0, 0,shadowfiend.getDamage(),
					shadowfiend.getHp(),shadowfiendAI.getHp());
				if(shadowfiendAI.getHp()<1){
					gameState = GameState.Won;
					Settings.rank+=25;
					Settings.wins++;
					}
				}
			if(towers!=null)
				for(Tower tower:towers){
				if(raze.v2Position.dst(tower.v2Position)< 150&& raze.aliveTime<2*delta 
						&& tower.side!=ShadowFiendGame.side)
				{
					tower.hp -= shadowfiend.baseDamage + shadowfiend.getDamage();
					if(tower.hp<1){
						Settings.gold+=25;
						removedTowers.add(tower);
					}
					}
				}
			for(Tower tower:removedTowers)
				towers.remove(tower);
		}
		for(Raze raze : razesAI){
			if(raze.v2Position.dst(shadowfiend.v2Position)< 120&& raze.aliveTime<2*delta)
			{	shadowfiend.setHp(shadowfiend.getHp() - shadowfiendAI.baseDamage - shadowfiendAI.getDamage());
				if(shadowfiend.getHp()<1){
					gameState = GameState.Lost;
					
					}
				}
		}
	}

	//
	public void checkCollide() {

		if(shadowfiend.getHp()<0) gameState = GameState.Lost;
	}

	public void updateAI(float delta){
		shadowfiendAI.update(delta);
	}
	public boolean gameOver() {
		return ((shadowfiend.getHp() < 0 || shadowfiendAI.getHp() < 0)&& gameState == GameState.Done);
	}
	public void checkNearTower(){
		if(towers!=null)
			for(Tower tower:towers){
				if(tower.v2Position.dst(shadowfiend.v2Position) < 100 && tower.hits.size() == 0
						&& tower.side!=ShadowFiendGame.side)
					tower.hits.add(new Hit(tower.v2Position.x,tower.v2Position.y + 180,200,300));
			}
			}
	public void updateCamera(){
		if(shadowfiend.v2Position.x > Settings.CAMERA_WIDTH/2f &&shadowfiend.v2Position.x<(MAX_MAP_WIDTH - Settings.CAMERA_WIDTH/2f -100))
		{
			camera.position.x = shadowfiend.v2Position.x + Settings.CAMERA_WIDTH/2f -100;
			
		}
	else if(shadowfiend.v2Position.x>(MAX_MAP_WIDTH - Settings.CAMERA_WIDTH/2f - 2 -100))
		camera.position.x = MAX_MAP_WIDTH - Settings.CAMERA_WIDTH/2f - 2;
	else camera.position.x = Settings.CAMERA_WIDTH/2f;
		
	}
}
