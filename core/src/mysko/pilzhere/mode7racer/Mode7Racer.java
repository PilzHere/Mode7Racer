package mysko.pilzhere.mode7racer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import mysko.pilzhere.mode7racer.screens.GameScreen;

/**
 * TODO: Implement classic intro cam.
 * TODO: Implement jumps
 * Jump from ramps.
 * TODO: Implement weapons?
 * F-zero X Mario kart?
 * Homing missiles? Lasers? Mines? Shield?
 * TODO: Implement more micro/macro gameplay.
 *  ...
 * TODO: Redo assets. Same style?, new look.
 * TODO: Implement environment sprites.
 * Buildings, domes, futuristic stuff...
 * TODO: Change license? Not MIT?
 * TODO: Sounds? Music?
 * Download samples and use them? Change/create new with Reaper and Akai MPK Mini MK2?
 * RafaSKB said we should contact Barry Leitch for music to the game. He has made music for Top Gear game and many more!
 * 
 * DONE:
 * * Implement sprite change from directon of camera.
 * 
 * @author pilzhere
 */

public class Mode7Racer extends Game {
	public long getCurrentTime() {
		return currentTime;
	}
	
	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public FrameBuffer getFb01() {
		return fb01;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	
	public AssetManager getAssMan() {
		return assMan;
	}
	
	public ModelBatch getModelBatch() {
		return mdlBatch;
	}
	
	private AssetManager assMan;
	
	private SpriteBatch batch;
	private ModelBatch mdlBatch;
	private ShapeRenderer shapeRenderer;
	
	private FrameBuffer fb01;

	public final long timeStarted = System.currentTimeMillis();
	
	private long timePro;
	
	private void updateTimePro() {
		timePro = System.currentTimeMillis();
	}
	
	private long timeEnd;
	
	private void updateTimeEnd(long timeStart) {
		timeEnd = System.currentTimeMillis() - timeStart;
	}
	
	private long currentTime;
	
	private void updateCurrentTime() {
		currentTime = System.currentTimeMillis();
	}
	
	@Override
	public void create () {		
		System.out.println("Mode7Racer is running.");
		System.out.println("STATUS: Setting up critical objects...");
		updateCurrentTime();
		updateTimePro();
		
		fb01 = new FrameBuffer(Format.RGB888, 256, 224, true);
		fb01.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		mdlBatch = new ModelBatch();
		
		assMan = new AssetManager();
		
		updateTimeEnd(timePro);
		System.out.println("STATUS: Finished setting up critical objects. (" + timeEnd + " ms)");
		
		System.out.println("STATUS: Loading assets...");
		updateTimePro();
		
		loadAssets();
		
		updateTimeEnd(timePro);
		System.out.println("STATUS: All assets loaded. (" + timeEnd + " ms)");
		
		System.out.println("STATUS: Setting up new screen...");
		updateTimePro();
		
		setScreen(new GameScreen(this));
		
		updateTimeEnd(timePro);
		System.out.println("STATUS: Screen done. (" + timeEnd + " ms");
		
		updateTimeEnd(timeStarted);
		System.out.println("STATUS: Total time for loading and setting up everything: (" + timeEnd + " ms)");
	}
	
	private void loadAssets() {
		assMan.load("bg01.png", Texture.class);
		assMan.load("sample256.png", Texture.class);
		assMan.load("grassPlane.g3db", Model.class);
		
		assMan.load("curb0101.png", Texture.class); // l
		assMan.load("curb1010.png", Texture.class); // r
		assMan.load("curb1100.png", Texture.class); // s
		assMan.load("curb0011.png", Texture.class); // n
		
		assMan.load("curb1110.png", Texture.class); // Outer corner nl
		assMan.load("curb1101.png", Texture.class); // Outer corner nr
		assMan.load("curb0111.png", Texture.class); // Outer corner sr
		assMan.load("curb1011.png", Texture.class); // Outer corner sl
		
		assMan.load("curb1000.png", Texture.class); // Inner corner nl
		assMan.load("curb0100.png", Texture.class); // Inner corner nr
		assMan.load("curb0010.png", Texture.class); // Inner corner sl
		assMan.load("curb0001.png", Texture.class); // Inner corner sr
		
		assMan.load("curb0000.png", Texture.class); // none
		assMan.load("curb1111.png", Texture.class); // full
		
		assMan.load("road01.png", Texture.class);
		assMan.load("road02.png", Texture.class);
		assMan.load("void.png", Texture.class);
		
		assMan.load("levelBg01Back.png", Texture.class);
		assMan.load("levelBg01Front.png", Texture.class);
		
		assMan.load("fog01.png", Texture.class);
		
		assMan.load("level02.png", Texture.class);
		assMan.load("level03.png", Texture.class);
		assMan.load("level04.png", Texture.class);
		
		assMan.load("car01Size09Back01.png", Texture.class);
		assMan.load("car01Size08Back01.png", Texture.class);
		assMan.load("car01Size07Back01.png", Texture.class);
		assMan.load("car01Size06Back01.png", Texture.class);
		assMan.load("car01Size05Back01.png", Texture.class);
		assMan.load("car01Size04Back01.png", Texture.class);
		assMan.load("car01Size03Back01.png", Texture.class);
		assMan.load("car01Size02Back01.png", Texture.class);
		assMan.load("car01Size01Back01.png", Texture.class);
		
		assMan.load("car01Size09BackTurnRight02.png", Texture.class);
		
		assMan.load("car01Size09BackLeft01.png", Texture.class);
		assMan.load("car01Size08BackLeft01.png", Texture.class);
		assMan.load("car01Size07BackLeft01.png", Texture.class);
		assMan.load("car01Size06BackLeft01.png", Texture.class);
		assMan.load("car01Size05BackLeft01.png", Texture.class);
		
		assMan.load("car01Size09Left01.png", Texture.class);
		assMan.load("car01Size08Left01.png", Texture.class);
		assMan.load("car01Size07Left01.png", Texture.class);
		assMan.load("car01Size06Left01.png", Texture.class);
		assMan.load("car01Size05Left01.png", Texture.class);
		assMan.load("car01Size04Left01.png", Texture.class);
		
		assMan.load("car01Size09Front01.png", Texture.class);
		assMan.load("car01Size08Front01.png", Texture.class);
		assMan.load("car01Size07Front01.png", Texture.class);
		assMan.load("car01Size06Front01.png", Texture.class);
		assMan.load("car01Size05Front01.png", Texture.class);
		assMan.load("car01Size04Front01.png", Texture.class);
		
		assMan.load("car01Size09FrontLeft01.png", Texture.class);
		assMan.load("car01Size08FrontLeft01.png", Texture.class);
		assMan.load("car01Size07FrontLeft01.png", Texture.class);
		assMan.load("car01Size06FrontLeft01.png", Texture.class);
		assMan.load("car01Size05FrontLeft01.png", Texture.class);
		
		assMan.load("fonts/font01_16.fnt", BitmapFont.class);
		
		assMan.finishLoading();
	}

	@Override
	public void render () {
		updateCurrentTime();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		getScreen().dispose();
		
		fb01.dispose();
		
		mdlBatch.dispose();
		
		shapeRenderer.dispose();
		
		batch.dispose();
		
		assMan.dispose();
	}
}
