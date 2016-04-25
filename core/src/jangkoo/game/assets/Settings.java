package jangkoo.game.assets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	public static boolean soundOn = true;
	public static String name = "Player";
	public static int wins = 0;
	public static int gold = 0;
	public static int rank = 3000;
	public static int level = 1;
	public static int startLevel = 1;
	public static int DEVICE_WIDTH = Gdx.graphics.getWidth();
	public static int DEVICE_HEIGHT = Gdx.graphics.getHeight();	
	public static int CAMERA_WIDTH = 890;
	public static int CAMERA_HEIGHT = 500;
	public static int BUTTON_HEIGHT = 75;
	public static int BUTTON_WIDTH = 75;
	public static int MAX_MAP_WIDTH = 4*CAMERA_WIDTH;
	public static float UNIT_DISTANCE = (float) (Math.sqrt(CAMERA_WIDTH*CAMERA_WIDTH + CAMERA_HEIGHT*CAMERA_HEIGHT)/100);
	public static float SCALE_WIDTH = (float)CAMERA_WIDTH/DEVICE_WIDTH;
	public static float SCALE_HEIGHT = (float)CAMERA_HEIGHT/DEVICE_HEIGHT;
	public final static String file = ".shadowfiend";
	public static void playCoin(){
		if (soundOn) Assets.coin.play();
	}
	public static void playAttack(){
		if (soundOn) Assets.attack.play();
	}
	public static void playBlink(){
		if (soundOn) Assets.music.play();
	}
	public static void load1 () {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(Gdx.files.external(file).read()));
			soundOn = Boolean.parseBoolean(in.readLine());
			name = in.readLine();
			wins = Integer.parseInt(in.readLine());
		} catch (Throwable e) {
			// :( It's ok we have defaults
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
			}
		}
	}
	public static void load (){
		Preferences prefs = Gdx.app.getPreferences("Shadow Fiend");
		soundOn = prefs.getBoolean("soundOn");
		name = prefs.getString("name");
		wins = prefs.getInteger("wins");
		gold = prefs.getInteger("gold");
		rank = prefs.getInteger("rank");
		level = prefs.getInteger("level");
		startLevel = prefs.getInteger("startLevel");
	}
	public static void save () {
		Preferences prefs = Gdx.app.getPreferences("Shadow Fiend");
		prefs.putBoolean("soundOn", soundOn);
		prefs.putString("name", name);
		prefs.putInteger("wins", wins);
		prefs.putInteger("gold", gold);
		prefs.putInteger("rank", rank);
		prefs.putInteger("level", level);
		prefs.putInteger("startLevel", startLevel);
		prefs.flush();
	}
	public static void save1 () {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external(file).write(false)));
			out.write(Boolean.toString(soundOn));
			out.write("\n");
			out.write(name);
			out.write("\n");
			out.write(wins);

		} catch (Throwable e) {
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
			}
		}
	}
}
