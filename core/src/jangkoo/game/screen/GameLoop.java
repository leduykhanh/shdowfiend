package jangkoo.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import jangkoo.game.assets.Settings;
import jangkoo.game.control.Controller;
import jangkoo.game.model.Creep;
import jangkoo.game.model.Hit;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.shadowfiend.Renderer;


public class GameLoop extends SFScreen{
	private final Renderer renderer;
	private final Controller controller;
	boolean hited = false;
	public GameLoop(ShadowFiend shadowfiend){
		renderer = new Renderer(shadowfiend);
		controller = new Controller(shadowfiend);
	}
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) 
		{   

			int x = (int) (Gdx.input.getX()/Settings.SCALE_WIDTH);
			int y = (int) ((Settings.CAMERA_HEIGHT - Gdx.input.getY())/Settings.SCALE_HEIGHT);
//			int y =  Gdx.input.getY();
			int trueX = (int) ((x + controller.camera.position.x -Settings.CAMERA_WIDTH/2f)/Settings.SCALE_WIDTH);

			hited = false;
			if(x>Settings.BUTTON_HEIGHT && x < Settings.CAMERA_WIDTH -4*Settings.BUTTON_HEIGHT/5 
					&& y > 2*Settings.BUTTON_HEIGHT/3){
				if(controller.onBlink){
					controller.onBlink = false;
					controller.shadowfiend.setXy(trueX, y);
					Settings.playBlink();
				}
				else{
					for(Creep hero:controller.creeps)
						if(Math.sqrt((trueX-hero.v2Position.x)*(trueX-hero.v2Position.x) + (y-hero.v2Position.y)*(y-hero.v2Position.y))<50)
						{
							Hit hit = new Hit(controller.shadowfiend.v2Position.x,controller.shadowfiend.v2Position.y,hero.v2Position.x,hero.v2Position.y);
							controller.shadowfiend.hits.add(hit);
							controller.shadowfiend.onhit = hero;
							hited = true;
							break;
							}
						
				}
				//if(!hited)
					controller.shadowfiend.setXy_target(trueX,y);
				
				controller.click.aliveTime = 0;
				controller.click.v2Position.x = x;
				controller.click.v2Position.y = y;
				
			}
			
		}
		controller.update(delta);
		
	}

	@Override
	public void draw(float delta) {
		// TODO Auto-generated method stub
		renderer.render( controller,delta);		
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return controller.gameOver();
	}
	@Override
	public void dispose () {
		renderer.dispose();
		}
	public void resize (int width, int height) {
		renderer.stage.getViewport().update(width, height, false);
	}
}
