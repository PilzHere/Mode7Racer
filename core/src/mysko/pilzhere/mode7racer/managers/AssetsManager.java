package mysko.pilzhere.mode7racer.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetsManager extends AssetManager {
	public void loadAssets() {
		loadTextures();
		loadFonts();
		loadSkins();
		
		finishLoading();
	}
	
	private void loadTextures() {
		loadBackgrounds();
		loadTiles();
		loadSprites();
		loadMaps();
	}
	
//	Tiles
	public final String road01 = "textures/tiles/road01.png";
	public final String road02 = "textures/tiles/road02.png";
	public final String void01 = "textures/tiles/void.png";
	
	public final String jumpHori01 = "textures/tiles/jumpHorizontal01.png";
	public final String jumpHoriLeft01 = "textures/tiles/jumpHorizontalLeft01.png";
	public final String jumpHoriRight01 = "textures/tiles/jumpHorizontalRight01.png";
	
	public final String jumpVert01 = "textures/tiles/jumpVertical01.png";
	public final String jumpVertTop01 = "textures/tiles/jumpVerticalTop01.png";
	public final String jumpVertBottom01 = "textures/tiles/jumpVerticalBottom01.png";
	
	public final String curb0101 = "textures/tiles/curb0101.png"; // l
	public final String curb1010 = "textures/tiles/curb1010.png"; // r
	public final String curb1100 = "textures/tiles/curb1100.png"; // s
	public final String curb0011 = "textures/tiles/curb0011.png"; // n
	
	public final String curb1110 = "textures/tiles/curb1110.png"; // outer corner nl
	public final String curb1101 = "textures/tiles/curb1101.png"; // outer corner nr
	public final String curb0111 = "textures/tiles/curb0111.png"; // outer corner sr
	public final String curb1011 = "textures/tiles/curb1011.png"; // outer corner sl
	
	public final String curb1000 = "textures/tiles/curb1000.png"; // inner corner nl
	public final String curb0100 = "textures/tiles/curb0100.png"; // inner corner nr
	public final String curb0010 = "textures/tiles/curb0010.png"; // inner corner sl
	public final String curb0001 = "textures/tiles/curb0001.png"; // inner corner sr
	
	public final String curb0000 = "textures/tiles/curb0000.png"; // none
	public final String curb1111 = "textures/tiles/curb1111.png"; // full
	
	public final String goalVert01 = "textures/tiles/goalVertical01.png";
	public final String goalHori01 = "textures/tiles/goalHorizontal01.png";
	
	private void loadTiles() {
		load(road01, Texture.class);
		load(road02, Texture.class);
		load(void01, Texture.class);
		
		load(jumpHori01, Texture.class);
		load(jumpHoriLeft01, Texture.class);
		load(jumpHoriRight01, Texture.class);
		
		load(curb0101, Texture.class);
		load(curb1010, Texture.class);
		load(curb1100, Texture.class);
		load(curb0011, Texture.class);
		
		load(curb1110, Texture.class);
		load(curb1101, Texture.class);
		load(curb0111, Texture.class);
		load(curb1011, Texture.class);
		
		load(curb1000, Texture.class);
		load(curb0100, Texture.class);
		load(curb0010, Texture.class);
		load(curb0001, Texture.class);
		
		load(curb0000, Texture.class);
		load(curb1111, Texture.class);
		
		load(jumpVert01, Texture.class);
		load(jumpVertTop01, Texture.class);
		load(jumpVertBottom01, Texture.class);
		
		load(goalVert01, Texture.class);
		load(goalHori01, Texture.class);
	}
	
//	Backgrounds
	public final String bg01Back = "textures/backgrounds/levelBg01Back.png";
	public final String bg01Front = "textures/backgrounds/levelBg01Front.png";
	public final String bgFog = "textures/backgrounds/fog01.png";
	
	private void loadBackgrounds() {
		load(bg01Back, Texture.class);
		load(bg01Front, Texture.class);
		load(bgFog, Texture.class);
	}
	
//	Sprites
	public final String car01Size09Back01 = "textures/sprites/car01/car01Size09Back01.png";
	public final String car01Size08Back01 = "textures/sprites/car01/car01Size08Back01.png";
	public final String car01Size07Back01 = "textures/sprites/car01/car01Size07Back01.png";
	public final String car01Size06Back01 = "textures/sprites/car01/car01Size06Back01.png";
	public final String car01Size05Back01 = "textures/sprites/car01/car01Size05Back01.png";
	public final String car01Size04Back01 = "textures/sprites/car01/car01Size04Back01.png";
	public final String car01Size03Back01 = "textures/sprites/car01/car01Size03Back01.png";
	public final String car01Size02Back01 = "textures/sprites/car01/car01Size02Back01.png";
	public final String car01Size01Back01 = "textures/sprites/car01/car01Size01Back01.png";
	
	public final String car01Size09BackTurnRight02 = "textures/sprites/car01/car01Size09BackTurnRight02.png";
	
	public final String car01Size09BackLeft01 = "textures/sprites/car01/car01Size09BackLeft01.png";
	public final String car01Size08BackLeft01 = "textures/sprites/car01/car01Size08BackLeft01.png";
	public final String car01Size07BackLeft01 = "textures/sprites/car01/car01Size07BackLeft01.png";
	public final String car01Size06BackLeft01 = "textures/sprites/car01/car01Size06BackLeft01.png";
	public final String car01Size05BackLeft01 = "textures/sprites/car01/car01Size05BackLeft01.png";
	public final String car01Size04BackLeft01 = "textures/sprites/car01/car01Size04BackLeft01.png"; // Not from F-zero.
	public final String car01Size03BackLeft01 = "textures/sprites/car01/car01Size03BackLeft01.png"; // Not from F-zero.
	public final String car01Size02BackLeft01 = "textures/sprites/car01/car01Size02BackLeft01.png"; // Not from F-zero.
	public final String car01Size01BackLeft01 = "textures/sprites/car01/car01Size01BackLeft01.png"; // Not from F-zero.
	
	public final String car01Size09Left01 = "textures/sprites/car01/car01Size09Left01.png";
	public final String car01Size08Left01 = "textures/sprites/car01/car01Size08Left01.png";
	public final String car01Size07Left01 = "textures/sprites/car01/car01Size07Left01.png";
	public final String car01Size06Left01 = "textures/sprites/car01/car01Size06Left01.png";
	public final String car01Size05Left01 = "textures/sprites/car01/car01Size05Left01.png";
	public final String car01Size04Left01 = "textures/sprites/car01/car01Size04Left01.png";
	public final String car01Size03Left01 = "textures/sprites/car01/car01Size03Left01.png"; // Not from F-zero.
	public final String car01Size02Left01 = "textures/sprites/car01/car01Size02Left01.png"; // Not from F-zero.
	public final String car01Size01Left01 = "textures/sprites/car01/car01Size01Left01.png"; // Not from F-zero.
	
	public final String car01Size09Front01 = "textures/sprites/car01/car01Size09Front01.png";
	public final String car01Size08Front01 = "textures/sprites/car01/car01Size08Front01.png";
	public final String car01Size07Front01 = "textures/sprites/car01/car01Size07Front01.png";
	public final String car01Size06Front01 = "textures/sprites/car01/car01Size06Front01.png";
	public final String car01Size05Front01 = "textures/sprites/car01/car01Size05Front01.png";
	public final String car01Size04Front01 = "textures/sprites/car01/car01Size04Front01.png";
	public final String car01Size03Front01 = "textures/sprites/car01/car01Size03Front01.png"; // Not from F-zero.
	public final String car01Size02Front01 = "textures/sprites/car01/car01Size02Front01.png"; // Not from F-zero.
	public final String car01Size01Front01 = "textures/sprites/car01/car01Size01Front01.png"; // Not from F-zero.
	
	public final String car01Size09FrontLeft01 = "textures/sprites/car01/car01Size09FrontLeft01.png";
	public final String car01Size08FrontLeft01 = "textures/sprites/car01/car01Size08FrontLeft01.png";
	public final String car01Size07FrontLeft01 = "textures/sprites/car01/car01Size07FrontLeft01.png";
	public final String car01Size06FrontLeft01 = "textures/sprites/car01/car01Size06FrontLeft01.png";
	public final String car01Size05FrontLeft01 = "textures/sprites/car01/car01Size05FrontLeft01.png";
	public final String car01Size04FrontLeft01 = "textures/sprites/car01/car01Size04FrontLeft01.png"; // Not from F-zero.
	public final String car01Size03FrontLeft01 = "textures/sprites/car01/car01Size03FrontLeft01.png"; // Not from F-zero.
	public final String car01Size02FrontLeft01 = "textures/sprites/car01/car01Size02FrontLeft01.png"; // Not from F-zero.
	public final String car01Size01FrontLeft01 = "textures/sprites/car01/car01Size01FrontLeft01.png"; // Not from F-zero.
	
	private void loadSprites() {
		load(car01Size09Back01, Texture.class);
		load(car01Size08Back01, Texture.class);
		load(car01Size07Back01, Texture.class);
		load(car01Size06Back01, Texture.class);
		load(car01Size05Back01, Texture.class);
		load(car01Size04Back01, Texture.class);
		load(car01Size03Back01, Texture.class);
		load(car01Size02Back01, Texture.class);
		load(car01Size01Back01, Texture.class);
		
		load(car01Size09BackTurnRight02, Texture.class);
		
		load(car01Size09BackLeft01, Texture.class);
		load(car01Size08BackLeft01, Texture.class);
		load(car01Size07BackLeft01, Texture.class);
		load(car01Size06BackLeft01, Texture.class);
		load(car01Size05BackLeft01, Texture.class);
		load(car01Size04BackLeft01, Texture.class);
		load(car01Size03BackLeft01, Texture.class);
		load(car01Size02BackLeft01, Texture.class);
		load(car01Size01BackLeft01, Texture.class);
		
		load(car01Size09Left01, Texture.class);
		load(car01Size08Left01, Texture.class);
		load(car01Size07Left01, Texture.class);
		load(car01Size06Left01, Texture.class);
		load(car01Size05Left01, Texture.class);
		load(car01Size04Left01, Texture.class);
		load(car01Size03Left01, Texture.class);
		load(car01Size02Left01, Texture.class);
		load(car01Size01Left01, Texture.class);
		
		load(car01Size09Front01, Texture.class);
		load(car01Size08Front01, Texture.class);
		load(car01Size07Front01, Texture.class);
		load(car01Size06Front01, Texture.class);
		load(car01Size05Front01, Texture.class);
		load(car01Size04Front01, Texture.class);
		load(car01Size03Front01, Texture.class);
		load(car01Size02Front01, Texture.class);
		load(car01Size01Front01, Texture.class);
		
		load(car01Size09FrontLeft01, Texture.class);
		load(car01Size08FrontLeft01, Texture.class);
		load(car01Size07FrontLeft01, Texture.class);
		load(car01Size06FrontLeft01, Texture.class);
		load(car01Size05FrontLeft01, Texture.class);
		load(car01Size04FrontLeft01, Texture.class);
		load(car01Size03FrontLeft01, Texture.class);
		load(car01Size02FrontLeft01, Texture.class);
		load(car01Size01FrontLeft01, Texture.class);
	}
	
//	Fonts
	public final String font01_16 = "fonts/font01_16.fnt";
	public final String font01_08 = "fonts/font01_08.fnt";
	
	private void loadFonts() {
		load(font01_08, BitmapFont.class);
		load(font01_16, BitmapFont.class);
	}
	
//	Skins
	public final String gameSkin = "skins/game-skin.json";
	
	private void loadSkins() {
		load(gameSkin, Skin.class);
	}
	
//	Maps
	public final String map01 = "maps/level02.png";
	public final String map02 = "maps/level03.png";
	public final String map03 = "maps/level04.png";
	public final String mapMuteCity = "maps/levelMuteCity.png";
	
	public final String mapSilence = "maps/silence.tmx";
	
	private void loadMaps() {
		load(map01, Texture.class);
		load(map02, Texture.class);
		load(map03, Texture.class);
		load(mapMuteCity, Texture.class);
		
		load(mapSilence, TiledMap.class);
	}
	
	@Override
	public synchronized void dispose() {
		super.dispose();
	}
}
