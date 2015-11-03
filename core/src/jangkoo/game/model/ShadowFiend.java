package jangkoo.game.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ShadowFiend extends BaseActor {

	protected int hp = 100;
	protected int damage = 0;
	public int baseDamage = 45;
	private ArrayList <Item> items = new ArrayList<Item>();
	public ArrayList<Hit> hits = new ArrayList<Hit>();
	public ArrayList<Hit> removedHits = new ArrayList<Hit>();
	public Creep onhit;
	public ShadowFiend(int x, int y,double angle,ArrayList <Item> items) {
		super(x,y,angle);
		isMoving = false;
		this.items= items;
		speed = 2f;
	}
	public ShadowFiend(float x, float y,double angle) {
		super(x,y,angle);
		isMoving = false;
		speed = 2f;
	}
	public void update (float delta){
		super.update(delta);
	}
	public void setXy(float x,float y){
		v2Position = new Vector2(x,y);
	}
	public void setXy_target(float x,float y){
		this.x_target = x;
		this.y_target = y;
		setVelocity(x,y);
		Vector2 tempV = new Vector2((float)(x - this.v2Position.x),(float)(y - this.v2Position.y));
		angle = tempV.angle();
		
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
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public double getY_target() {
		return y_target;
	}
	public void setY_target(float y_target) {
		this.y_target = y_target;
	}
	public double getX_target() {
		return x_target;
	}
	public void setX_target(float x_target) {
		this.x_target = x_target;
	}
	public void setVelocity (float toX, float toY) {

		// The .set() is setting the distance from the starting position to end position
		v2Velocity.set(toX - v2Position.x, toY - v2Position.y);
		v2Velocity.nor(); // Normalizes the value to be used
		v2Velocity.x *= speed;  // Set speed of the object
		v2Velocity.y *= speed;
		}

	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public ArrayList <Item> getItems() {
		return items;
	}
	public void addItems(Item item) {
		 items.add(item);
	}

}
