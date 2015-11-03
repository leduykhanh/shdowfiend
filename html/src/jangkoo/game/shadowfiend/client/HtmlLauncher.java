package jangkoo.game.shadowfiend.client;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import jangkoo.game.model.Item;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.screen.LeaderBoard;
import jangkoo.game.shadowfiend.ShadowFiendGame;

public class HtmlLauncher extends GwtApplication implements LeaderBoard{

		ShadowFiend shadowfiend;
	@Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener getApplicationListener () {
        	shadowfiend = new ShadowFiend(300,300,0,new ArrayList <Item>());
                return new ShadowFiendGame(this,shadowfiend);
        }

		@Override
		public void submitScore(String user, long score) {
			// TODO Auto-generated method stub
			
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