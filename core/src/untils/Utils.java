package untils;

import org.json.JSONException;
import org.json.JSONObject;

import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;

import appwarp.WarpController;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Utils {
	public static void razeRed(SpriteBatch batch,int x,int y, float delta){
		Assets.redRazeEffect.start();
		Assets.redRazeEffect.setPosition(x,y);
		Assets.redRazeEffect.update(delta);
		Assets.redRazeEffect.getEmitters().clear();
		Assets.redRazeEffect.getEmitters().add(Assets.redEmitters.get(0));
		Assets.redRazeEffect.draw(batch, delta);
	}
    public static void razeGreen(SpriteBatch batch,int x,int y, float delta){
    	Assets.greenRazeEffect.start();
    	Assets.greenRazeEffect.setPosition(x,y);
    	Assets.greenRazeEffect.update(delta);
    	Assets.greenRazeEffect.getEmitters().clear();
    	Assets.greenRazeEffect.getEmitters().add(Assets.greenEmitters.get(0));
    	Assets.greenRazeEffect.draw(batch, delta);
	}
    public static void sfEffects(SpriteBatch batch,int x,int y, float delta,double angle){
    	Assets.sfEffect.start();
    	Assets.sfEffect.setPosition(x,y);
    	Assets.sfEffect.update(delta);
    	Assets.sfEffect.getEmitters().clear();
    	Assets.sfEmitters.get(1).getAngle().setHigh((float)angle);
    	Assets.sfEmitters.get(1).getAngle().setLow((float)angle);
    	Assets.sfEffect.getEmitters().add(Assets.sfEmitters.get(0));
    	//sfEffect.getEmitters().add(sfEmitters.get(1));
    	Assets.sfEffect.draw(batch, delta);
	}
    public static void hitEffects(SpriteBatch batch,int x,int y, float delta){
    	Assets.hitEffect.start();
    	Assets.hitEffect.setPosition(x,y);
    	Assets.hitEffect.update(delta);
    	Assets.hitEffect.getEmitters().clear();
    	Assets.hitEffect.getEmitters().add(Assets.hitEmitters.get(0));
    	Assets.hitEffect.draw(batch, delta);
	}
    public static void towerHitEffects(SpriteBatch batch,int x,int y, float delta){
    	Assets.towerHitEffect.start();
    	Assets.towerHitEffect.setPosition(x,y);
    	Assets.towerHitEffect.update(delta);
    	Assets.towerHitEffect.getEmitters().clear();
    	Assets.towerHitEffect.getEmitters().add(Assets.towerEmitters.get(0));
    	Assets.towerHitEffect.draw(batch, delta);
	}
    public static void coinEffects(SpriteBatch batch,int x,int y, float delta){
    	Assets.coinEffect.start();
    	Assets.coinEffect.setPosition(x,y);
    	Assets.coinEffect.update(delta);
    	Assets.coinEffect.getEmitters().clear();
    	Assets.coinEffect.getEmitters().add(Assets.coinEmitters.get(0));
    	Assets.coinEffect.draw(batch, delta);
	}
    public static void sendData(float x,float y,float raze,int damage,int hp,int AIHp){
		try {
			JSONObject data = new JSONObject();
			data.put("x", x);
			data.put("y", y);
			data.put("raze", raze);
			data.put("name", Settings.name);
			data.put("damage", damage);
			data.put("hp",hp);
			data.put("AIhp",AIHp);
			WarpController.getInstance().sendGameUpdate(data.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
