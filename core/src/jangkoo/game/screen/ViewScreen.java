package jangkoo.game.screen;

import jangkoo.game.shadowfiend.ShadowFiendGame;

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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class ViewScreen extends SFScreen
{
    Stage stage;
    TextButton exitButton;
    TextButtonStyle textButtonStyle;
    Texture backgroundTexture;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    SpriteBatch spriteBatch;
    String links[] ={"http://www.youtube.com/watch?v=mLbsmftuFYc"}; 
	/** is done flag **/
	private boolean isDone = false;
    public ViewScreen () {
        stage = new Stage();
        
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        font.setColor(100, 100, 0, 1);
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("blank_buttons/buttons.pack"));
		backgroundTexture = new Texture(Gdx.files.internal("data/6302-dota2_ls_mirana.png"), Format.RGB565, true);
		backgroundTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("green");
        textButtonStyle.font = font;
        exitButton = new TextButton("Exit", textButtonStyle);
        exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				isDone = true;

			}
        });
        Table table = new Table();
        table.setFillParent(true);
        table.bottom();
        table.add(exitButton);
        stage.addActor(table);
    	spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(100, 100, 0, 1);
		backgroundTexture = new Texture(Gdx.files.internal("data/Dota2_Dashboard_BG.jpg"), Format.RGB565, true);
		backgroundTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
        
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
		spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1280, 720, false, false);
		for(int i = 0;i<links.length;i++)
			font.draw(spriteBatch, links[i], 270, 350);
		font.draw(spriteBatch, "@Copyright Jangkoo", 250, 20);
		spriteBatch.end();
		stage.draw();

	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return isDone;
	}

}
