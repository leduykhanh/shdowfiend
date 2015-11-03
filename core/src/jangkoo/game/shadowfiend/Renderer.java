package jangkoo.game.shadowfiend;

import untils.Utils;
import jangkoo.game.model.Creep;
import jangkoo.game.model.Hit;
import jangkoo.game.model.Item;
import jangkoo.game.model.Raze;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.model.Tower;
import jangkoo.game.shadowfiend.ShadowFiendGame.ScreenType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;
import jangkoo.game.control.Controller;
import jangkoo.game.control.Controller.GameState;
import jangkoo.game.control.Controller.RazeColor;


public class Renderer {
		/** sprite batch to draw text **/
		private SpriteBatch spriteBatch;
		ShapeRenderer sr;
		/** the background texture **/
		TextButtonStyle textButtonStyle;
	    TextButton raze1,raze2,raze3,blink,next,radiance,heart,quitButton;
	    Button buyItem;
	    TextureAtlas buttonAtlas,itemAtlas;
	    Table leftBottomTable,bottomRightTable,infoTable,rightMidle,leftTable;
	    public Stage stage;
		Sprite sprite;
		Environment lights;
		Controller controller;
		String randomQuote[] = {
								"Upload your art work at www.jangkoo.com",
								"leejangkoo@gmail.com",
								"+65 97798852",
								"Kill more heroes for more damage",
								"Roshan is at the end of map",
								"You can raze the tower",
								"There is only one tower a time",
								"You are handsome",
								"Please rate the game",
								"No offense"};

		final SelectBox<String> razeColor;
		Label souls,hp,dmg,gold;
		
		TiledMapRenderer tiledMapRenderer;
		public Renderer (ShadowFiend shadowfiend) {
	        
	        
			razeColor = new SelectBox<String>(Assets.skin1);
			razeColor.setItems("Raze Color","red","green");
			razeColor.setSelected("Raze Color");
			razeColor.setSize(50,50);
			buyItem = new Button(Assets.skin1);
			buyItem.add("BUY ITEM");
			souls = new Label("SOULS ",Assets.skin1);
			hp = new Label("HP ",Assets.skin1);
			dmg = new Label("DAMAGE ",Assets.skin1);
			gold = new Label("GOLD ",Assets.skin1);
			
			buttonAtlas = new TextureAtlas(Gdx.files.internal("skill_buttons/skill.pack"));
			itemAtlas =  new TextureAtlas(Gdx.files.internal("item/item.pack"));
			try {
				controller = new Controller(shadowfiend);
				tiledMapRenderer = new OrthogonalTiledMapRenderer(controller.tiledMap);
				lights = new Environment();
				//stage = new Stage(new StretchViewport(890,500));
				stage = new Stage();
				Gdx.input.setInputProcessor(stage);
				lights.add(new DirectionalLight().set(Color.WHITE, new Vector3(-1, -0.5f, 0).nor()));
				//modelBatch = new ModelBatch();
				spriteBatch = new SpriteBatch();
				sr = new ShapeRenderer();	
				infoTable = new Table(Assets.skin1);
				infoTable.top().left();
				infoTable.setFillParent(true);
				infoTable.add(souls);
				infoTable.row();
				infoTable.add(hp);
				infoTable.row();
				infoTable.add(dmg);
				infoTable.row();
				infoTable.add(gold);
				
				bottomRightTable = new Table();
				bottomRightTable.bottom().right();
				bottomRightTable.setFillParent(true);
				
				rightMidle = new Table();
				rightMidle.setFillParent(true);
				rightMidle.right();

				leftTable = new Table();
				leftTable.setFillParent(true);
				leftTable.left();
				leftBottomTable = new Table();
				leftBottomTable.setFillParent(true);
				leftBottomTable.left();
				leftBottomTable.bottom();
				leftBottomTable.padLeft(123);
				leftBottomTable.add(razeColor).height(Settings.BUTTON_HEIGHT/2).padBottom(10);
				leftBottomTable.add(buyItem).height(Settings.BUTTON_HEIGHT/2).padBottom(10);
		        quitButton = new TextButton("",Assets.skin1,"exit");
		        quitButton.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						// TODO Auto-generated method stub
						controller.shadowfiend.setHp(-1);
						controller.gameState = GameState.Done;
						ShadowFiendGame.nextScreen = ScreenType.GAMEOVER;

					}
		        });
				buyItem.addListener(new ChangeListener() {
					public void changed (ChangeEvent event, Actor actor) {
						buyItem();
					}
				});
				razeColor.addListener(new ChangeListener() {
					public void changed (ChangeEvent event, Actor actor) {
						if(razeColor.getSelected()=="red") controller.razeColor = RazeColor.RED;
						if(razeColor.getSelected()=="green") controller.razeColor = RazeColor.GREEN;
					}
				});
				Assets.skin.addRegions(buttonAtlas);
		        textButtonStyle = new TextButtonStyle();
		        textButtonStyle.up = Assets.skin.getDrawable("raze");
		        textButtonStyle.down = Assets.skin.getDrawable("raze_pressed");
		        textButtonStyle.font = Assets.bigTitleFont;
		        raze1 = new TextButton("", textButtonStyle);
		        raze2 = new TextButton("", textButtonStyle);
		        raze3 = new TextButton("", textButtonStyle);
		        raze1.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						if(controller.raze1CD >0) return;
						addRaze(60);
						controller.raze1CD = 3;

					}
		        });
		        raze2.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						if(controller.raze2CD >0) return;
						addRaze(160);
						controller.raze2CD = 3;

					}
		        });
		        raze3.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						if(controller.raze3CD >0) return;
						addRaze(240);
						controller.raze3CD = 3;

					}
		        });
		        raze3.setWidth(50);
		        textButtonStyle = new TextButtonStyle();
		        textButtonStyle.up = Assets.skin.getDrawable("blink");
		        textButtonStyle.down = Assets.skin.getDrawable("blink_pressed");
		        textButtonStyle.font = Assets.smallfont;
		        blink = new TextButton("", textButtonStyle);
		        blink.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						// TODO Auto-generated method stub
						if(controller.blinkCD >0) return;
						controller.blinkCD = 3;
						onBlink();
						}
		        });
		        buttonAtlas = new TextureAtlas(Gdx.files.internal("blank_button/button.pack"));
		        Assets.skin.addRegions(buttonAtlas);
		        textButtonStyle = new TextButtonStyle();
		        textButtonStyle.up = Assets.skin.getDrawable("next");
		        textButtonStyle.font = Assets.smallfont;
		        next = new TextButton("", textButtonStyle);
		        next.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						// TODO Auto-generated method stub
						controller.updateCamera();
						}
		        });
		        leftTable.add(raze1).width(Settings.BUTTON_HEIGHT).height(Settings.BUTTON_HEIGHT);
		        leftTable.row();
		        leftTable.add(raze2).width(Settings.BUTTON_HEIGHT).height(Settings.BUTTON_HEIGHT);
		        leftTable.row();
		        leftTable.add(raze3).width(Settings.BUTTON_HEIGHT).height(Settings.BUTTON_HEIGHT);
		        leftTable.row();
		        leftTable.add(blink).width(Settings.BUTTON_HEIGHT).height(Settings.BUTTON_HEIGHT);
		        rightMidle.add(next).width(Settings.BUTTON_HEIGHT).height(Settings.BUTTON_HEIGHT);
		        bottomRightTable.add(quitButton).width(2*Settings.BUTTON_HEIGHT/3).height(2*Settings.BUTTON_HEIGHT/3);
		        stage.addActor(infoTable);
		        stage.addActor(leftTable);
		        stage.addActor(rightMidle);
		        stage.addActor(leftBottomTable);
		        stage.addActor(bottomRightTable);
		        spriteBatch = new SpriteBatch();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		public void render ( Controller controller,float delta) {
			// We explicitly require GL10, otherwise we could've used the GLCommon
			// interface via Gdx.gl
			GL20 gl = Gdx.gl;
			gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			this.controller = controller;
			renderMap();
			renderBackground();
			if(controller.shadowfiend.isMoving)
				sprite = new Sprite(Assets.movingSfAn.getKeyFrame(controller.sfStateTime));
			else 
				sprite = new Sprite(Assets.staySfAn.getKeyFrame(controller.sfStateTime));
			//sprite.setSize(71*Settings.SCALE_WIDTH,80*Settings.SCALE_HEIGHT);
			sprite.setRotation((float) (controller.shadowfiend.getAngle()));
//			sprite.setPosition((float)controller.shadowfiend.v2Position.x -40*Settings.SCALE_WIDTH , (float)controller.shadowfiend.v2Position.y -40*Settings.SCALE_HEIGHT);
			sprite.setPosition((float)controller.shadowfiend.v2Position.x -80 , (float)controller.shadowfiend.v2Position.y -70);
			spriteBatch.begin();
			Utils.sfEffects(spriteBatch,(int)controller.shadowfiend.v2Position.x + 10, 
					(int)controller.shadowfiend.v2Position.y + 10,delta,controller.shadowfiend.getAngle());

			if(controller.roshan!=null)
				if(controller.roshan.getHp()>0){
				//Assets.font.setScale(0.5f);
				spriteBatch.draw(Assets.roshanTexture,controller.roshan.v2Position.x,controller.roshan.v2Position.y);
				Assets.smallfont.draw(spriteBatch,controller.roshan.getHp()+"",(float)controller.roshan.v2Position.x + 40,(float)controller.roshan.v2Position.y + 200);
			}
			for(Creep hero:controller.creeps){
				//spriteBatch.draw(hero.animation.getKeyFrame(controller.sfStateTime),(float)hero.v2Position.x - 50,(float)hero.v2Position.y - 50);
				spriteBatch.draw(hero.animation.getKeyFrame(controller.sfStateTime),(float)hero.v2Position.x - 20,(float)hero.v2Position.y - 20,40,40, 30, 30, 1f, 1f,270- hero.getAngle(), false);
				Assets.smallfont.draw(spriteBatch,hero.getHp()+"",(float)hero.v2Position.x -10,(float)hero.v2Position.y + 10);
				for(Hit hit:hero.hits){
					Utils.hitEffects(spriteBatch,(int)hit.v2Position.x,(int)hit.v2Position.y,delta);
				}
			}
			spriteBatch.setProjectionMatrix(controller.uiCamera.combined);
			spriteBatch.draw(Assets.avatarAn.getKeyFrame(controller.sfStateTime),10,10,100*Settings.SCALE_WIDTH,100*Settings.SCALE_HEIGHT);
			spriteBatch.setProjectionMatrix(controller.camera.combined);
			if(controller.justDied!= null) {
				Utils.coinEffects(spriteBatch, (int)controller.justDied.v2Position.x,(int) controller.justDied.v2Position.y, delta);
				}
			
			for(Raze raze : this.controller.razes){
				if(raze.aliveTime <Raze.RAZE_LIVE_TIME)
				{
					if(controller.razeColor == RazeColor.RED)
						Utils.razeRed(spriteBatch,(int)raze.v2Position.x,(int)raze.v2Position.y,delta);
					else Utils.razeGreen(spriteBatch,(int)raze.v2Position.x,(int)raze.v2Position.y,delta);
				}
				else {
					//effect.dispose();
					this.controller.removedRazes.add(raze);
	
				}
			}
			for(Tower tower:controller.towers)
				for(Hit hit:tower.hits){
					Utils.towerHitEffects(spriteBatch,(int)hit.v2Position.x,(int)hit.v2Position.y,delta);
				}
			for(Hit hit:controller.shadowfiend.hits){
				Utils.hitEffects(spriteBatch,(int)hit.v2Position.x,(int)hit.v2Position.y,delta);
			}

			if(controller.towers.size() > 0)
				for(Tower tower:controller.towers)
				{
					spriteBatch.draw(Assets.towerAn.getKeyFrame(controller.sfStateTime),tower.v2Position.x,tower.v2Position.y,
							71*Settings.SCALE_WIDTH, 140*Settings.SCALE_HEIGHT);
					Assets.smallfont.draw(spriteBatch,tower.hp+"",(float)tower.v2Position.x + 20,(float)tower.v2Position.y + 141*Settings.SCALE_HEIGHT);
				}
			
			sprite.draw(spriteBatch);
			spriteBatch.end();
			spriteBatch.setProjectionMatrix(controller.uiCamera.combined);
			controller.uiCamera.update();
			spriteBatch.begin();
			for(int k=0;k<controller.shadowfiend.getItems().size();k++){
				Texture item = new Texture("item/"+controller.shadowfiend.getItems().get(k).nameItem + ".png");
				spriteBatch.draw(item,Settings.CAMERA_WIDTH- 100f*(k+1) -5,0);
			}
			if(controller.gameState == GameState.Lost)
				spriteBatch.draw(new Texture("data/game_over.png"),Settings.CAMERA_WIDTH/2 - 300,Settings.CAMERA_HEIGHT/2 - 100);
			if(controller.gameState == GameState.Won)
				spriteBatch.draw(new Texture("data/victory.png"),Settings.CAMERA_WIDTH/2 - 300,Settings.CAMERA_HEIGHT/2 - 100);
			if(controller.gameState == GameState.Pause)
				spriteBatch.draw(Assets.levelupTexture,Settings.CAMERA_WIDTH/2 - 120,Gdx.graphics.getHeight()/2);
			if(controller.click.aliveTime <Raze.RAZE_LIVE_TIME)
				spriteBatch.draw(Assets.clickTt,controller.click.v2Position.x-10*Settings.SCALE_WIDTH,controller.click.v2Position.y-10*Settings.SCALE_HEIGHT,30*Settings.SCALE_WIDTH,30*Settings.SCALE_HEIGHT);
			souls.setText("SOULS  "+ ShadowFiendGame.score);
			hp.setText("HP  "+controller.shadowfiend.getHp());
			gold.setText("GOLD  "+Settings.gold);
			dmg.setText("DAMAGE "+controller.shadowfiend.baseDamage + " + " + controller.shadowfiend.getDamage());
			Assets.bigTitleFont.draw(spriteBatch,randomQuote[controller.shadowfiend.getDamage()%randomQuote.length],
					100*Settings.SCALE_WIDTH + 3*Settings.BUTTON_HEIGHT + 20,30);
			Assets.smallfont.draw(spriteBatch, "LEVEL" + Settings.level, Settings.CAMERA_WIDTH - 200, Settings.CAMERA_HEIGHT -10);
			
			spriteBatch.end();
			raze1.setText((controller.raze1CD > 0)?(int)controller.raze1CD+"":"");
			raze2.setText((controller.raze2CD > 0)?(int)controller.raze2CD+"":"");
			raze3.setText((controller.raze3CD > 0)?(int)controller.raze3CD+"":"");
			blink.setText((controller.blinkCD > 0)?(int)controller.blinkCD+"":"");
			stage.act(delta);
			stage.draw();
		}

		private void renderBackground () {
//			spriteBatch.setProjectionMatrix(controller.camera.combined);
//			controller.camera.update();
//			spriteBatch.begin();
//			spriteBatch.draw(Assets.creepBackgroundTexture, 0, 2*Settings.BUTTON_HEIGHT/3, Settings.MAX_MAP_WIDTH, Settings.CAMERA_HEIGHT - 2*Settings.BUTTON_HEIGHT/3, 0, 0, Settings.MAX_MAP_WIDTH, Assets.creepBackgroundTexture.getHeight(), false, false);
//			spriteBatch.end();
			sr.begin(ShapeType.Line);
			sr.setColor(new Color(0,0,1,0));
			sr.rect(Settings.BUTTON_HEIGHT, 2*Settings.BUTTON_HEIGHT/3, Settings.CAMERA_WIDTH - Settings.BUTTON_HEIGHT -1, Gdx.graphics.getHeight() - 2*Settings.BUTTON_HEIGHT/3 -1);
			sr.circle(controller.shadowfiend.boundingCircle.x, controller.shadowfiend.boundingCircle.y, controller.shadowfiend.boundingCircle.radius);
			sr.end();
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(0,0,0,0));
			sr.rect(0, 0, Settings.BUTTON_HEIGHT, Settings.CAMERA_HEIGHT);
			sr.rect(0, 0, Settings.CAMERA_WIDTH, Settings.BUTTON_HEIGHT);
			sr.end();
			
			
			
		}
		public void onBlink(){
			this.controller.onBlink = true;
		}
		private void addRaze(float distance){
			this.controller.razes.add(new Raze(distance));
		}
		public void dispose () {
		}
		public void renderMap(){
			controller.camera.update();
//			controller.camera.setToOrtho(false);
	        tiledMapRenderer.setView(controller.camera);
	        tiledMapRenderer.render();
		}
		public void buyItem(){
			controller.gameState = GameState.Buying;
			final Table itemTable = new Table();
			itemTable.setFillParent(true);
			itemTable.center();
			Assets.skin.addRegions(itemAtlas);
	        textButtonStyle = new TextButtonStyle();
	        textButtonStyle.up = Assets.skin.getDrawable("heart");
	        textButtonStyle.down = Assets.skin.getDrawable("heart");
	        textButtonStyle.font = Assets.smallfont;
	        heart = new TextButton("", textButtonStyle);
	        heart.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					Dialog dialog = new Dialog("Info",Assets.skin1);
					if(Settings.gold>1000){
						controller.shadowfiend.setHp(controller.shadowfiend.getHp()+500);
						Settings.gold -= 1000;
						controller.shadowfiend.addItems(new Item(0,"heart",1000,500));
						dialog.add("You just purchased a heart. This grant you 500 more hp");
					}
					else {
						dialog.add("Need 1000 gold");
					}
					TextButton close = new TextButton("OK",Assets.skin1);
					dialog.button(close);
					dialog.show(stage);
	
					itemTable.remove();
					controller.gameState = GameState.Run;

				}
	        });
	        itemTable.add(heart);
	        textButtonStyle = new TextButtonStyle();
	        textButtonStyle.up = Assets.skin.getDrawable("radiance");
	        textButtonStyle.down = Assets.skin.getDrawable("radiance");
	        textButtonStyle.font = Assets.smallfont;
	        radiance = new TextButton("", textButtonStyle);
	        radiance.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					Dialog dialog = new Dialog("Info",Assets.skin1);
					if(Settings.gold>1000){
						controller.shadowfiend.setDamage(controller.shadowfiend.getDamage()+100);
						Settings.gold-= 1000;
						controller.shadowfiend.addItems(new Item(100,"radiance",1000,500));
						dialog.add("You just purchased a radiance. This grant you 100 more damage");
					}
					else {
						dialog.add("Need 1000 gold");
					}
					TextButton close = new TextButton("OK",Assets.skin1);
					dialog.button(close);
					dialog.show(stage);
	
					itemTable.remove();
					controller.gameState = GameState.Run;

				}
	        });
	        itemTable.add(radiance);
			stage.addActor(itemTable);
			
		}
}
