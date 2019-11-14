package mysko.pilzhere.mode7racer.screens;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer.AspectTextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
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
	public Texture texTileCurbLeft;
	public Texture texTileRoad01;
	public Texture texTileVoid;

	public Model mdlTile;
	private ModelInstance mdlInstTile;

	private Texture levelBgFront;
	private Texture levelBgBack;
	private float bgFrontPosX = 0f;
	private float bgBackPosX = 0f;

	private Model mdlLevel;
	public ModelInstance mdlInstLevel;

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
		texTileCurbLeft = assMan.get("curbLeft.png", Texture.class);
		texTileRoad01 = assMan.get("road01.png", Texture.class);
		texTileVoid = assMan.get("void.png", Texture.class);

		mdlTile = assMan.get("grassPlane.g3db", Model.class);
//		mdlInstTile = new ModelInstance(mdlTile);
//		mdlInstTile.transform.setTranslation(0, 0, -1);

//		loadLevelFromText();
//		loadLevelFromTexture();
		loadLevelFromTexture2();

		levelBgBack = assMan.get("levelBg01Back.png", Texture.class);
		levelBgFront = assMan.get("levelBg01Front.png", Texture.class);
		levelBgBack.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		levelBgFront.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
	}

	private void loadLevelFromText() {
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

		final char death = 'X';
		final char road = '#';
		final char curb = 'C';

		int hori = -1;
		int vert = -1;

//		Place tiles for each char at line
		for (int x = -levelXOffset; x < levelXOffset; x++) {
			hori++;
			for (int z = -levelZOffset; z < levelZOffset; z++) {
				vert++;
				if (vert > wordsArray.length - 1) {
					vert = 0;
				}
				final char type = wordsArray[vert].charAt(hori);
				System.out.println("x: " + hori + " | " + "y: " + vert + " | char: " + type);

				entities.add(new Tile(this, new Vector3(x + 0.5f, 0, z), type));
			}
		}
	}

	private void loadLevelFromTexture() {
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

				entities.add(new Tile(this, new Vector3((x - (pixmap.getWidth() / 2)), 0, y - (pixmap.getHeight() / 2)),
						pix));
			}

		}
	}

	private final int tileSize = 16;

	private final int red = Color.rgba8888(Color.RED);
	private final int green = Color.rgba8888(Color.GREEN);
	private final int grey = Color.rgba8888(Color.GRAY);
	
	private void loadLevelFromTexture2() {
		long startTime = System.nanoTime();
		System.out.println("STATUS: Building level started...");
		
		Texture levelLevelMap = assMan.get("level01.png", Texture.class);
		TextureData loadedlevelMapData = levelLevelMap.getTextureData();
		if (!loadedlevelMapData.isPrepared()) {
			loadedlevelMapData.prepare();
		}

		Pixmap loadedLevelPixmap = loadedlevelMapData.consumePixmap();

		Pixmap generatedLevelPixmap = new Pixmap(loadedLevelPixmap.getWidth() * tileSize,
				loadedLevelPixmap.getHeight() * tileSize, Format.RGB888);
		final int loadedlevelTextureWidth = loadedLevelPixmap.getWidth();
		final int loadedlevelTextureHeight = loadedLevelPixmap.getHeight();

		TextureData tileCurbLeftData = texTileCurbLeft.getTextureData();
		if (!tileCurbLeftData.isPrepared()) {
			tileCurbLeftData.prepare();
		}

		Pixmap pixTileCurbLeft = tileCurbLeftData.consumePixmap();

		TextureData tileRoad01Data = texTileRoad01.getTextureData();
		if (!tileRoad01Data.isPrepared()) {
			tileRoad01Data.prepare();
		}

		Pixmap pixTileRoad01 = tileRoad01Data.consumePixmap();

		TextureData tileVoidData = texTileVoid.getTextureData();
		if (!tileVoidData.isPrepared()) {
			tileVoidData.prepare();
		}

		Pixmap pixTileVoid01 = tileVoidData.consumePixmap();
		
		int[][] array2d = new int[loadedlevelTextureWidth + 1][loadedlevelTextureHeight + 1];

		int loadedMapPixelX = -1;
		int loadedMapPixelY = -1;

//		Place pixels to pixmap for each pixel from texture loaded.
		for (int x = 0; x < generatedLevelPixmap.getWidth(); x++) {
			if (x % loadedlevelTextureWidth / (tileSize / 2) == 0) {
				loadedMapPixelX++;
			}

			for (int y = 0; y < generatedLevelPixmap.getHeight(); y++) {
				if (loadedlevelTextureHeight % tileSize == 0) {
					loadedMapPixelY++;
					if (loadedMapPixelY > loadedlevelTextureHeight) {
						loadedMapPixelY = 0;
					}
				}

				final int currentColor = loadedLevelPixmap.getPixel(loadedMapPixelX, loadedMapPixelY);
				array2d[loadedMapPixelX][loadedMapPixelY] = currentColor;
			}
		}

		int tileX = -1;
		int tileY = -1;
		
		for (int i = 0; i < loadedLevelPixmap.getWidth() * tileSize; i++) {
			tileX++;
			if (tileX > tileSize - 1) {
				tileX = 0;
			}

			for (int j = 0; j < loadedLevelPixmap.getHeight() * tileSize; j++) {
				tileY++;
				if (tileY > tileSize - 1) {
					tileY = 0;
				}

				final int color = array2d[(i / tileSize)][(j / tileSize)];
				if (color == red) {
//					System.out.println("RED");
					generatedLevelPixmap.drawPixel(i, j, pixTileVoid01.getPixel(tileX, tileY));
				} else if (color == green) {
//					System.out.println("GREEN");
					generatedLevelPixmap.drawPixel(i, j, pixTileCurbLeft.getPixel(tileX, tileY));
				} else if (color == grey) {
//					System.out.println("GREY");
					generatedLevelPixmap.drawPixel(i, j, pixTileRoad01.getPixel(tileX, tileY));
				} else {
//					generatedLevelPixmap.drawPixel(i, j, Color.YELLOW.toIntBits());
//					System.out.println("OTHER");
				}
			}
		}

		Texture levelTextureGenerated = new Texture(generatedLevelPixmap);
		levelTextureGenerated.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		ModelBuilder modelBuilder = new ModelBuilder();
		mdlLevel = modelBuilder.createBox(loadedLevelPixmap.getWidth(), 0, loadedLevelPixmap.getHeight(),
				new Material(TextureAttribute.createDiffuse(levelTextureGenerated)),
				Usage.Position | Usage.TextureCoordinates);
		mdlInstLevel = new ModelInstance(mdlLevel);
		mdlInstLevel.transform.rotate(Vector3.Y, 90);

		loadedLevelPixmap.dispose();
		generatedLevelPixmap.dispose();
		pixTileCurbLeft.dispose();
		pixTileRoad01.dispose();
		pixTileVoid01.dispose();
		
		long endTime = System.nanoTime();
		System.out.println("STATUS: Building level finished. (" + (endTime - startTime) / 1000000 + " ms)");
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
		batch.draw(levelBgBack, 0, viewportHeight - 64 + 5, (int) bgBackPosX, 17, viewportWidthStretched, 64);
		batch.draw(levelBgFront, 0, viewportHeight - 64 + 5, (int) bgFrontPosX, 17, viewportWidthStretched, 64);
		batch.end();

		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidthStretched, viewportHeight);

		mdlBatch.begin(cam);
		for (Entity entity : entities) {
			entity.render3D(delta);
		}

		mdlBatch.render(mdlInstLevel);

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
