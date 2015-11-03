package jangkoo.game.shadowfiend;

import untils.Utils;
import jangkoo.game.model.Hit;
import jangkoo.game.model.Item;
import jangkoo.game.model.Raze;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.model.Tower;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.World;
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
import jangkoo.game.control.ControllerOnline;


public class RendererOnline {
		public static final int BUTTON_HEIGHT = Gdx.graphics.getHeight()/7;
		/** sprite batch to draw text **/
		private SpriteBatch spriteBatch;
		ShapeRenderer sr;
		/** the background texture **/
		public static Texture backgroundTexture;
		TextButtonStyle textButtonStyle;
	    TextButton raze1,raze2,raze3,blink,radiance,heart,ward;
	    Button buyItem;
	    TextureAtlas buttonAtlas,itemAtlas;
	    Stage stage;
		Sprite sprite,spriteAI;
		PointLight sfLight,wardLight;
		RayHandler rayHandler;
		World world;
		ControllerOnline controller;
		String randomQuote[] = {"Kill that mother fucker",
								"Donate if you like this",
								"You can raze the tower",
								"Train harder",
								"You are handsome",
								"Please rate the game"};

		final SelectBox<String> razeColor;
		public Label souls,hp,dmg,gold,rank,opsName;
		public RendererOnline (ShadowFiend shadowfiend) {
			controller = new ControllerOnline(shadowfiend);
			/** BOX2D LIGHT STUFF BEGIN */
			
			/** BOX2D LIGHT STUFF BEGIN */
			RayHandler.setGammaCorrection(true);
			RayHandler.useDiffuseLight(true);
			rayHandler = new RayHandler(controller.world);
			rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 0.5f);
			rayHandler.setBlurNum(3);
			//rayHandler.setShadows(false);
			sfLight = new PointLight(
					rayHandler, 128, null, 600, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
			sfLight.attachToBody(controller.sfBody,1/2f,1/2f);
			sfLight.setColor(1f,1f,1f,1f);
			wardLight = new PointLight(
					rayHandler, 128, null, 800, -1000, -1000);
			wardLight.setColor(1f,1f,1f,1f);
			wardLight.attachToBody(controller.wardBody,1/2f,1/2f);
			/*light.setColor(
					MathUtils.random(),
					MathUtils.random(),
					MathUtils.random(),
					1f);*/
			sfLight.setSoftnessLength(0);

			razeColor = new SelectBox<String>(Assets.skin1);
			razeColor.setItems("Raze Color","red","green");
			razeColor.setSelected("Raze Color");
			razeColor.setSize(50,50);
			buyItem = new Button(Assets.skin1);
			buyItem.add("BUY ITEM");
			souls = new Label("SOULS ",Assets.skin1);
			rank = new Label("RANK ",Assets.skin1);
			hp = new Label("HP ",Assets.skin1);
			dmg = new Label("DAMAGE ",Assets.skin1);
			gold = new Label("GOLD ",Assets.skin1);
			opsName = new Label("Player ",Assets.skin1);
			
			buttonAtlas = new TextureAtlas(Gdx.files.internal("skill_buttons/skill.pack"));
			itemAtlas =  new TextureAtlas(Gdx.files.internal("item/item.pack"));
			try {
				stage = new Stage();
				Gdx.input.setInputProcessor(stage);
				//modelBatch = new ModelBatch();
				spriteBatch = new SpriteBatch();
				sr = new ShapeRenderer();
				backgroundTexture = new Texture(Gdx.files.internal("data/bg.png"), Format.RGB565, true);
				backgroundTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
				Table infoTable = new Table();
				infoTable.top().left();
				infoTable.setFillParent(true);
				infoTable.add(rank);
				infoTable.row();
				infoTable.add(souls);
				infoTable.row();
				infoTable.add(hp);
				infoTable.row();
				infoTable.add(dmg);
				infoTable.row();
				infoTable.add(gold);
				Table rightMidle = new Table();
				rightMidle.setFillParent(true);
				rightMidle.right();
				Table leftTable = new Table();
				leftTable.setFillParent(true);
				leftTable.left();
				Table leftBottomTable = new Table();
				leftBottomTable.setFillParent(true);
				leftBottomTable.left();
				leftBottomTable.bottom();
				leftBottomTable.padLeft(123);
				Table rightTopTable = new Table();
				rightTopTable.setFillParent(true);
				rightTopTable.right();
				rightTopTable.top();
				rightTopTable.padRight(123);
				rightTopTable.add(opsName);
				leftBottomTable.add(razeColor).height(BUTTON_HEIGHT/2).padBottom(10);
				leftBottomTable.add(buyItem).height(BUTTON_HEIGHT/2).padBottom(10);
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
						Utils.sendData(0, 0, 60f/Settings.CAMERA_WIDTH,controller.shadowfiend.getDamage(),
								controller.shadowfiend.getHp(),controller.shadowfiendAI.getHp());

					}
		        });
		        raze2.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						if(controller.raze2CD >0) return;
						addRaze(160);
						controller.raze2CD = 3;
						Utils.sendData(0, 0, 160f/Settings.CAMERA_WIDTH,controller.shadowfiend.getDamage(),
								controller.shadowfiend.getHp(),controller.shadowfiendAI.getHp());

					}
		        });
		        raze3.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						if(controller.raze3CD >0) return;
						addRaze(240);
						controller.raze3CD = 3;
						Utils.sendData(0, 0, 240f/Settings.CAMERA_WIDTH,controller.shadowfiend.getDamage(),
								controller.shadowfiend.getHp(),controller.shadowfiendAI.getHp());

					}
		        });
		        raze3.setWidth(50);
		        textButtonStyle = new TextButtonStyle();
		        textButtonStyle.up = Assets.skin.getDrawable("blink");
		        textButtonStyle.down = Assets.skin.getDrawable("blink_pressed");
		        textButtonStyle.font = Assets.bigTitleFont;
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

		        leftTable.add(raze1).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
		        leftTable.row();
		        leftTable.add(raze2).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
		        leftTable.row();
		        leftTable.add(raze3).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
		        leftTable.row();
		        leftTable.add(blink).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
		        stage.addActor(infoTable);
		        stage.addActor(leftTable);
		        stage.addActor(rightMidle);
		        stage.addActor(leftBottomTable);
		        stage.addActor(rightTopTable);
		        spriteBatch = new SpriteBatch();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		public void render ( ControllerOnline controller,float delta) {
			// We explicitly require GL10, otherwise we could've used the GLCommon
			// interface via Gdx.gl
			GL20 gl = Gdx.gl;
			gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			this.controller = controller;
			
			renderBackground();
			sprite = new Sprite(Assets.movingSfAn.getKeyFrame(controller.sfStateTime));
			sprite.setPosition((float)controller.shadowfiend.v2Position.x -75 , (float)controller.shadowfiend.v2Position.y -75);
			sprite.setRotation((float) (controller.shadowfiend.getAngle()));
			spriteAI = new Sprite(Assets.movingSfAn.getKeyFrame(controller.sfStateTime));
			spriteAI.setPosition((float)controller.shadowfiendAI.v2Position.x-75 , (float)controller.shadowfiendAI.v2Position.y -75);
			spriteAI.setRotation((float) (controller.shadowfiendAI.getAngle()));
			sr.setProjectionMatrix(controller.camera.combined);
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(0,0,1,0));
			sr.rect((float)controller.shadowfiendAI.v2Position.x-75,(float)controller.shadowfiendAI.v2Position.y +75, 150*controller.shadowfiendAI.getHp()/200, 10);
			sr.end();
			spriteBatch.begin();
			Utils.sfEffects(spriteBatch,(int)controller.shadowfiend.v2Position.x + 10, 
					(int)controller.shadowfiend.v2Position.y + 10,delta, controller.shadowfiend.getAngle());


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
			for(Raze raze : this.controller.razesAI){
				if(raze.aliveTime <Raze.RAZE_LIVE_TIME)
				{
					Utils.razeGreen(spriteBatch,(int)raze.v2Position.x,(int)raze.v2Position.y,delta);
				}
				else {
					//effect.dispose();
					this.controller.removedRazesAI.add(raze);
	
				}
			}
			for(Tower tower:controller.towers)
				for(Hit hit:tower.hits){
					Utils.towerHitEffects(spriteBatch,(int)hit.v2Position.x,(int)hit.v2Position.y,delta);
				}

			for(Raze raze : this.controller.removedRazes)
				this.controller.razes.remove(raze);
			for(Raze raze : this.controller.removedRazesAI)
				this.controller.razesAI.remove(raze);
			if(controller.towers.size() > 0)
				for(Tower tower:controller.towers)
				{
					spriteBatch.draw(Assets.towerAn.getKeyFrame(controller.sfStateTime),tower.v2Position.x,tower.v2Position.y, 100, 200);
					Assets.smallfont.draw(spriteBatch,tower.hp+"",(float)tower.v2Position.x + 20,(float)tower.v2Position.y + 210);
				}
			
			sprite.draw(spriteBatch);
			spriteAI.draw(spriteBatch);
			spriteBatch.end();
			spriteBatch.setProjectionMatrix(controller.uiCamera.combined);
			controller.uiCamera.update();
			spriteBatch.begin();
			for(int k=0;k<controller.shadowfiend.getItems().size();k++){
				Texture item = new Texture("item/"+controller.shadowfiend.getItems().get(k).nameItem + ".png");
				spriteBatch.draw(item,Gdx.graphics.getWidth()- 100f*(k+1) -5,0);
			}
			if(controller.wardBody !=null){
				spriteBatch.draw(new Texture("data/ward_down.png"),controller.wardBodyDef.position.x,controller.wardBodyDef.position.y);
			}


			if(controller.gameState == GameState.Pause)
				spriteBatch.draw(Assets.levelupTexture,Gdx.graphics.getWidth()/2 - 120,Gdx.graphics.getHeight()/2);
			if(controller.click.aliveTime <Raze.RAZE_LIVE_TIME)
				spriteBatch.draw(Assets.clickTt,controller.click.v2Position.x,controller.click.v2Position.y,70,70);
			souls.setText("SOULS  "+ ShadowFiendGame.score);
			hp.setText("HP  "+controller.shadowfiend.getHp());
			gold.setText("GOLD  "+Settings.gold);
			dmg.setText("DAMAGE "+controller.shadowfiend.baseDamage + " + " + controller.shadowfiend.getDamage());
			rank.setText("RANK "+Settings.rank);

			spriteBatch.end();
			drawLight();
			
			spriteBatch.begin();
			if(controller.gameState == GameState.Lost)
				spriteBatch.draw(new Texture("data/game_over.png"),Settings.CAMERA_WIDTH/2 - 300,Settings.CAMERA_HEIGHT/2 - 100);
			if(controller.gameState == GameState.Won)
				spriteBatch.draw(new Texture("data/victory.png"),Settings.CAMERA_WIDTH/2 - 300,Settings.CAMERA_HEIGHT/2 - 100);
			Assets.bigTitleFont.draw(spriteBatch,randomQuote[controller.shadowfiend.getDamage()%randomQuote.length],
					100*Settings.SCALE_WIDTH + 3*Settings.BUTTON_HEIGHT + 20,30);
			spriteBatch.draw(Assets.avatarAn.getKeyFrame(controller.sfStateTime),10,10);
			spriteBatch.end();
			raze1.setText((controller.raze1CD > 0)?(int)controller.raze1CD+"":"");
			raze2.setText((controller.raze2CD > 0)?(int)controller.raze2CD+"":"");
			raze3.setText((controller.raze3CD > 0)?(int)controller.raze3CD+"":"");
			blink.setText((controller.blinkCD > 0)?(int)controller.blinkCD+"":"");
			stage.act(delta);
			stage.draw();
		}

		private void renderBackground () {
			spriteBatch.setProjectionMatrix(controller.camera.combined);
			controller.camera.update();
			spriteBatch.begin();
			spriteBatch.draw(backgroundTexture, 0, 2*BUTTON_HEIGHT/3, Settings.CAMERA_WIDTH, Settings.CAMERA_HEIGHT - 2*BUTTON_HEIGHT/3, 0, 0, backgroundTexture.getWidth(), backgroundTexture.getHeight(), false, false);
			spriteBatch.end();
			sr.setProjectionMatrix(controller.uiCamera.combined);
			sr.begin(ShapeType.Line);
			sr.setColor(new Color(0,0,1,0));
			sr.rect(BUTTON_HEIGHT, 2*BUTTON_HEIGHT/3, Gdx.graphics.getWidth() - BUTTON_HEIGHT -1, Gdx.graphics.getHeight() - 2*BUTTON_HEIGHT/3 -1);
			sr.end();
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(0,0,0,0));
			sr.rect(0, 0, BUTTON_HEIGHT, Gdx.graphics.getHeight());
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
		private void drawLight(){
			rayHandler.setCombinedMatrix(controller.camera.combined);
			sfLight.attachToBody(controller.sfBody,1/2f,1/2f);
			if(controller.wardBody!=null)
				wardLight.attachToBody(controller.wardBody,1/2f,1/2f);
			rayHandler.setAmbientLight(0.5f);
			rayHandler.updateAndRender();
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
	        textButtonStyle.font = Assets.bigTitleFont;
	        heart = new TextButton("", textButtonStyle);
	        heart.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					Dialog dialog = new Dialog("Info", Assets.skin1);
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
	        textButtonStyle.font = Assets.bigTitleFont;
	        radiance = new TextButton("", textButtonStyle);
	        radiance.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					Dialog dialog = new Dialog("Info", Assets.skin1);
					if(Settings.gold>1000){
						controller.shadowfiend.setDamage(controller.shadowfiend.getDamage()+100);
						Settings.gold -= 1000;
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
	        textButtonStyle = new TextButtonStyle();
	        textButtonStyle.up = Assets.skin.getDrawable("ward");
	        textButtonStyle.down = Assets.skin.getDrawable("ward");
	        textButtonStyle.font = Assets.bigTitleFont;
	        ward = new TextButton("", textButtonStyle);
	        ward.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					Dialog dialog = new Dialog("Info", Assets.skin1);
					if(Settings.gold>999){
						Settings.gold -= 1000;
						dialog.add("You just purchased a ward, now place it on the map");
						controller.onWard = true;
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
	        itemTable.add(ward);
			stage.addActor(itemTable);
			
		}
}
