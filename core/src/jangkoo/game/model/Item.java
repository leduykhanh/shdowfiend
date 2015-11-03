package jangkoo.game.model;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Item extends Actor{
	public int damage;
	public String nameItem;
	public int price;
	public int hp;
	public Item(int damage,String name,int price,int hp){
		this.damage = damage;
		this.nameItem = name;
		this.price = price;
		this.hp = hp;
	}
}
