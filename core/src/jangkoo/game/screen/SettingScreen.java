
package jangkoo.game.screen;

import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingScreen extends SFScreen{
	Stage stage;
    TextButton OKbutton,SoundButton;
    BitmapFont font;
    TextureAtlas buttonAtlas;
    SpriteBatch spriteBatch;
    Label gameName;
    TextField playerName;
    /** is done flag **/
	private boolean isDone = false;
	public static final int HIGHEST_SCORE= 0;
	public static final int HIGHEST_TOTAL= 0;
	public static final int BUTTON_HEIGHT = Gdx.graphics.getHeight()/7;
    public SettingScreen (final LeaderBoard leaderBoard) {
    	stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        gameName = new Label("Settings",Assets.skin1);
        gameName.setFontScale(3);

        buttonAtlas = new TextureAtlas(Gdx.files.internal("heroes/hero.pack"));
        Assets.skin.addRegions(buttonAtlas);
        playerName = new TextField(Settings.name,Assets.skin1);
        SoundButton = new TextButton("ON",Assets.skin1);
        SoundButton.setText((Settings.soundOn)?"ON":"OFF");
        OKbutton = new TextButton("",Assets.skin1,"ok");
        spriteBatch = new SpriteBatch();
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        
        table.add(gameName).colspan(4);
        table.row();
        table.add(new Label("Name ",Assets.skin1)).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT/2);;
        table.add(playerName).width(BUTTON_HEIGHT * 2).height(BUTTON_HEIGHT/2);
        table.row();
        table.add(new Label("Sound ",Assets.skin1));
        table.add(SoundButton).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT/2);
        table.row();
        table.add(OKbutton).width(BUTTON_HEIGHT).height(BUTTON_HEIGHT);
        SoundButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				Settings.soundOn =!Settings.soundOn;
				SoundButton.setText((Settings.soundOn)?"ON":"OFF");
			}
			});
        OKbutton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				Settings.name = playerName.getText();
				Settings.save();
				isDone = true;

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
		spriteBatch.draw(Assets.blackgroundTexture, 0, 0, Settings.CAMERA_WIDTH, Settings.CAMERA_HEIGHT);
		//font.setScale(1f);
		font.draw(spriteBatch, "@Copyright Jangkoo",Settings.CAMERA_WIDTH/2 - 200, 50);
		spriteBatch.end();
		stage.draw();
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return isDone;
	}

}
