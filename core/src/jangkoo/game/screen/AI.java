package jangkoo.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import jangkoo.game.assets.Settings;
import jangkoo.game.control.Controller;
import jangkoo.game.control.ControllerAI;
import jangkoo.game.model.Creep;
import jangkoo.game.model.Hit;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.shadowfiend.Renderer;
import jangkoo.game.shadowfiend.RendererAI;


public class AI extends SFScreen{
	private final RendererAI renderer;
	private final ControllerAI controller;
	boolean hited = false;
	public AI(ShadowFiend shadowfiend){
		renderer = new RendererAI(shadowfiend);
		controller = new ControllerAI(shadowfiend);
	}
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) 
		{   
			int x = Gdx.input.getX();
			int y = Gdx.graphics.getHeight() - Gdx.input.getY();
			int trueX = (int) (x + controller.camera.position.x -Settings.CAMERA_WIDTH/2f);
			hited = false;
			if(x>Settings.BUTTON_HEIGHT && x < Settings.CAMERA_WIDTH -4*Settings.BUTTON_HEIGHT/5 
					&& y > 2*Settings.BUTTON_HEIGHT/3){
				if(controller.onBlink){
					controller.onBlink = false;
					controller.shadowfiend.setXy(trueX, y);
					Settings.playBlink();
				}
				else if(controller.onWard){
					controller.onWard = false;
					controller.wardBodyDef.position.set(trueX, y);
					controller.wardBody = controller.world.createBody(controller.wardBodyDef);
				}
				else{
					for(Creep hero:controller.heroes)
						if(Math.sqrt((trueX-hero.v2Position.x)*(trueX-hero.v2Position.x) + (y-hero.v2Position.y)*(y-hero.v2Position.y))<50)
						{
							Hit hit = new Hit(controller.shadowfiend.v2Position.x,controller.shadowfiend.v2Position.y,hero.v2Position.x,hero.v2Position.y);
							//controller.towers.add(hit);
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
	}
}
