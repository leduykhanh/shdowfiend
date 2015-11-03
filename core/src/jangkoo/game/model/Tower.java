package jangkoo.game.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Tower {
	public Vector2 v2Position;
	private int damage = 10;
	public int hp = 200;
	public int side = 2;
	Circle boundingCircle;
	public ArrayList<Hit> hits = new ArrayList<Hit>();
	public ArrayList<Hit> removedHits = new ArrayList<Hit>();
	public Tower(float x,float y){
		v2Position = new Vector2();
		v2Position.x = x;
		v2Position.y = y;
	}
	public Tower(float x,float y,int side){
		v2Position = new Vector2();
		v2Position.x = x;
		v2Position.y = y;
		this.side = side;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
