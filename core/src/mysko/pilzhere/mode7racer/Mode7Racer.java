package mysko.pilzhere.mode7racer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.UBJsonReader;

import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Mode7Racer extends Game {
	private AssetManager assMan;
	
	private SpriteBatch batch;
	private ModelBatch mdlBatch;
	private ShapeRenderer shapeRenderer;
	
	public G3dModelLoader mdlLoader;
	
	private FrameBuffer fb01;
	
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

	@Override
	public void create () {
		System.out.println("Mode7Racer is running.");
		
		assMan = new AssetManager();
		
		fb01 = new FrameBuffer(Format.RGB888, 256, 224, true);
		fb01.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		mdlBatch = new ModelBatch();
		
		loadAssets();
		
		setScreen(new GameScreen(this));
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
		
		assMan.load("car01BigBack01.png", Texture.class);
		
		assMan.load("car01Big.g3db", Model.class);
		
		assMan.finishLoading();
	}

	@Override
	public void render () {		
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
