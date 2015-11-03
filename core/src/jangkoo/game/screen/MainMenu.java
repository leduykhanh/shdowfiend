package jangkoo.game.screen;

import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;
import jangkoo.game.shadowfiend.ShadowFiendGame;
import jangkoo.game.shadowfiend.ShadowFiendGame.GameType;
import appwarp.WarpController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu  extends SFScreen{
	Stage stage;
    TextButton playButton, leaderBoardButton,buyButton,rateButton,adRemoveButton,
    achivementButton,AIbutton,OnlineButton,setingsButton,winLeaderBoard,quitButton;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    SpriteBatch spriteBatch;
    Table table ,bottomRightTable;
    Label gameName;
    /** is done flag **/
	private boolean isDone = false;
	public static final int HIGHEST_SCORE= 0;
	public static final int HIGHEST_TOTAL= 0;
	
    public MainMenu (final LeaderBoard leaderBoard) {
    	stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        gameName = new Label("Shadow Fiend Fight",Assets.skin1);
        gameName.setFontScale(3);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("play");
        spriteBatch = new SpriteBatch();
        playButton = new TextButton("", textButtonStyle);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("leaderboard");
        leaderBoardButton = new TextButton("", textButtonStyle);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("buy");
        buyButton = new TextButton("", textButtonStyle);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = Assets.skin.getDrawable("rate");
        rateButton = new TextButton("", textButtonStyle);
        adRemoveButton = new TextButton("", Assets.skin1,"adremove");  
        achivementButton = new TextButton("", Assets.skin1,"medal2");
        AIbutton = new TextButton("",Assets.skin1,"ai");
        OnlineButton = new TextButton("",Assets.skin1,"online");
        setingsButton = new TextButton("",Assets.skin1,"settings");
        winLeaderBoard = new TextButton("",Assets.skin1,"wins");
        quitButton = new TextButton("",Assets.skin1,"exit");
        quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				Gdx.app.exit();

			}
        });
        bottomRightTable = new Table();
        bottomRightTable.bottom().right();
        bottomRightTable.setFillParent(true);
        
        table = new Table(Assets.skin1);
        table.center();
        table.setFillParent(true);
        
        table.add(gameName).colspan(4);
        table.row();
        table.row();
        table.add(playButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(AIbutton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(OnlineButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(achivementButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.row();
        table.add(leaderBoardButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(buyButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(rateButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(adRemoveButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.row();
        table.add(winLeaderBoard).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        table.add(setingsButton).width(Settings.BUTTON_WIDTH).height(Settings.BUTTON_HEIGHT);
        bottomRightTable.add(quitButton).width(2*Settings.BUTTON_HEIGHT/3).height(2*Settings.BUTTON_HEIGHT/3);
        
        winLeaderBoard.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.viewWinningScore();

			}
        });
        setingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShadowFiendGame.gameType = GameType.SETTING;
				isDone = true;

			}
        });
        playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShadowFiendGame.gameType = GameType.ROSHAN;
				isDone = true;

			}
        });
        AIbutton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShadowFiendGame.gameType = GameType.AI;
				isDone = true;

			}
        });
        OnlineButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShadowFiendGame.gameType = GameType.ONLINE;
				isDone = true;
				WarpController.getInstance().startApp("player"+System.currentTimeMillis());
			}
        });
        rateButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.rate();

			}
        });
        adRemoveButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.adremove();

			}
        });
        leaderBoardButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.viewScore();

			}
        });
        achivementButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.viewAchivement();

			}
        });
        buyButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.adremove();
				

			}
        });
        stage.addActor(table);
        stage.addActor(bottomRightTable);
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
		spriteBatch.draw(Assets.mainMenuBackgroundTexture, 0, 0, Settings.CAMERA_WIDTH, Settings.CAMERA_HEIGHT);
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