package jangkoo.game.assets;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class Assets {
	public static Texture sfTexture,heroTexture,hitTexture,roshanTexture,levelupTexture,
	blackgroundTexture,greengroundTexture,gifTexture,towerTexture,clickTt,mainMenuBackgroundTexture,
	creepBackgroundTexture;
	public static Animation movingSfAn, staySfAn,towerAn,creepAn,avatarAn,creep1An,creep2An,creep3An,creep4An;
	public static ParticleEffect  redRazeEffect,greenRazeEffect,sfEffect,towerHitEffect,coinEffect,hitEffect;
	public static Array<ParticleEmitter> redEmitters,greenEmitters,sfEmitters,towerEmitters,coinEmitters,hitEmitters;
    public static TextureAtlas buttonAtlas;
	public static ArrayList<Animation> listAn = new ArrayList<Animation>();
	public static Skin skin,skin1;
	public static Music music, attack,coin;
	public static BitmapFont smallfont,bigTitleFont,infoFont;
	public static void load () {
		FileHandle infofontFile = Gdx.files.internal("ui/ITCKRIST.TTF");
	    FreeTypeFontGenerator infogenerator = new FreeTypeFontGenerator(infofontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = 12;
	    infoFont = infogenerator.generateFont(parameter);
	    FileHandle fontFile = Gdx.files.internal("ui/BUXTONSKETCH.TTF");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    parameter.size = 12;
	    smallfont = generator.generateFont(parameter);
	    parameter.size = 24;
	    bigTitleFont = generator.generateFont(parameter);
		creepBackgroundTexture = new Texture(Gdx.files.internal("data/bg.jpg"), Format.RGB565, true);
		creepBackgroundTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		
		blackgroundTexture = new Texture(Gdx.files.internal("data/black.jpg"), Format.RGB565, true);
		blackgroundTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		greengroundTexture= new Texture(Gdx.files.internal("data/green.jpg"), Format.RGB565, true);
		greengroundTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		mainMenuBackgroundTexture = new Texture(Gdx.files.internal("data/background.jpg"), Format.RGB565, true);
		mainMenuBackgroundTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		sfTexture= new Texture("data/sf1.png");
		roshanTexture = new Texture("data/roshan.png");
		hitTexture  = new Texture("data/hit.png");
		levelupTexture =new Texture("data/levelup.png");
		towerTexture = new Texture("data/tower.png");
		clickTt = new Texture("data/redX2.png");
		Texture sfTexture1= new Texture("data/sf1.png");
		Texture sfTexture2= new Texture("data/sf2.png");
		Texture sfTexture3= new Texture("data/sf3.png");
		Texture sfTexture4= new Texture("data/sf4.png");
		Texture sfTexture5= new Texture("data/sf5.png");
		Texture sfTexture10= new Texture("data/sf10.png");
		Texture towerTexture1 = new Texture("data/tower1.png");
		Texture creep1Texture1 = new Texture("data/creep1.png");
		Texture creep1Texture2 = new Texture("data/creep4.png");
		Texture creep2Texture1 = new Texture("data/creep6.png");
		Texture creep2Texture2 = new Texture("data/creep7.png");
		Texture creep3Texture1 = new Texture("data/necro2.png");
		Texture creep3Texture2 = new Texture("data/necro3.png");
		Texture creep4Texture1 = new Texture("data/creep8.png");
		Texture creep4Texture2 = new Texture("data/creep9.png");
		Texture avatarTexture1 = new Texture("avatar/circle.png");
		Texture avatarTexture2 = new Texture("avatar/circle2.png");
		movingSfAn = new Animation(0.5f,new TextureRegion(sfTexture10),
				new TextureRegion(sfTexture1),
				new TextureRegion(sfTexture4),
				new TextureRegion(sfTexture3),
				new TextureRegion(sfTexture10),
				new TextureRegion(sfTexture2),
				new TextureRegion(sfTexture5));
		movingSfAn.setPlayMode(PlayMode.LOOP);
		staySfAn = new Animation(0.5f,new TextureRegion(sfTexture2),
				new TextureRegion(sfTexture3),
				new TextureRegion(sfTexture4),
				new TextureRegion(sfTexture3));
		staySfAn.setPlayMode(PlayMode.LOOP);
		towerAn = new Animation(1f,new TextureRegion(towerTexture),new TextureRegion(towerTexture1),new TextureRegion(towerTexture));
		towerAn.setPlayMode(PlayMode.LOOP);
		creep1An = new Animation(0.3f,new TextureRegion(creep1Texture1),new TextureRegion(creep1Texture2),new TextureRegion(creep1Texture1));
		creep1An.setPlayMode(PlayMode.LOOP);
		creep2An = new Animation(0.4f,new TextureRegion(creep2Texture1),new TextureRegion(creep2Texture2),new TextureRegion(creep2Texture1));
		creep2An.setPlayMode(PlayMode.LOOP);
		creep3An = new Animation(0.5f,new TextureRegion(creep3Texture1),new TextureRegion(creep3Texture2),new TextureRegion(creep3Texture1));
		creep3An.setPlayMode(PlayMode.LOOP);
		creep4An = new Animation(0.45f,new TextureRegion(creep4Texture1),new TextureRegion(creep4Texture2),new TextureRegion(creep4Texture1));
		creep4An.setPlayMode(PlayMode.LOOP);
		avatarAn = new Animation(0.6f,new TextureRegion(avatarTexture1),new TextureRegion(avatarTexture2),new TextureRegion(avatarTexture1));
		avatarAn.setPlayMode(PlayMode.LOOP);
		
		listAn.add(creep1An);listAn.add(creep2An);listAn.add(creep3An);listAn.add(creep4An);
		sfTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		skin1 = new Skin(Gdx.files.internal("ui/uiskin.json"));
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("blank_button/button.pack"));
		skin.addRegions(buttonAtlas);
		redRazeEffect = new ParticleEffect();
		greenRazeEffect = new ParticleEffect();
		sfEffect = new ParticleEffect();
		towerHitEffect = new ParticleEffect();
		coinEffect = new ParticleEffect();
		hitEffect = new ParticleEffect();
		
		redRazeEffect.load(Gdx.files.internal("effects/red.p"), Gdx.files.internal("img"));
		greenRazeEffect.load(Gdx.files.internal("effects/green.p"), Gdx.files.internal("img"));
		sfEffect.load(Gdx.files.internal("effects/sf.p"), Gdx.files.internal("img"));
		towerHitEffect.load(Gdx.files.internal("effects/tower.p"), Gdx.files.internal("data"));
		coinEffect.load(Gdx.files.internal("effects/coin.p"), Gdx.files.internal("img"));
		hitEffect.load(Gdx.files.internal("effects/hit.p"), Gdx.files.internal("img"));
		
		redEmitters = new Array(redRazeEffect.getEmitters());
		redRazeEffect.getEmitters().clear();
		redRazeEffect.getEmitters().add(redEmitters.get(0));
		greenEmitters =new Array(greenRazeEffect.getEmitters());
		greenRazeEffect.getEmitters().clear();
		greenRazeEffect.getEmitters().add(greenEmitters.get(0));
		sfEmitters = new Array(sfEffect.getEmitters());
		sfEffect.getEmitters().clear();
		sfEffect.getEmitters().add(sfEmitters.get(0));
		towerEmitters = new Array(towerHitEffect.getEmitters());
		towerHitEffect.getEmitters().clear();
		towerHitEffect.getEmitters().add(towerEmitters.get(0));
		coinEmitters = new Array(coinEffect.getEmitters());
		coinEffect.getEmitters().clear();
		coinEffect.getEmitters().add(coinEmitters.get(0));	
		hitEmitters = new Array(hitEffect.getEmitters());
		hitEffect.getEmitters().clear();
		hitEffect.getEmitters().add(hitEmitters.get(0));
		music = Gdx.audio.newMusic(Gdx.files.internal("data/Nev_spawn_03.mp3"));
		attack = Gdx.audio.newMusic(Gdx.files.internal("data/Nev_attack_06.mp3"));
		coin = Gdx.audio.newMusic(Gdx.files.internal("data/coins-in-hand-1.mp3"));
		
	}
}
