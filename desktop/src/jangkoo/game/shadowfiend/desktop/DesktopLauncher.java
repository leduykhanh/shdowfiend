package jangkoo.game.shadowfiend.desktop;

import java.util.ArrayList;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import jangkoo.game.model.Item;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.screen.LeaderBoard;
import jangkoo.game.shadowfiend.ShadowFiendGame;

public class DesktopLauncher implements LeaderBoard {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 890;
		config.height = 500;
		ShadowFiend shadowfiend = new ShadowFiend(300,300,1,new ArrayList <Item>());
		new LwjglApplication(new ShadowFiendGame(new DesktopLauncher(),shadowfiend), config);
	}

	@Override
	public void submitScore(String user, long score) {
		System.out.println("Submitting Score" + score);
		
	}

	@Override
	public void subWinningScore(String user, long score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toMainMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeToMemory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewScore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewWinningScore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void donate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adremove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockAchivement(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewAchivement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyItem(String itemName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showads() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shareFB() {
		// TODO Auto-generated method stub
		
	}
}