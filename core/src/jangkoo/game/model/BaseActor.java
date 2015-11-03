package jangkoo.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {
	protected float x_target = 1;
	protected float y_target = 1;
	public Circle boundingCircle;
	public Vector2 v2Velocity;
	public Vector2 v2Position;
	public Vector2 v2Target;
	protected double angle;
	public boolean isMoving;
	protected float speed = 2f;
	public BaseActor(float x, float y,double angle){
		v2Position = new Vector2(x,y);
		v2Velocity = new Vector2();
		this.angle = angle;
		boundingCircle = new Circle();
	}
	public void update (float delta){
		boundingCircle.set(v2Position, 30);
		if(v2Position.dst(x_target, y_target)>10){
			v2Position.add (v2Velocity);
			isMoving = true;
		}
		else isMoving = false;
	
	}
}
