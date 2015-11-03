package jangkoo.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Hit {
	public Vector2 v2Velocity;
	public Vector2 v2Position;
	protected float x_target = 1;
	protected float y_target = 1;
	private float speed = 1f;
	public Texture texture;
	public boolean isMoving = true;
	public Hit(float x,float y,float x_target,float y_target){
		v2Position = new Vector2(x,y);
		v2Velocity = new Vector2();
		this.x_target = x_target;
		this.y_target = y_target;
		setVelocity(x_target,y_target);
		}
	public void update (float delta){
		if(v2Position.dst(x_target, y_target)>10)
			v2Position.add (v2Velocity);
		else isMoving = false;
	}
	public void setXy(float x,float y){
		v2Position = new Vector2(x,y);
	}
	public void setXy_target(float x,float y){
		this.x_target = x;
		this.y_target = y;
		setVelocity(x,y);
		}
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public void setVelocity (float toX, float toY) {

		// The .set() is setting the distance from the starting position to end position
		v2Velocity.set(toX - v2Position.x, toY - v2Position.y);
		v2Velocity.nor(); // Normalizes the value to be used
		v2Velocity.x *= speed;  // Set speed of the object
		v2Velocity.y *= speed;
}
}
