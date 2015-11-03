package jangkoo.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;
import jangkoo.game.screen.LeaderBoard;
import jangkoo.game.screen.SFScreen;
import jangkoo.game.shadowfiend.ShadowFiendGame;

public class GameOver  extends SFScreen{
	Stage stage;
    TextButton restartButton,leaderBoardButton,buyButton,rateButton,achivementButton,donateButton;
    BitmapFont font;
    SpriteBatch spriteBatch;
	/** is done flag **/
	private boolean isDone = false;
	String explanation = "";
	String highestScore = "";
	public static final int HIGHEST_SCORE= 0;
	public static final int HIGHEST_TOTAL= 0;
    public GameOver (final LeaderBoard leaderBoard) {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        explanation = "ShadowFiend Game is a free game . " +
        		"Any sugguestions, send email to me at  " +
        		"leejangkoo@gmail.com";
        spriteBatch = new SpriteBatch();
        restartButton = new TextButton("", Assets.skin1,"restart");
        leaderBoardButton = new TextButton("", Assets.skin1,"leaderboard");
        buyButton = new TextButton("", Assets.skin1,"buy");
        rateButton = new TextButton("", Assets.skin1,"rate");
        achivementButton = new TextButton("", Assets.skin1,"medal2");
        donateButton = new TextButton("", Assets.skin1,"donate");
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        
        //table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);
        table.add(restartButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(achivementButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(donateButton).width(2*Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.row();
        table.add(leaderBoardButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(buyButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(rateButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
 
        stage.addActor(table);
        donateButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.donate();

			}
        });
        rateButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				isDone = true;
				leaderBoard.rate();

			}
        });

        leaderBoardButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.viewScore();
				isDone = true;

			}
        });
        achivementButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.viewAchivement();
				isDone = true;

			}
        });
        buyButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				
				isDone = true;

			}
        });
        table.setFillParent(true);
        
        //table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);
        
        stage.addActor(table);
        restartButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShadowFiendGame.score = 0;
				isDone = true;

			}
        });
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
		//font.setScale(2f);
		spriteBatch.draw(Assets.blackgroundTexture, 0, 0, Settings.CAMERA_WIDTH, Settings.CAMERA_HEIGHT);
		//font.drawMultiLine(spriteBatch, explanation, 100, 100);
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