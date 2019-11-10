package mysko.pilzhere.mode7racer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer.AspectTextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.entities.Entity;
import mysko.pilzhere.mode7racer.entities.ModelInstanceBB;
import mysko.pilzhere.mode7racer.entities.tiles.Tile;

public class GameScreen implements Screen {
	private final Mode7Racer game;
	private final AssetManager assMan;
	private final SpriteBatch batch;
	private final ModelBatch mdlBatch;

	public PerspectiveCamera cam;
	private Viewport viewport;
	private final int viewportWidth = 256;
	private final int viewportHeight = 224;
	private final int viewportWidthStretched = 299; // As displayed from SNES.

	private Texture bgGrid;
	private Texture bgSample;
	public Texture tileCurbLeft;
	public Texture tileRoad01;
	public Texture tileVoid;

	public Model mdlTile;
	private ModelInstance mdlInstTile;
	
	private Texture levelBgFront;
	private Texture levelBgBack;
	private float bgFrontPosX = 0f;
	private float bgBackPosX = 0f;

	public Array<Entity> entities = new Array<Entity>();

	public ModelBatch getModelBatch() {
		return this.mdlBatch;
	}

	public GameScreen(Mode7Racer game) {
		this.game = game;
		this.assMan = this.game.getAssMan();
		this.batch = this.game.getBatch();
		this.mdlBatch = this.game.getModelBatch();

		final int fOV = 78; // 33 or 80
		cam = new PerspectiveCamera(fOV, 256, 224);
		cam.position.set(new Vector3(-0.5f, 1, 1));
		cam.near = 0.001f;
		cam.far = 40f;

		viewport = new FitViewport(viewportWidthStretched, viewportHeight, cam);

		bgGrid = assMan.get("bg01.png", Texture.class);
		bgSample = assMan.get("sample256.png", Texture.class);
		tileCurbLeft = assMan.get("curbLeft.png", Texture.class);
		tileRoad01 = assMan.get("road01.png", Texture.class);
		tileVoid = assMan.get("void.png", Texture.class);

		mdlTile = assMan.get("grassPlane.g3db", Model.class);
//		mdlInstTile = new ModelInstance(mdlTile);
//		mdlInstTile.transform.setTranslation(0, 0, -1);

//		read level file
		FileHandle handle = Gdx.files.local("level01.txt");
		String text = handle.readString();
		String wordsArray[] = text.split("\\r?\\n");

//		Check for level line length. Should be same length.
//		TODO: Make sure it looks for EVEN LINE LENGTH!
		for (int i = 0; i < wordsArray.length; i++) {
//			System.out.println(wordsArray[i]);
			if (wordsArray[i].length() != wordsArray[0].length()) {
				System.err.println("Error: Level: Lines not same length. (Line: " + (i + 1) + ")");
			}
		}

		final int levelXOffset = wordsArray[0].length() / 2;
		final int levelZOffset = wordsArray.length / 2;

//		System.out.println(levelXOffset + " * " + levelZOffset);

//		final char death = 'X';
//		final char road = '#';
//		final char curb = 'C';

		int hori = -1;
		int vert = -1;

////		Place tiles for each char at line
//		for (int x = -levelXOffset; x < levelXOffset; x++) {
//			hori++;
//			for (int z = -levelZOffset; z < levelZOffset; z++) {
//				vert++;
//				if (vert > wordsArray.length - 1) {
//					vert = 0;
//				}
//				final char type = wordsArray[vert].charAt(hori);
//				System.out.println("x: " + hori + " | " + "y: " + vert + " | char: " + type);
//				
//				entities.add(new Tile(this, new Vector3(x + 0.5f, 0, z), type));
//			}
//		}

		// read map fron .png
		Texture levelMap = assMan.get("level01.png", Texture.class);
		TextureData levelMapData = levelMap.getTextureData();
		if (!levelMapData.isPrepared()) {
			levelMapData.prepare();
		}

		Pixmap pixmap = levelMapData.consumePixmap();

//		Place tiles for each char at line FROM PIXMAP
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = 0; y < pixmap.getHeight(); y++) {
				final int pix = pixmap.getPixel(x, y);
				System.out.println(pix);

				entities.add(new Tile(this, new Vector3((x - (pixmap.getWidth() / 2)), 0, y - (pixmap.getHeight() / 2)), pix));
			}

		}
		
		levelBgBack = assMan.get("levelBg01Back.png", Texture.class);
		levelBgFront = assMan.get("levelBg01Front.png", Texture.class);
		levelBgBack.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		levelBgFront.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
	}

	private final Vector3 currentModelPos = new Vector3();

//	Used for BoundingBoxes, like for walls, stairs, floor, ceiling etc.
	public boolean isVisible(final Camera cam, final ModelInstanceBB modelInst) {
		modelInst.transform.getTranslation(currentModelPos);
		currentModelPos.add(modelInst.center);
		return cam.frustum.sphereInFrustum(currentModelPos, modelInst.radius);
	}

	@Override
	public void show() {
	}

	private final int moveSpeed = 25;
	private final int rotSpeed = 250;
	private final float bgSpeed = 2.135f;

	private void input(float delta) {		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			useFbo = !useFbo;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.position.add(cam.direction.cpy().nor().scl(moveSpeed * delta));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.position.add(cam.direction.cpy().nor().scl(-moveSpeed * delta));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {			
			cam.rotate(Vector3.Y, rotSpeed * delta);
			bgFrontPosX -= rotSpeed * bgSpeed * 1.5f * delta;
			bgBackPosX -= rotSpeed * bgSpeed * delta;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.rotate(Vector3.Y, -rotSpeed * delta);
			bgFrontPosX += rotSpeed * bgSpeed * 1.5f * delta;
			bgBackPosX += rotSpeed * bgSpeed * delta;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			cam.position.add(cam.direction.cpy().crs(cam.up).nor().scl(moveSpeed * delta));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			cam.position.add(cam.direction.cpy().crs(cam.up).nor().scl(-moveSpeed * delta));
		}
	}

	private void tick(float delta) {
		for (Entity entity : entities) {
			entity.tick(delta);
		}

		cam.position.y = 3.14f;
		cam.direction.y = -0.63f; // -0.59f
	}

	public int renderedModels = 0;

	private boolean useFbo = true;

	@Override
	public void render(float delta) {
		renderedModels = 0;

		input(delta);
		tick(delta);

//		OVERNIGHT AWESOMENESS
//		cam.position.add(cam.direction.cpy().nor().scl(moveSpeed * delta));
//		cam.rotate(Vector3.Y, -rotSpeed * delta);
//		cam.position.add(cam.direction.cpy().crs(cam.up).nor().scl(-moveSpeed * delta));
//		END

		cam.update();

		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight);

		if (useFbo) {
			game.fb01.begin();
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		}

		batch.begin();
		batch.draw(levelBgBack, 0, viewportHeight - 64 + 5, (int)bgBackPosX, 17, viewportWidthStretched, 64);
		batch.draw(levelBgFront, 0, viewportHeight - 64 + 5, (int)bgFrontPosX, 17, viewportWidthStretched, 64);
		batch.end();

		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidthStretched, viewportHeight);
		
		mdlBatch.begin(cam);
		for (Entity entity : entities) {
			entity.render3D(delta);
		}
		mdlBatch.end();

		if (useFbo) {
			game.fb01.end();
			viewport.apply(); // Else FBO draws over all screen.

			batch.begin();
			batch.draw(game.fb01.getColorBufferTexture(), 0, 0, viewportWidthStretched, viewportHeight, 0, 0, 1, 1);
			batch.end();
		}

		Gdx.app.getGraphics()
				.setTitle("Mode7Racer | " + Gdx.graphics.getFramesPerSecond() + " FPS | " + renderedModels + " models");
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		for (Entity entity : entities) {
			entity.dispose();
		}

		bgGrid.dispose();
		
		levelBgFront.dispose();
		levelBgBack.dispose();
	}

}
