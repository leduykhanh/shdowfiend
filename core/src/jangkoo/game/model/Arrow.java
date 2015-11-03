package jangkoo.game.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class Arrow  extends ModelInstance{
	public static float ARROW_VELOCITY = (float) 5f;
	private int x = 1;
	private int y = 1;
	public boolean isFlying = false;
	public float distance = 0f;
	public Arrow(Model model,Vector3 position) {
		super(model,position);
		// TODO Auto-generated constructor stub
	}
	public void update(float delta){
		transform.trn((float)(ARROW_VELOCITY * delta*x/Math.sqrt(x*x+y*y)),(float)(ARROW_VELOCITY * delta*Math.abs(y)/Math.sqrt(x*x+y*y)),0);
		distance += ARROW_VELOCITY * delta;
	}
	public void setXy(int x,int y){
		this.x = x;
		this.y = y;
	}
}
