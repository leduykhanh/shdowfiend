package jangkoo.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {
	public Circle boundingCircle;
	public Vector2 v2Velocity;
	public Vector2 v2Position;
	public Vector2 v2Target;
	public Vector2 v2Previous;
	protected double angle;
	public boolean isMoving;
	protected float speed = 2f;
	public BaseActor(float x, float y,double angle){
		v2Position = new Vector2(x,y);
		v2Target = new Vector2(x,y);
		v2Previous = new Vector2(x,y);
		v2Velocity = new Vector2();
		this.angle = angle;
		boundingCircle = new Circle();
	}
	public void update (float delta){
		boundingCircle.set(v2Position, 30);
		if(v2Position.dst(v2Target)>10){
			v2Previous.x = v2Position.x;
			v2Previous.y = v2Position.y;
			v2Position.add (v2Velocity);
			isMoving = true;
		}
		else isMoving = false;
	
	}
}
