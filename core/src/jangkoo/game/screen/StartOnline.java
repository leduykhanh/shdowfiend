package jangkoo.game.screen;

import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;
import jangkoo.game.shadowfiend.ShadowFiendGame;
import jangkoo.game.shadowfiend.ShadowFiendGame.ScreenType;
import untils.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import appwarp.WarpController;
import appwarp.WarpListener;

public class StartOnline extends SFScreen implements  WarpListener {
	SpriteBatch spriteBatch;
	Stage stage;
	TextButton quitButton,shareFacbookButton;
	private final String tryingToConnect = "Connecting";
	private final String waitForOtherUser = "Waiting for other user";
	private final String errorInConnection = "Error in Connection Go Back";
	Table centerTable,bottomRightTable;
	private Label info,inviteFB;
	private String msg = tryingToConnect;
	private boolean isDone = false;
	public static final int BUTTON_HEIGHT = Settings.CAMERA_HEIGHT/7;
	public StartOnline(final LeaderBoard leaderBoard){
		spriteBatch = new SpriteBatch();
		stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        centerTable = new Table();
        bottomRightTable = new Table();
        centerTable.center();
        bottomRightTable.bottom().right();
        centerTable.setFillParent(true);
        bottomRightTable.setFillParent(true);
        quitButton = new TextButton("",Assets.skin1,"exit");
        shareFacbookButton = new TextButton("",Assets.skin1,"facebook");
		shareFacbookButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				leaderBoard.shareFB();

			}
		});
        quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				isDone = true;
				ShadowFiendGame.nextScreen = ScreenType.GAMEOVER;

			}
        });
		info = new Label("Online mode is under testing and lack of players." +
				"To get practice with this, invite your friend to play with." +
				"Due to a free server, it might be slow and got bug." +
				"Please enjoy it, i am improving this :)."+
				"To play with Admin, whatsapp : +6597798842, I will go online and solo with you."+
				"Email leejangkoo@gmail.com."+
				"Site: www.jangkoo.ml.",Assets.skin1);
		inviteFB = new Label("INVITE ON FACEBOOK",Assets.skin1);
		inviteFB.setFontScale(4);
		info.setFontScale(2);
		info.setWrap(true);
		centerTable.add(info).width(6*BUTTON_HEIGHT).height(4*BUTTON_HEIGHT);
		centerTable.row();
		centerTable.add(inviteFB).width(3*BUTTON_HEIGHT).height(BUTTON_HEIGHT);
		centerTable.add(shareFacbookButton).width(3*BUTTON_HEIGHT).height(BUTTON_HEIGHT);
		bottomRightTable.add(quitButton).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
		WarpController.getInstance().setListener(this);
		stage.addActor(centerTable);
		stage.addActor(bottomRightTable);
	}

	@Override
	public void onWaitingStarted(String message) {
		// TODO Auto-generated method stub
		this.msg = waitForOtherUser;
		}

	@Override
	public void onError(String message) {
		// TODO Auto-generated method stub
		this.msg = errorInConnection;
	}

	@Override
	public void onGameStarted(String message) {
		// TODO Auto-generated method stub
		this.msg = "Started";
		Utils.sendData(0, 0, 0,0,0,0);
		isDone = true;
	}

	@Override
	public void onGameFinished(int code, boolean isRemote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameUpdateReceived(String message) {
		// TODO Auto-generated method stub
		this.msg = message;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(float delta) {
		// TODO Auto-generated method stub
		spriteBatch.begin();
		spriteBatch.draw(Assets.greengroundTexture, 0, 0, Settings.CAMERA_WIDTH, Settings.CAMERA_HEIGHT);
		Assets.bigTitleFont.draw(spriteBatch,msg,100,100);
		spriteBatch.end();
		stage.draw();
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return isDone;
	}

}
