package jangkoo.game.control;


import java.util.ArrayList;
import java.util.Random;

import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;
import jangkoo.game.model.Creep;
import jangkoo.game.model.Hit;
import jangkoo.game.model.Raze;
import jangkoo.game.model.Roshan;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.model.Tower;
import jangkoo.game.shadowfiend.ShadowFiendGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

public class Controller implements Disposable {
	public enum RazeColor {RED,GREEN};
	public RazeColor razeColor = RazeColor.RED;
	public ArrayList<Raze> razes = new ArrayList<Raze>();
	public ArrayList<Raze> removedRazes = new ArrayList<Raze>();
	public ShadowFiend shadowfiend;
	public ArrayList<Creep> creeps = new ArrayList<Creep>();
	public ArrayList<Creep> removedCreeps = new ArrayList<Creep>();
	public Creep justDied;
	public boolean onBlink = false;
	public Roshan roshan;
	public ArrayList<Tower> towers = new ArrayList<Tower>();
	public ArrayList<Tower> removedTowers = new ArrayList<Tower>();
	public static int total = 0;
	public static final int numberOfHeroes = 0;
	public Raze click ;
	public float sfStateTime = 0;
	
	public enum GameState {Start,Pause,Run,Won,Lost,Done,Buying};
	public GameState gameState;
	public float timer = 0;
	public OrthographicCamera camera,uiCamera;
	public static int NUM_OF_TOWERS = 4;
	public static int MAX_CREEPS = 5;
	public float raze1CD = 0;
	public float raze2CD = 0;
	public float raze3CD = 0;
	public float blinkCD = 0;
	Random r;
	public TiledMap tiledMap;
	TiledMapTileLayer bg_layer,bounder_layer;
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};
	private Array<Rectangle> tiles = new Array<Rectangle>();
	public Controller(ShadowFiend shadowfiend) {
		r = new Random();
		tiledMap = new TmxMapLoader().load("map/map.tmx");
//		tiledMap.getLayers().get("BG").setVisible(false);
//		tiledMap.getLayers().get("tree").setVisible(false);
//		tiledMap.getLayers().get("tower").setVisible(false);
		
		bg_layer = (TiledMapTileLayer)tiledMap.getLayers().get("BG");
		bounder_layer = (TiledMapTileLayer)tiledMap.getLayers().get("bounder");
		this.shadowfiend = shadowfiend;
		roshan = new Roshan(Settings.MAX_MAP_WIDTH - 100,Settings.CAMERA_HEIGHT/2,0,Assets.creep1An);
		for(int i = 0;i<NUM_OF_TOWERS;i++)
		towers.add (new Tower(Settings.MAX_MAP_WIDTH - Settings.CAMERA_WIDTH*i -100,Settings.CAMERA_HEIGHT/2));
		
		if(creeps.size() < MAX_CREEPS){
			for(int i=creeps.size();i<numberOfHeroes;i++)
			{
				int randW = r.nextInt(900);
				int randH = r.nextInt(1*Settings.CAMERA_HEIGHT/3) + 1*Settings.CAMERA_HEIGHT/3;
				int index = r.nextInt(4);
				creeps.add(new Creep(randW,randH,0,Assets.listAn.get(index)));
			}
		}
		click = new Raze(0);
		click.aliveTime = 0.1f;
		gameState = GameState.Start;

//		camera= new OrthographicCamera(Settings.CAMERA_WIDTH, Settings.CAMERA_HEIGHT);
		camera= new OrthographicCamera();
		camera.setToOrtho(false,Settings.CAMERA_WIDTH, Settings.CAMERA_HEIGHT);
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

		if(roshan!=null){
			if(shadowfiend.v2Position.x > (Settings.MAX_MAP_WIDTH - 500)){
				roshan.setXy_target(shadowfiend.v2Position.x,shadowfiend.v2Position.y);
				roshan.update(delta);
			}
			}
		sfStateTime += delta;
		for(Raze raze : razes){
			raze.v2Position.x = ((float) (shadowfiend.v2Position.x+ raze.distance*shadowfiend.v2Velocity.x/shadowfiend.getSpeed()));
			raze.v2Position.y =((float) (shadowfiend.v2Position.y+ raze.distance*shadowfiend.v2Velocity.y/shadowfiend.getSpeed()));
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
			}
			for(Hit hit:tower.removedHits){
				tower.hits.remove(hit);
			}
		}
		for(Hit hit:shadowfiend.hits){
			if (shadowfiend.onhit!=null)
			hit.setXy_target(shadowfiend.onhit.v2Position.x, shadowfiend.onhit.v2Position.y);
			hit.update(delta);
			if(!hit.isMoving){
				shadowfiend.removedHits.add(hit);
				if (shadowfiend.onhit!=null){
				shadowfiend.onhit.setHp(shadowfiend.onhit.getHp() - shadowfiend.getDamage()-shadowfiend.baseDamage);
				shadowfiend.onhit = null;
				}
			}
		}
		for(Hit hit:shadowfiend.removedHits){
			shadowfiend.hits.remove(hit);
		}
		for(Creep hero : creeps){
			
			if(hero.v2Position.x>Settings.MAX_MAP_WIDTH||hero.getHp()<0)
				{
				removedCreeps.add(hero);
				if(hero.getHp()<0){
					ShadowFiendGame.score ++;
					Settings.gold+=50;
					shadowfiend.setDamage(shadowfiend.getDamage() + 2);
					Settings.playCoin();
				}
				}
				
			else {
				if(shadowfiend.v2Position.dst(hero.v2Position)<300)
				{	
					hero.setXy_target(shadowfiend.v2Position.x,shadowfiend.v2Position.y);
					Hit hit = new Hit(hero.v2Position.x,hero.v2Position.y,shadowfiend.v2Position.x,shadowfiend.v2Position.y);
					if(hero.hits.size()<2)
						hero.hits.add(hit);
					}
				hero.update(delta);
				
			}
			for(Hit hit:hero.hits){
				hit.update(delta);
				if(!hit.isMoving){
					hero.removedHits.add(hit);
					if (shadowfiend.v2Position.dst(hit.v2Position) <10){
					shadowfiend.setHp(shadowfiend.getHp() - 10);
					}
				}
			}
			for(Hit hit:hero.removedHits){
				hero.hits.remove(hit);
			}
		}
		for(Creep hero:removedCreeps){
			creeps.remove(hero);
		}
		for(Raze raze :removedRazes)
			razes.remove(raze);
		if(creeps.size() < MAX_CREEPS){
			for(int i=creeps.size();i<numberOfHeroes;i++)
			{
			
			//int randW = r.nextInt(Gdx.graphics.getWidth() - 150) + 150;
			int randW = r.nextInt(Settings.MAX_MAP_WIDTH);
			int randH = r.nextInt(Gdx.graphics.getHeight()-100) + 100;
			int index = r.nextInt(3);
			creeps.add(new Creep(randW,randH,0,Assets.listAn.get(index)));
			}
		}
		click.update(delta);
		checkCollide();
		checkNearTower();
		checkHit(delta);
		checkNextLevel();
		checkCoolDown(delta);
		}

	}
	public void checkCoolDown(float delta){
		if(raze1CD > 0) raze1CD -= delta;
		if(raze2CD > 0) raze2CD -= delta;
		if(raze3CD > 0) raze3CD -= delta;
		if(blinkCD > 0) blinkCD -= delta;
		if(justDied != null) {
			justDied.update(delta);
			if (justDied.fadeTime < 0) justDied = null;
		}
	} 
	private void checkHit(float delta) {
		for(Raze raze : razes){
			for(Creep hero:creeps){
			if(raze.v2Position.dst(hero.v2Position)< 120&& raze.aliveTime<2*delta)
			{	hero.setHp(hero.getHp() - shadowfiend.baseDamage - shadowfiend.getDamage());
				if(hero.getHp()<1){
					removedCreeps.add(hero);
					justDied = new Creep(hero.v2Position.x,hero.v2Position.y,0,Assets.creep1An);
					//shadowfiend.setGold(shadowfiend.getGold()+50);
					//shadowfiend.setDamage(shadowfiend.getDamage() + 2);
					//coin.play();
					}
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
			if(roshan!=null)
				if(raze.v2Position.dst(roshan.v2Position)< 250&& raze.aliveTime<2*delta)
					{	roshan.setHp(roshan.getHp() - shadowfiend.baseDamage - shadowfiend.getDamage());
			if(roshan.getHp()<1){
				roshan = null;
				gameState = GameState.Won;
				Settings.gold+=500;
				shadowfiend.setDamage(shadowfiend.getDamage() + 2);
				ShadowFiendGame.score++;
				}
			}
		}
	}
	public void checkCollide() {
		for(Creep hero:creeps){
			if(shadowfiend.v2Position.dst(hero.v2Position) < 50)
			{	
				removedCreeps.add(hero);
				shadowfiend.setHp(shadowfiend.getHp()- hero.getDamage());
				Settings.playAttack();
			}
		}
		if(roshan!=null)
			if(shadowfiend.v2Position.dst(roshan.v2Position) < 100)
		{	
			shadowfiend.setHp(shadowfiend.getHp()- roshan.getDamage());
		}
		if(shadowfiend.getHp()<0) gameState = GameState.Lost;
		getTiles((int)shadowfiend.v2Position.x -32,Settings.CAMERA_HEIGHT -(int)shadowfiend.v2Position.y - 32 
				,(int)shadowfiend.v2Position.x + 32,Settings.CAMERA_HEIGHT -(int)shadowfiend.v2Position.y + 32,tiles);
//		System.out.println(tiles.size);
		for(Rectangle tile : tiles){
//			System.out.println(tile.x +"," + tile.y+","+shadowfiend.boundingCircle.x +","+ shadowfiend.boundingCircle.y);
			if (Intersector.overlaps(shadowfiend.boundingCircle,tile))
				shadowfiend.isMoving = false;
				shadowfiend.setXy_target(shadowfiend.v2Previous.x,shadowfiend.v2Previous.y);
				break;
		}
	}

	public void checkNextLevel() {
		if(ShadowFiendGame.score > 15)
		{
			Settings.level ++;
			shadowfiend.setHp(shadowfiend.getHp() + 20);
			shadowfiend.baseDamage += 2;
			ShadowFiendGame.score = 0;
			gameState = GameState.Pause;
		}
	}
	public boolean gameOver() {
		return ((shadowfiend.getHp() < 0 || roshan == null )&& gameState == GameState.Done);
	}
	public void checkNearTower(){
		if(towers!=null)
			for(Tower tower:towers){
				if(tower.v2Position.dst(shadowfiend.v2Position) < 300 && tower.hits.size() == 0)
					tower.hits.add(new Hit(tower.v2Position.x,tower.v2Position.y + 90,200,300));
			}
			}
	public void updateCamera(){
		if(shadowfiend.v2Position.x > Settings.CAMERA_WIDTH/2f &&shadowfiend.v2Position.x<(Settings.MAX_MAP_WIDTH - Settings.CAMERA_WIDTH -100))
		{
			camera.position.x = shadowfiend.v2Position.x + Settings.CAMERA_WIDTH/2f -100;
			
		}
	else if(shadowfiend.v2Position.x>(Settings.MAX_MAP_WIDTH - Settings.CAMERA_WIDTH/2f - 2 -100))
		camera.position.x = Settings.MAX_MAP_WIDTH - Settings.CAMERA_WIDTH/2f - 2;
	else camera.position.x = Settings.CAMERA_WIDTH/2f;
		
	}
	private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		rectPool.freeAll(tiles);
		tiles.clear();
		for (int y = startY/32; y <= endY/32; y++) {
			for (int x = startX/32; x <= endX/32; x++) {
				Cell cell = bounder_layer.getCell(x, y);
				
				if (cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x*32, Settings.CAMERA_HEIGHT - y*32, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}
}
