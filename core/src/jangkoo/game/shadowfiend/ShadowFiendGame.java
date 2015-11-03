package jangkoo.game.shadowfiend;
import jangkoo.game.assets.Assets;
import jangkoo.game.assets.Settings;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.screen.AI;
import jangkoo.game.screen.HeroSelection;
import jangkoo.game.screen.Online;
import jangkoo.game.screen.SFScreen;
import jangkoo.game.screen.GameLoop;
import jangkoo.game.screen.GameOver;
import jangkoo.game.screen.LeaderBoard;
import jangkoo.game.screen.MainMenu;
import jangkoo.game.screen.SettingScreen;
import jangkoo.game.screen.StartOnline;

import appwarp.WarpController;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ShadowFiendGame extends Game {
	public enum GameType{AI,ROSHAN,ONLINE,SETTING};
	public enum ScreenType{GAMEOVER,GAMELOOP,ONLINE,SETTING};
	public static GameType gameType = GameType.ROSHAN;
	public static ScreenType nextScreen = ScreenType.GAMELOOP;
	public static boolean gameOver = false;
	public static long score = 0;
	public static long side = 1;
	private LeaderBoard leaderBoard;
	private ShadowFiend shadowfiend;
	
	public ShadowFiendGame(LeaderBoard leaderBoard,ShadowFiend shadowfiend){
		this.leaderBoard = leaderBoard;
		this.shadowfiend = shadowfiend;
	}
	@Override
	public void create () {
		Assets.load();
		Settings.load();
		setScreen(new MainMenu(leaderBoard));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		SFScreen currentScreen = getScreen();

		// update the screen
		currentScreen.render(Gdx.graphics.getDeltaTime());
		if (currentScreen.isDone()) {
			// dispose the resources of the current screen
			currentScreen.dispose();
			if(currentScreen instanceof MainMenu){
				if(gameType == GameType.ROSHAN)
					setScreen(new GameLoop(shadowfiend));
				else if(gameType == GameType.AI)
					setScreen(new HeroSelection(leaderBoard));
				else if(gameType == GameType.ONLINE)
					setScreen(new StartOnline(leaderBoard));
				else if(gameType == GameType.SETTING)
					setScreen(new SettingScreen(leaderBoard));
						}
			else if(currentScreen instanceof StartOnline){
				if(side==1)shadowfiend.setXy(1*Settings.CAMERA_WIDTH/7,Settings.CAMERA_HEIGHT/2);
				else {
					shadowfiend.setXy(6*Settings.CAMERA_WIDTH/7,Settings.CAMERA_HEIGHT/2);
					shadowfiend.setAngle(180);
				}
				if(nextScreen == ScreenType.GAMEOVER)
					{
					setScreen(new GameOver(leaderBoard));
					nextScreen = ScreenType.ONLINE;
					}
				else setScreen(new Online(shadowfiend));
				
			}
			else if(currentScreen instanceof HeroSelection)
				setScreen(new AI(shadowfiend));
			else if((currentScreen instanceof GameLoop)||(currentScreen instanceof AI)
					||(currentScreen instanceof Online)){
				Settings.save();
				setScreen(new GameOver(leaderBoard));
				shadowfiend.setHp(100);
				gameType = GameType.ROSHAN;
				Settings.startLevel = Settings.level;
				if(Settings.level==2) leaderBoard.unlockAchivement("CgkIkuCni_kIEAIQAg");
				else if(Settings.level==4) leaderBoard.unlockAchivement("CgkIkuCni_kIEAIQAw");
				else if(Settings.level==6) leaderBoard.unlockAchivement("CgkIkuCni_kIEAIQBA");
				else if(Settings.level==8) leaderBoard.unlockAchivement("CgkIkuCni_kIEAIQBQ");
				else if(Settings.level==10) leaderBoard.unlockAchivement("CgkIkuCni_kIEAIQBg");
				shadowfiend.setDamage(0);
				leaderBoard.writeToMemory();
				leaderBoard.submitScore("User", Settings.rank);
				leaderBoard.subWinningScore("User", Settings.wins);
			}
			else if(currentScreen instanceof GameOver){
				setScreen(new MainMenu(leaderBoard));
				WarpController.getInstance().stopApp();
				shadowfiend.setXy(200, 200);
				leaderBoard.showads();
			}
			else if(currentScreen instanceof SettingScreen){
				setScreen(new MainMenu(leaderBoard));
			}
			}
		
	}
	public SFScreen getScreen(){
		return (SFScreen)super.getScreen();
	}
	
}
