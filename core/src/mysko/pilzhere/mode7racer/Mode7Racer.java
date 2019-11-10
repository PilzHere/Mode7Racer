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
import com.badlogic.gdx.utils.UBJsonReader;

import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Mode7Racer extends Game {
	private AssetManager assMan;
	
	private SpriteBatch batch;
	private ModelBatch mdlBatch;
	
	public G3dModelLoader mdlLoader;
	
	public FrameBuffer fb01;
	
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
		assMan = new AssetManager();
		
		fb01 = new FrameBuffer(Format.RGBA8888, 256, 224, true);
		fb01.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		batch = new SpriteBatch();
		mdlBatch = new ModelBatch();
		
		loadAssets();
		
		setScreen(new GameScreen(this));
	}
	
	private void loadAssets() {
		assMan.load("bg01.png", Texture.class);
		assMan.load("sample256.png", Texture.class);
		assMan.load("grassPlane.g3db", Model.class);
		assMan.load("curbLeft.png", Texture.class);
		assMan.load("road01.png", Texture.class);
		assMan.load("void.png", Texture.class);
		
		assMan.load("levelBg01Back.png", Texture.class);
		assMan.load("levelBg01Front.png", Texture.class);
		
		assMan.load("level01.png", Texture.class);
		
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
		
		assMan.dispose();
		
		batch.dispose();
	}
}
