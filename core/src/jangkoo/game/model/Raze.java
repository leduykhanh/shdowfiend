package jangkoo.game.model;

import com.badlogic.gdx.math.Vector2;


public class Raze  {
	public static final float RAZE_LIVE_TIME = 0.1f;
	public float aliveTime = 0;
	public float distance = 100f;
	public Vector2 v2Position;
	public Raze () {
			}
	public Raze (float distance) {
		v2Position = new Vector2();
		this.distance =distance;
	}
	public void update (float delta) {
		aliveTime += delta;
	}

}
