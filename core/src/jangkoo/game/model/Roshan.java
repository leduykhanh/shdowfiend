package jangkoo.game.model;

import com.badlogic.gdx.graphics.g2d.Animation;

public class Roshan extends Creep{
	
	public Roshan(int x, int y, double angle,Animation animation) {
		super(x, y, angle,animation);
		damage = 30;
		hp = 500;
		// TODO Auto-generated constructor stub
	}
	public void update(float delta){
		if(x_target==1 && y_target == 1 && v2Position.x >100 )
			v2Position.x = v2Position.x - HERO_VELOCITY*delta;
		else {
			super.update(delta);
		}
	}
}
