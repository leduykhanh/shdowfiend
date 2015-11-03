package jangkoo.game.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Creep extends BaseActor{
	public final static float HERO_SIZE = 1f;
	public float fadeTime = 0.2f;
	public static float HERO_VELOCITY = (float) 0.5f;
	protected int hp = 100;
	public ArrayList<Hit> hits = new ArrayList<Hit>();
	public ArrayList<Hit> removedHits = new ArrayList<Hit>();
	protected int damage = 0;
	public Animation animation;
	public Creep( float x, float y,double angle,Animation animation) {
		super(x,y,angle);
		damage = 10;
		this.animation = animation;
	}
	public int getDamage(){
		return damage;
	}
	public void update(float delta){
		if(x_target==1 && y_target == 1)
			v2Position.x = v2Position.x - HERO_VELOCITY*delta;
		else {
			if(v2Position.dst(x_target, y_target)>10){
				v2Position.add (v2Velocity);
				isMoving = true;
			}
			else isMoving = false;
		}
		if(fadeTime>0) fadeTime-= delta;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public float getAngle() {
		return (float)angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public void setXy_target(float x,float y){
		this.x_target = x;
		this.y_target = y;
		setVelocity(x,y);
		Vector2 tempV = new Vector2((float)(x - this.v2Position.x),(float)(y - this.v2Position.y));
		angle = tempV.angle();
		
	}
	public void setVelocity (float toX, float toY) {

		// The .set() is setting the distance from the starting position to end position
		v2Velocity.set(toX - v2Position.x, toY - v2Position.y);
		v2Velocity.nor(); // Normalizes the value to be used
		v2Velocity.x *= HERO_VELOCITY;  // Set speed of the object
		v2Velocity.y *= HERO_VELOCITY;
		}
}
