package mysko.pilzhere.mode7racer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import mysko.pilzhere.mode7racer.constants.GameConstants;
import mysko.pilzhere.mode7racer.inputs.GameInputManager;
import mysko.pilzhere.mode7racer.managers.AssetsManager;
import mysko.pilzhere.mode7racer.screens.MenuScreen;
import mysko.pilzhere.mode7racer.storage.GameStorage;

/** 
 * @author pilzhere
 */

public class Mode7Racer extends Game {
	
	public boolean isGameVolumeMute() {
		return gameVolumeMute;
	}

	private float lastGameVolume;
	
	public void setGameVolumeMute(final boolean gameVolumeMute) {
		if (gameVolumeMute) {
			lastGameVolume = gameVolume;
			gameVolume = 0;
			System.out.println("STATUS: Volume muted.");
		} else {
			gameVolume = lastGameVolume;
			System.out.println("STATUS: Volume unmuted.");
		}
		
		this.gameVolumeMute = gameVolumeMute;
	}

	public float getGameVolume() {
		return gameVolume;
	}

	public void setGameVolume(float gameVolume) {		
		gameVolume = gameVolume > 1 ? 1 : gameVolume;
		gameVolume = gameVolume < 0 ? 0 : gameVolume;
		
		this.gameVolume = gameVolume;
	}

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
		return assMan.get(assMan.GAME_SKIN);
	}
	
	public GameStorage getStorage(){
		return storage;
	}
	
	private AssetsManager assMan;
	
	private SpriteBatch batch;
	private ModelBatch mdlBatch;
	private ShapeRenderer shapeRenderer;
	
	private FrameBuffer fb01;
	
	private float gameVolume = GameConstants.GAME_MUSIC_VOLUME_DEFAULT;
	private boolean gameVolumeMute;

	public final long TIME_STARTED = System.currentTimeMillis();
	
	private long timePro;
	
	private void updateTimePro() {
		timePro = System.currentTimeMillis();
	}
	
	private long timeEnd;
	
	private void updateTimeEnd(final long timeStart) {
		timeEnd = System.currentTimeMillis() - timeStart;
	}
	
	private long currentTime;

	private GameStorage storage;
	public final GameInputManager INPUTS = new GameInputManager();
	
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
		
		fb01 = new FrameBuffer(Format.RGB888, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT, true);
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
		
		// XXX setScreen(new GameScreen(this));
		setScreen(new MenuScreen(this));
		
		updateTimeEnd(timePro);
		System.out.println("STATUS: Screen done. (" + timeEnd + " ms");
		
		updateTimeEnd(TIME_STARTED);
		System.out.println("STATUS: Total time for loading and setting up everything: (" + timeEnd + " ms)");
	}
	
	private void loadAssets() {
		assMan.loadAssets();
	}

	@Override
	public void render () {
		updateCurrentTime();
		clearScreen();
		
		getScreen().render(Gdx.graphics.getDeltaTime());

		INPUTS.swap();
	}
	
	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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
