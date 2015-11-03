package jangkoo.game.screen;

import org.json.JSONObject;

import untils.Utils;

import appwarp.WarpController;
import appwarp.WarpListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import jangkoo.game.assets.Settings;
import jangkoo.game.control.Controller;
import jangkoo.game.control.ControllerOnline;
import jangkoo.game.model.Creep;
import jangkoo.game.model.Hit;
import jangkoo.game.model.Raze;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.shadowfiend.RendererOnline;


public class Online extends SFScreen implements  WarpListener{
	private final RendererOnline renderer;
	private final ControllerOnline controller;
	boolean hited = false;
	
	public Online(ShadowFiend shadowfiend){
		renderer = new RendererOnline(shadowfiend);
		controller = new ControllerOnline(shadowfiend);
		WarpController.getInstance().setListener(this);
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
							hited = true;
							break;
							}
						
				}
				//if(!hited)
					controller.shadowfiend.setXy_target(trueX,y);
					
				Utils.sendData((float)trueX/Settings.CAMERA_WIDTH, (float)y/Settings.CAMERA_HEIGHT, 
						0,controller.shadowfiend.getDamage(),controller.shadowfiend.getHp(),controller.shadowfiendAI.getHp());
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
	@Override
	public void onWaitingStarted(String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onError(String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGameStarted(String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGameFinished(int code, boolean isRemote) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGameUpdateReceived(String message) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject data = new JSONObject(message);
			float x = (float)data.getDouble("x");
			float y = (float)data.getDouble("y");
			float raze = (float)data.getDouble("raze");
			int damage = data.getInt("damage");
			int hp = data.getInt("hp");
			int AIhp = data.getInt("AIhp");
			String name = data.getString("name");
			renderer.opsName.setText(name); 
			if (raze>0) controller.razesAI.add(new Raze(raze*Settings.CAMERA_WIDTH));
			if (x+y>0) controller.shadowfiendAI.setXy_target(x*Settings.CAMERA_WIDTH, y*Settings.CAMERA_HEIGHT);
			if(damage!=0) controller.shadowfiendAI.setDamage(damage);
			if(hp!=0) {
				controller.shadowfiendAI.setHp(hp);
			}
			if(AIhp!=0) {
				controller.shadowfiend.setHp(AIhp);
			}
			}
		catch(Exception e){}
	
	
	}
}
