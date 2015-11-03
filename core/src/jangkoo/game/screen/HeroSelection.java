package jangkoo.game.screen;

import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class HeroSelection  extends SFScreen{
	Stage stage;
    TextButton sf, sniper,AM,DR,adRemoveButton,achivementButton,AIbutton;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    TextureAtlas buttonAtlas;
    SpriteBatch spriteBatch;
    Label gameName;
    /** is done flag **/
	private boolean isDone = false;
	public static final int HIGHEST_SCORE= 0;
	public static final int HIGHEST_TOTAL= 0;
	public static final int BUTTON_HEIGHT = Settings.CAMERA_HEIGHT/7;
    public HeroSelection (final LeaderBoard leaderBoard) {
    	stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        gameName = new Label("Pick hero for AI",Assets.skin1);
        gameName.setFontScale(3);
        buttonAtlas = new TextureAtlas(Gdx.files.internal("heroes/hero.pack"));
        Assets.skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("shadowfiend");
        spriteBatch = new SpriteBatch();
        sf = new TextButton("", textButtonStyle);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("sniper");
        sniper = new TextButton("", textButtonStyle);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("antimage");
        AM = new TextButton("", textButtonStyle);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("drowranger");
        DR = new TextButton("", textButtonStyle);
       /* textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("adsremove");
        adRemoveButton = new TextButton("", textButtonStyle);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("medal2");
        achivementButton = new TextButton("", textButtonStyle);
        AIbutton = new TextButton("AI",Assets.skin1);*/
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        
        table.add(gameName).colspan(4);
        table.row();
        table.row();
        table.add(sf).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
       // table.add(AIbutton).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
        //table.add(achivementButton).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
        table.add(sniper).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
        table.add(AM).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
        table.add(DR).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
        //table.add(adRemoveButton).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
        

        sf.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				isDone = true;

			}
        });
       /* AIbutton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShadowFiendGame.gameType = GameType.AI;
				isDone = true;

			}
        });*/
        DR.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.showads();

			}
        });
        /*adRemoveButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				

			}
        });*/
        sniper.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.showads();

			}
        });
        /*
        achivementButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				

			}
        });*/
        AM.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
			
				leaderBoard.showads();	

			}
        });
        stage.addActor(table);
    }

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(float delta) {
		// TODO Auto-generated method stub
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		//font.setScale(4f);
		spriteBatch.draw(Assets.mainMenuBackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 480, 300, false, false);
		//font.setScale(1f);
		font.draw(spriteBatch, "@Copyright Jangkoo", Settings.CAMERA_WIDTH/2 - 200, 50);
		spriteBatch.end();
		stage.draw();
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return isDone;
	}

}
