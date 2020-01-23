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
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import mysko.pilzhere.mode7racer.inputs.GameInputManager;
import mysko.pilzhere.mode7racer.managers.AssetsManager;
import mysko.pilzhere.mode7racer.screens.GameScreen;
import mysko.pilzhere.mode7racer.storage.GameStorage;

/** 
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
	
	public AssetsManager getAssMan() {
		return assMan;
	}
	
	public ModelBatch getModelBatch() {
		return mdlBatch;
	}
	
	public Skin getSkin(){
		return assMan.get(assMan.gameSkin);
	}
	
	public GameStorage getStorage(){
		return storage;
	}
	
	private AssetsManager assMan;
	
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

	private GameStorage storage;
	public final GameInputManager inputs = new GameInputManager();
	
	private void updateCurrentTime() {
		currentTime = System.currentTimeMillis();
	}
	
	@Override
	public void create () {		
		System.out.println("Mode7Racer is running.");
		updateCurrentTime();
		System.out.println("STATUS: Setting up critical objects...");
		
		storage = new GameStorage(this);
		storage.load();		
		
		updateTimePro();
		
		fb01 = new FrameBuffer(Format.RGB888, 256, 224, true);
		fb01.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		mdlBatch = new ModelBatch();
		
		assMan = new AssetsManager();
		assMan.setLoader(TiledMap.class, new TmxMapLoader());
		
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
		assMan.loadAssets();
	}

	@Override
	public void render () {
		updateCurrentTime();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		getScreen().render(Gdx.graphics.getDeltaTime());

		inputs.swap();
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
