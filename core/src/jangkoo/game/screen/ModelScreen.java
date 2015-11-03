package jangkoo.game.screen;

import jangkoo.game.model.ShadowFiend;

import jangkoo.game.shadowfiend.ShadowFiendGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class ModelScreen extends SFScreen
{
    Stage stage;
    TextButton exitButton;
    TextButtonStyle textButtonStyle;
    Texture backgroundTexture;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    SpriteBatch spriteBatch;
    public ShadowFiend shadowfiend;
    public Model potmModel;
    private PerspectiveCamera camera;
    Environment lights;
    ModelBatch modelBatch;
    private Vector3 tmpV0 = new Vector3(0,2,0);
	/** is done flag **/
	private boolean isDone = false;
	int xstart = 0;
	int ystart = 0;
    public ModelScreen () {
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
    	ObjLoader objLoader = new ObjLoader();
    	potmModel = objLoader.loadModel(Gdx.files.internal("data/small_mirana.obj"));
		final Texture potmTexture = new Texture(Gdx.files.internal("data/potm.jpg"), Format.RGB565, true);
		potmTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		potmModel.materials.get(0).set(TextureAttribute.createDiffuse(potmTexture));
		lights = new Environment();
		lights.add(new DirectionalLight().set(Color.WHITE, new Vector3(-1, -0.5f, 0).nor()));
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		modelBatch = new ModelBatch();
		spriteBatch = new SpriteBatch();
		camera.position.set(0, 5, 10);
		//camera.direction.set(tmpV.x, 0, -4).sub(camera.position).nor();
		camera.lookAt(0, 0, 0);
		//camera.near = 1f;
		//camera.far = 300f;
		camera.update();
    	
    }

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) 
		{   
			xstart = Gdx.input.getX() - Gdx.graphics.getWidth()/2;
			ystart = Gdx.graphics.getHeight() - Gdx.input.getY();
			shadowfiend.setXy(xstart,ystart);	
			shadowfiend.update(delta);
		}
		
	}

	@Override
	public void draw(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL20.GL_DEPTH_TEST);
		gl.glEnable(GL20.GL_CULL_FACE);
		modelBatch.begin(camera);
		//modelBatch.render(shadowfiend,lights);
		modelBatch.end();
		stage.draw();

	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return isDone;
	}

}
