package mysko.pilzhere.mode7racer.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetsManager extends AssetManager {
	public void loadAssets() {
		loadTextures();
		loadFonts();
		loadSkins();
		loadSounds();
		
		finishLoading();
	}

	private void loadTextures() {
		loadBackgrounds();
		loadTiles();
		loadSprites();
		loadMaps();
	}
	
	private void loadSounds() {
		loadSFX();
		loadMusic();
	}

	//	Tiles
	public final String EDGE = "textures/tiles/edge.png";
	
	public final String ROAD_01 = "textures/tiles/road01.png";
	public final String ROAD_02 = "textures/tiles/road02.png";
	public final String VOID_01 = "textures/tiles/void.png";
	public final String VOID_ALPHA_01 = "textures/tiles/voidAlpha.png";
	
	public final String JUMP_HORIZONTAL_01 = "textures/tiles/jumpHorizontal01.png";
	public final String JUMP_HORIZONTAL_LEFT_01 = "textures/tiles/jumpHorizontalLeft01.png";
	public final String JUMP_HORIZONTAL_RIGHT_01 = "textures/tiles/jumpHorizontalRight01.png";
	
	public final String JUMP_VERTICAL_01 = "textures/tiles/jumpVertical01.png";
	public final String JUMP_VERTICAL_TOP_01 = "textures/tiles/jumpVerticalTop01.png";
	public final String JUMP_VERTICAL_BOTTOM_01 = "textures/tiles/jumpVerticalBottom01.png";
	
	public final String CURB_0101 = "textures/tiles/curb0101.png"; // l
	public final String CURB_1010 = "textures/tiles/curb1010.png"; // r
	public final String CURB_1100 = "textures/tiles/curb1100.png"; // s
	public final String CURB_0011 = "textures/tiles/curb0011.png"; // n
	
	public final String CURB_1110 = "textures/tiles/curb1110.png"; // outer corner nl
	public final String CURB_1101 = "textures/tiles/curb1101.png"; // outer corner nr
	public final String CURB_0111 = "textures/tiles/curb0111.png"; // outer corner sr
	public final String CURB_1011 = "textures/tiles/curb1011.png"; // outer corner sl
	
	public final String CURB_1000 = "textures/tiles/curb1000.png"; // inner corner nl
	public final String CURB_0100 = "textures/tiles/curb0100.png"; // inner corner nr
	public final String CURB_0010 = "textures/tiles/curb0010.png"; // inner corner sl
	public final String CURB_0001 = "textures/tiles/curb0001.png"; // inner corner sr
	
	public final String CURB_0000 = "textures/tiles/curb0000.png"; // none
	public final String CURB_1111 = "textures/tiles/curb1111.png"; // full
	
	public final String GOAL_VERTICAL_01 = "textures/tiles/goalVertical01.png";
	public final String GOAL_HORIZONTAL_01 = "textures/tiles/goalHorizontal01.png";
	
	private void loadTiles() {
		load(EDGE, Texture.class);
		
		load(ROAD_01, Texture.class);
		load(ROAD_02, Texture.class);
		load(VOID_01, Texture.class);
		load(VOID_ALPHA_01, Texture.class);
		
		load(JUMP_HORIZONTAL_01, Texture.class);
		load(JUMP_HORIZONTAL_LEFT_01, Texture.class);
		load(JUMP_HORIZONTAL_RIGHT_01, Texture.class);
		
		load(CURB_0101, Texture.class);
		load(CURB_1010, Texture.class);
		load(CURB_1100, Texture.class);
		load(CURB_0011, Texture.class);
		
		load(CURB_1110, Texture.class);
		load(CURB_1101, Texture.class);
		load(CURB_0111, Texture.class);
		load(CURB_1011, Texture.class);
		
		load(CURB_1000, Texture.class);
		load(CURB_0100, Texture.class);
		load(CURB_0010, Texture.class);
		load(CURB_0001, Texture.class);
		
		load(CURB_0000, Texture.class);
		load(CURB_1111, Texture.class);
		
		load(JUMP_VERTICAL_01, Texture.class);
		load(JUMP_VERTICAL_TOP_01, Texture.class);
		load(JUMP_VERTICAL_BOTTOM_01, Texture.class);
		
		load(GOAL_VERTICAL_01, Texture.class);
		load(GOAL_HORIZONTAL_01, Texture.class);
	}
	
//	Backgrounds
	public final String BACKGROUND_01_BACK = "textures/backgrounds/levelBg01Back.png";
	public final String BACKGROUND_01_FRONT = "textures/backgrounds/levelBg01Front.png";
	public final String BACKGROUND_FOG = "textures/backgrounds/fog01.png";
	
	public final String BACKGROUND_UNDERGROUND_CITY_01 = "textures/backgrounds/mapUnderlayCity01_512.png";
	public final String BACKGROUND_UNDERGROUND_CITY_02 = "textures/backgrounds/mapUnderlayCity01_1024.png";
	
	private void loadBackgrounds() {
		load(BACKGROUND_01_BACK, Texture.class);
		load(BACKGROUND_01_FRONT, Texture.class);
		load(BACKGROUND_FOG, Texture.class);
		
		load(BACKGROUND_UNDERGROUND_CITY_01, Texture.class);
		load(BACKGROUND_UNDERGROUND_CITY_02, Texture.class);
	}
	
//	Sprites
//	Car01
	public final String CAR_01_SIZE_09_BACK_01 = "textures/sprites/car01/car01Size09Back01.png";
	public final String CAR_01_SIZE_08_BACK_01 = "textures/sprites/car01/car01Size08Back01.png";
	public final String CAR_01_SIZE_07_BACK_01 = "textures/sprites/car01/car01Size07Back01.png";
	public final String CAR_01_SIZE_06_BACK_01 = "textures/sprites/car01/car01Size06Back01.png";
	public final String CAR_01_SIZE_05_BACK_01 = "textures/sprites/car01/car01Size05Back01.png";
	public final String CAR_01_SIZE_04_BACK_01 = "textures/sprites/car01/car01Size04Back01.png";
	public final String CAR_01_SIZE_03_BACK_01 = "textures/sprites/car01/car01Size03Back01.png";
	public final String CAR_01_SIZE_02_BACK_01 = "textures/sprites/car01/car01Size02Back01.png";
	public final String CAR_01_SIZE_01_BACK_01 = "textures/sprites/car01/car01Size01Back01.png";
	
	public final String CAR_01_SIZE_09_BACK_TURN_RIGHT_02 = "textures/sprites/car01/car01Size09BackTurnRight02.png";
	
	public final String CAR_01_SIZE_09_BACK_LEFT_01 = "textures/sprites/car01/car01Size09BackLeft01.png";
	public final String CAR_01_SIZE_08_BACK_LEFT_01 = "textures/sprites/car01/car01Size08BackLeft01.png";
	public final String CAR_01_SIZE_07_BACK_LEFT_01 = "textures/sprites/car01/car01Size07BackLeft01.png";
	public final String CAR_01_SIZE_06_BACK_LEFT_01 = "textures/sprites/car01/car01Size06BackLeft01.png";
	public final String CAR_01_SIZE_05_BACK_LEFT_01 = "textures/sprites/car01/car01Size05BackLeft01.png";
	public final String CAR_01_SIZE_04_BACK_LEFT_01 = "textures/sprites/car01/car01Size04BackLeft01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_03_BACK_LEFT_01 = "textures/sprites/car01/car01Size03BackLeft01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_02_BACK_LEFT_01 = "textures/sprites/car01/car01Size02BackLeft01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_01_BACK_LEFT_01 = "textures/sprites/car01/car01Size01BackLeft01.png"; // Not from F-zero.
	
	public final String CAR_01_SIZE_09_LEFT_01 = "textures/sprites/car01/car01Size09Left01.png";
	public final String CAR_01_SIZE_08_LEFT_01 = "textures/sprites/car01/car01Size08Left01.png";
	public final String CAR_01_SIZE_07_LEFT_01 = "textures/sprites/car01/car01Size07Left01.png";
	public final String CAR_01_SIZE_06_LEFT_01 = "textures/sprites/car01/car01Size06Left01.png";
	public final String CAR_01_SIZE_05_LEFT_01 = "textures/sprites/car01/car01Size05Left01.png";
	public final String CAR_01_SIZE_04_LEFT_01 = "textures/sprites/car01/car01Size04Left01.png";
	public final String CAR_01_SIZE_03_LEFT_01 = "textures/sprites/car01/car01Size03Left01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_02_LEFT_01 = "textures/sprites/car01/car01Size02Left01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_01_LEFT_01 = "textures/sprites/car01/car01Size01Left01.png"; // Not from F-zero.
	
	public final String CAR_01_SIZE_09_FRONT_01 = "textures/sprites/car01/car01Size09Front01.png";
	public final String CAR_01_SIZE_08_FRONT_01 = "textures/sprites/car01/car01Size08Front01.png";
	public final String CAR_01_SIZE_07_FRONT_01 = "textures/sprites/car01/car01Size07Front01.png";
	public final String CAR_01_SIZE_06_FRONT_01 = "textures/sprites/car01/car01Size06Front01.png";
	public final String CAR_01_SIZE_05_FRONT_01 = "textures/sprites/car01/car01Size05Front01.png";
	public final String CAR_01_SIZE_04_FRONT_01 = "textures/sprites/car01/car01Size04Front01.png";
	public final String CAR_01_SIZE_03_FRONT_01 = "textures/sprites/car01/car01Size03Front01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_02_FRONT_01 = "textures/sprites/car01/car01Size02Front01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_01_FRONT_01 = "textures/sprites/car01/car01Size01Front01.png"; // Not from F-zero.
	
	public final String CAR_01_SIZE_09_FRONT_LEFT_01 = "textures/sprites/car01/car01Size09FrontLeft01.png";
	public final String CAR_01_SIZE_08_FRONT_LEFT_01 = "textures/sprites/car01/car01Size08FrontLeft01.png";
	public final String CAR_01_SIZE_07_FRONT_LEFT_01 = "textures/sprites/car01/car01Size07FrontLeft01.png";
	public final String CAR_01_SIZE_06_FRONT_LEFT_01 = "textures/sprites/car01/car01Size06FrontLeft01.png";
	public final String CAR_01_SIZE_05_FRONT_LEFT_01 = "textures/sprites/car01/car01Size05FrontLeft01.png";
	public final String CAR_01_SIZE_04_FRONT_LEFT_01 = "textures/sprites/car01/car01Size04FrontLeft01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_03_FRONT_LEFT_01 = "textures/sprites/car01/car01Size03FrontLeft01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_02_FRONT_LEFT_01 = "textures/sprites/car01/car01Size02FrontLeft01.png"; // Not from F-zero.
	public final String CAR_01_SIZE_01_FRONT_LEFT_01 = "textures/sprites/car01/car01Size01FrontLeft01.png"; // Not from F-zero.
	
//	Car02
	public final String CAR_02_SIZE_09_BACK_01 = "textures/sprites/car02/car02Size09Back01.png";
	public final String CAR_02_SIZE_08_BACK_01 = "textures/sprites/car02/car02Size08Back01.png";
	public final String CAR_02_SIZE_07_BACK_01 = "textures/sprites/car02/car02Size07Back01.png";
	public final String CAR_02_SIZE_06_BACK_01 = "textures/sprites/car02/car02Size06Back01.png";
	public final String CAR_02_SIZE_05_BACK_01 = "textures/sprites/car02/car02Size05Back01.png";
	public final String CAR_02_SIZE_04_BACK_01 = "textures/sprites/car02/car02Size04Back01.png";
	public final String CAR_02_SIZE_03_BACK_01 = "textures/sprites/car02/car02Size03Back01.png";
	public final String CAR_02_SIZE_02_BACK_01 = "textures/sprites/car02/car02Size02Back01.png";
	public final String CAR_02_SIZE_01_BACK_01 = "textures/sprites/car02/car02Size01Back01.png";
	
	public final String CAR_02_SIZE_09_BACK_TURN_RIGHT_02 = "textures/sprites/car02/car02Size09BackTurnRight01.png"; // should be 02?
	
	public final String CAR_02_SIZE_09_BACK_LEFT_01 = "textures/sprites/car02/car02Size09BackLeft01.png";
	public final String CAR_02_SIZE_08_BACK_LEFT_01 = "textures/sprites/car02/car02Size08BackLeft01.png";
	public final String CAR_02_SIZE_07_BACK_LEFT_01 = "textures/sprites/car02/car02Size07BackLeft01.png";
	public final String CAR_02_SIZE_06_BACK_LEFT_01 = "textures/sprites/car02/car02Size06BackLeft01.png";
	public final String CAR_02_SIZE_05_BACK_LEFT_01 = "textures/sprites/car02/car02Size05BackLeft01.png";
	public final String CAR_02_SIZE_04_BACK_LEFT_01 = "textures/sprites/car02/car02Size04BackLeft01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_03_BACK_LEFT_01 = "textures/sprites/car02/car02Size03BackLeft01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_02_BACK_LEFT_01 = "textures/sprites/car02/car02Size02BackLeft01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_01_BACK_LEFT_01 = "textures/sprites/car02/car02Size01BackLeft01.png"; // Not from F-zero.
	
	public final String CAR_02_SIZE_09_LEFT_01 = "textures/sprites/car02/car02Size09Left01.png";
	public final String CAR_02_SIZE_08_LEFT_01 = "textures/sprites/car02/car02Size08Left01.png";
	public final String CAR_02_SIZE_07_LEFT_01 = "textures/sprites/car02/car02Size07Left01.png";
	public final String CAR_02_SIZE_06_LEFT_01 = "textures/sprites/car02/car02Size06Left01.png";
	public final String CAR_02_SIZE_05_LEFT_01 = "textures/sprites/car02/car02Size05Left01.png";
	public final String CAR_02_SIZE_04_LEFT_01 = "textures/sprites/car02/car02Size04Left01.png";
	public final String CAR_02_SIZE_03_LEFT_01 = "textures/sprites/car02/car02Size03Left01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_02_LEFT_01 = "textures/sprites/car02/car02Size02Left01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_01_LEFT_01 = "textures/sprites/car02/car02Size01Left01.png"; // Not from F-zero.
	
	public final String CAR_02_SIZE_09_FRONT_01 = "textures/sprites/car02/car02Size09Front01.png";
	public final String CAR_02_SIZE_08_FRONT_01 = "textures/sprites/car02/car02Size08Front01.png";
	public final String CAR_02_SIZE_07_FRONT_01 = "textures/sprites/car02/car02Size07Front01.png";
	public final String CAR_02_SIZE_06_FRONT_01 = "textures/sprites/car02/car02Size06Front01.png";
	public final String CAR_02_SIZE_05_FRONT_01 = "textures/sprites/car02/car02Size05Front01.png";
	public final String CAR_02_SIZE_04_FRONT_01 = "textures/sprites/car02/car02Size04Front01.png";
	public final String CAR_02_SIZE_03_FRONT_01 = "textures/sprites/car02/car02Size03Front01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_02_FRONT_01 = "textures/sprites/car02/car02Size02Front01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_01_FRONT_01 = "textures/sprites/car02/car02Size01Front01.png"; // Not from F-zero.
	
	public final String CAR_02_SIZE_09_FRONT_LEFT_01 = "textures/sprites/car02/car02Size09FrontLeft01.png";
	public final String CAR_02_SIZE_08_FRONT_LEFT_01 = "textures/sprites/car02/car02Size08FrontLeft01.png";
	public final String CAR_02_SIZE_07_FRONT_LEFT_01 = "textures/sprites/car02/car02Size07FrontLeft01.png";
	public final String CAR_02_SIZE_06_FRONT_LEFT_01 = "textures/sprites/car02/car02Size06FrontLeft01.png";
	public final String CAR_02_SIZE_05_FRONT_LEFT_01 = "textures/sprites/car02/car02Size05FrontLeft01.png";
	public final String CAR_02_SIZE_04_FRONT_LEFT_01 = "textures/sprites/car02/car02Size04FrontLeft01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_03_FRONT_LEFT_01 = "textures/sprites/car02/car02Size03FrontLeft01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_02_FRONT_LEFT_01 = "textures/sprites/car02/car02Size02FrontLeft01.png"; // Not from F-zero.
	public final String CAR_02_SIZE_01_FRONT_LEFT_01 = "textures/sprites/car02/car02Size01FrontLeft01.png"; // Not from F-zero.
	
	private void loadSprites() {
//		car01
		load(CAR_01_SIZE_09_BACK_01, Texture.class);
		load(CAR_01_SIZE_08_BACK_01, Texture.class);
		load(CAR_01_SIZE_07_BACK_01, Texture.class);
		load(CAR_01_SIZE_06_BACK_01, Texture.class);
		load(CAR_01_SIZE_05_BACK_01, Texture.class);
		load(CAR_01_SIZE_04_BACK_01, Texture.class);
		load(CAR_01_SIZE_03_BACK_01, Texture.class);
		load(CAR_01_SIZE_02_BACK_01, Texture.class);
		load(CAR_01_SIZE_01_BACK_01, Texture.class);
		
		load(CAR_01_SIZE_09_BACK_TURN_RIGHT_02, Texture.class);
		
		load(CAR_01_SIZE_09_BACK_LEFT_01, Texture.class);
		load(CAR_01_SIZE_08_BACK_LEFT_01, Texture.class);
		load(CAR_01_SIZE_07_BACK_LEFT_01, Texture.class);
		load(CAR_01_SIZE_06_BACK_LEFT_01, Texture.class);
		load(CAR_01_SIZE_05_BACK_LEFT_01, Texture.class);
		load(CAR_01_SIZE_04_BACK_LEFT_01, Texture.class);
		load(CAR_01_SIZE_03_BACK_LEFT_01, Texture.class);
		load(CAR_01_SIZE_02_BACK_LEFT_01, Texture.class);
		load(CAR_01_SIZE_01_BACK_LEFT_01, Texture.class);
		
		load(CAR_01_SIZE_09_LEFT_01, Texture.class);
		load(CAR_01_SIZE_08_LEFT_01, Texture.class);
		load(CAR_01_SIZE_07_LEFT_01, Texture.class);
		load(CAR_01_SIZE_06_LEFT_01, Texture.class);
		load(CAR_01_SIZE_05_LEFT_01, Texture.class);
		load(CAR_01_SIZE_04_LEFT_01, Texture.class);
		load(CAR_01_SIZE_03_LEFT_01, Texture.class);
		load(CAR_01_SIZE_02_LEFT_01, Texture.class);
		load(CAR_01_SIZE_01_LEFT_01, Texture.class);
		
		load(CAR_01_SIZE_09_FRONT_01, Texture.class);
		load(CAR_01_SIZE_08_FRONT_01, Texture.class);
		load(CAR_01_SIZE_07_FRONT_01, Texture.class);
		load(CAR_01_SIZE_06_FRONT_01, Texture.class);
		load(CAR_01_SIZE_05_FRONT_01, Texture.class);
		load(CAR_01_SIZE_04_FRONT_01, Texture.class);
		load(CAR_01_SIZE_03_FRONT_01, Texture.class);
		load(CAR_01_SIZE_02_FRONT_01, Texture.class);
		load(CAR_01_SIZE_01_FRONT_01, Texture.class);
		
		load(CAR_01_SIZE_09_FRONT_LEFT_01, Texture.class);
		load(CAR_01_SIZE_08_FRONT_LEFT_01, Texture.class);
		load(CAR_01_SIZE_07_FRONT_LEFT_01, Texture.class);
		load(CAR_01_SIZE_06_FRONT_LEFT_01, Texture.class);
		load(CAR_01_SIZE_05_FRONT_LEFT_01, Texture.class);
		load(CAR_01_SIZE_04_FRONT_LEFT_01, Texture.class);
		load(CAR_01_SIZE_03_FRONT_LEFT_01, Texture.class);
		load(CAR_01_SIZE_02_FRONT_LEFT_01, Texture.class);
		load(CAR_01_SIZE_01_FRONT_LEFT_01, Texture.class);
		
//		car02
		load(CAR_02_SIZE_09_BACK_01, Texture.class);
		load(CAR_02_SIZE_08_BACK_01, Texture.class);
		load(CAR_02_SIZE_07_BACK_01, Texture.class);
		load(CAR_02_SIZE_06_BACK_01, Texture.class);
		load(CAR_02_SIZE_05_BACK_01, Texture.class);
		load(CAR_02_SIZE_04_BACK_01, Texture.class);
		load(CAR_02_SIZE_03_BACK_01, Texture.class);
		load(CAR_02_SIZE_02_BACK_01, Texture.class);
		load(CAR_02_SIZE_01_BACK_01, Texture.class);
		
		load(CAR_02_SIZE_09_BACK_TURN_RIGHT_02, Texture.class);
		
		load(CAR_02_SIZE_09_BACK_LEFT_01, Texture.class);
		load(CAR_02_SIZE_08_BACK_LEFT_01, Texture.class);
		load(CAR_02_SIZE_07_BACK_LEFT_01, Texture.class);
		load(CAR_02_SIZE_06_BACK_LEFT_01, Texture.class);
		load(CAR_02_SIZE_05_BACK_LEFT_01, Texture.class);
		load(CAR_02_SIZE_04_BACK_LEFT_01, Texture.class);
		load(CAR_02_SIZE_03_BACK_LEFT_01, Texture.class);
		load(CAR_02_SIZE_02_BACK_LEFT_01, Texture.class);
		load(CAR_02_SIZE_01_BACK_LEFT_01, Texture.class);
		
		load(CAR_02_SIZE_09_LEFT_01, Texture.class);
		load(CAR_02_SIZE_08_LEFT_01, Texture.class);
		load(CAR_02_SIZE_07_LEFT_01, Texture.class);
		load(CAR_02_SIZE_06_LEFT_01, Texture.class);
		load(CAR_02_SIZE_05_LEFT_01, Texture.class);
		load(CAR_02_SIZE_04_LEFT_01, Texture.class);
		load(CAR_02_SIZE_03_LEFT_01, Texture.class);
		load(CAR_02_SIZE_02_LEFT_01, Texture.class);
		load(CAR_02_SIZE_01_LEFT_01, Texture.class);
		
		load(CAR_02_SIZE_09_FRONT_01, Texture.class);
		load(CAR_02_SIZE_08_FRONT_01, Texture.class);
		load(CAR_02_SIZE_07_FRONT_01, Texture.class);
		load(CAR_02_SIZE_06_FRONT_01, Texture.class);
		load(CAR_02_SIZE_05_FRONT_01, Texture.class);
		load(CAR_02_SIZE_04_FRONT_01, Texture.class);
		load(CAR_02_SIZE_03_FRONT_01, Texture.class);
		load(CAR_02_SIZE_02_FRONT_01, Texture.class);
		load(CAR_02_SIZE_01_FRONT_01, Texture.class);
		
		load(CAR_02_SIZE_09_FRONT_LEFT_01, Texture.class);
		load(CAR_02_SIZE_08_FRONT_LEFT_01, Texture.class);
		load(CAR_02_SIZE_07_FRONT_LEFT_01, Texture.class);
		load(CAR_02_SIZE_06_FRONT_LEFT_01, Texture.class);
		load(CAR_02_SIZE_05_FRONT_LEFT_01, Texture.class);
		load(CAR_02_SIZE_04_FRONT_LEFT_01, Texture.class);
		load(CAR_02_SIZE_03_FRONT_LEFT_01, Texture.class);
		load(CAR_02_SIZE_02_FRONT_LEFT_01, Texture.class);
		load(CAR_02_SIZE_01_FRONT_LEFT_01, Texture.class);
	}
	
//	Fonts
	public final String FONT_01_16 = "fonts/font01_16.fnt";
	public final String FONT_01_08 = "fonts/font01_08.fnt";
	
	private void loadFonts() {
		load(FONT_01_08, BitmapFont.class);
		load(FONT_01_16, BitmapFont.class);
	}
	
//	Skins
	public final String GAME_SKIN = "skins/slipstream-ui.json";
	
	private void loadSkins() {
		load(GAME_SKIN, Skin.class);
	}
	
//	Maps
	public final String MAP_01 = "maps/level02.png";
	public final String MAP_02 = "maps/level03.png";
	public final String MAP_03 = "maps/level04.png";
	public final String MAP_MUTE_CITY = "maps/levelMuteCity.png";
	public final String MAP_TEST = "maps/levelTest.png";
	
	public final String MAP_SILENCE = "maps/silence.tmx";
	
	private void loadMaps() {
		load(MAP_01, Texture.class);
		load(MAP_02, Texture.class);
		load(MAP_03, Texture.class);
		load(MAP_MUTE_CITY, Texture.class);
		load(MAP_TEST, Texture.class);
		
		load(MAP_SILENCE, TiledMap.class);
	}
	
//	SFX
//	public final String SFX = "...";
	
	private void loadSFX() {
//		load(SFX, Sound.class);
	}
	
//	Music
	public final String MUSIC_SILENCE = "sound/music/silence.mp3";
	public final String MUSIC_MUTE_CITY = "sound/music/muteCity.mp3";
	public final String MUSIC_BIG_BLUE = "sound/music/bigBlue.mp3";
	public final String MUSIC_ZOOM = "sound/music/zoom.mp3";
	public final String MUSIC_START = "sound/music/start.mp3";
	public final String MUSIC_ENDING_THEME = "sound/music/endingTheme.mp3";
	
	private void loadMusic() {
		load(MUSIC_SILENCE, Music.class);
		load(MUSIC_MUTE_CITY, Music.class);
		load(MUSIC_BIG_BLUE, Music.class);
		load(MUSIC_ZOOM, Music.class);
		load(MUSIC_START, Music.class);
		load(MUSIC_ENDING_THEME, Music.class);
	}
	
	@Override
	public synchronized void dispose() {
		super.dispose();
	}
}
