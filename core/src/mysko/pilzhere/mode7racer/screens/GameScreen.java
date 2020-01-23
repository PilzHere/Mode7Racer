package mysko.pilzhere.mode7racer.screens;

import java.util.Comparator;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.entities.Car;
import mysko.pilzhere.mode7racer.entities.Entity;
import mysko.pilzhere.mode7racer.entities.MapMaker;
import mysko.pilzhere.mode7racer.entities.ModelInstanceBB;
import mysko.pilzhere.mode7racer.inputs.GameInputManager.PlayerCommand;
import mysko.pilzhere.mode7racer.inputs.base.ControllerBase;
import mysko.pilzhere.mode7racer.loaders.MapData;
import mysko.pilzhere.mode7racer.loaders.MapLoader;
import mysko.pilzhere.mode7racer.managers.AssetsManager;
import mysko.pilzhere.mode7racer.ui.GameHUD;

public class GameScreen implements Screen {
	public Array<Entity> getEntities() {
		return ents;
	}

	public MapMaker getCurrentMap() {
		return mapMaker;
	}

	public PerspectiveCamera getCam() {
		return cam;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public long getCurrentTime() {
		return game.getCurrentTime();
	}

	public ModelBatch getModelBatch() {
		return this.mdlBatch;
	}

	public final int bgMoveSpeed = 80; // 2.135f
	public final float bgMoveSpeedBoost = 2.0f; // 1.5f
	public final float camDesiredDistFromCar = 2.3f;

	private final Mode7Racer game;
	public final AssetsManager assMan; // get
	private final SpriteBatch batch;
	private final ShapeRenderer shapeRenderer;
	private final ModelBatch mdlBatch;

	private final FrameBuffer fbo;

	private PerspectiveCamera cam;
	private Viewport viewport; // get
	public final int viewportWidth = 256;
	public final int viewportHeight = 224;
	public final int viewportWidthStretched = 299; // As displayed stretched from SNES output.

	public Car playerCar; // test
	private MapMaker mapMaker;
	private Array<Entity> ents = new Array<Entity>();
	public Array<Sprite> sprites = new Array<Sprite>(); // get
	public HashMap<Integer, Car> cars = new HashMap<Integer, Car>();

	private BitmapFont font01;
	
	private Stage stage;
	private GameHUD hud;

	public GameScreen(Mode7Racer game) {
		this.game = game;
		this.assMan = this.game.getAssMan();
		this.batch = this.game.getBatch();
		this.shapeRenderer = this.game.getShapeRenderer();
		this.mdlBatch = this.game.getModelBatch();
		this.fbo = this.game.getFb01();

//		font01 = assMan.get("fonts/font01_16.fnt");
		font01 = assMan.get(assMan.font01_08);

		final int fOV = 78; // 33 or 80
		cam = new PerspectiveCamera(fOV, 256, 224);
		cam.position.set(new Vector3(0, 0, 2.3f)); // Z 1.8f
//		cam.lookAt(new Vector3(0, 1, 1));
		cam.near = 0.001f;
		cam.far = 40f;

		viewport = new FitViewport(viewportWidthStretched, viewportHeight, cam);

		mapMaker = new MapMaker(this);

		// old map loading
//		currentMap.loadLevelFromTexture();

		// new map loading
		final MapData mapData = new MapLoader().load(assMan.get(assMan.mapSilence, TiledMap.class));
		mapMaker.loadLevelFromTexture(mapData);
		
		cars.put(0, new Car(this, new Vector3(0, 0, 0), true, false));
		cars.put(1, new Car(this, new Vector3(0, 0, -2), false, true));

		playerCar = cars.get(0);

		System.err.println("Size after: " + ents.size);

		stage = new Stage(new FitViewport(299 * 4, 224 * 4));
		stage.addActor(hud = new GameHUD(game, this, game.getSkin()));
	}

	private final Vector3 currentModelPos = new Vector3();

	public boolean isVisible(final Camera cam, final ModelInstanceBB modelInst) {
		modelInst.transform.getTranslation(currentModelPos);
		currentModelPos.add(modelInst.center);
		return cam.frustum.sphereInFrustum(currentModelPos, modelInst.radius);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	private boolean dbgUseFbo = true;
	private boolean dbgDisplayMap = false;

	private void input(float delta) {
		
		final ControllerBase controller = game.inputs.getController(0);
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			dbgUseFbo = !dbgUseFbo;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			dbgDisplayMap = !dbgDisplayMap;
		}
		
		if(hud.isSettingsOpened()) return;
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			hud.showSettings();
		}

		if (playerCar != null) {
//			if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
//				playerCar.resetPosition();
//			}
			
			if (controller.isOn(PlayerCommand.TURBO)) {
				playerCar.onInputT(delta);
			}
			
			if (controller.isOn(PlayerCommand.LEFT)) {
				playerCar.onInputA(delta);
			}

			if (controller.isOn(PlayerCommand.RIGHT)) {
				playerCar.onInputD(delta);
			}
			
			if (controller.isOn(PlayerCommand.SHIFT_LEFT)) {
				playerCar.onInputShiftLeft(delta);
			}
			
			if (controller.isOn(PlayerCommand.SHIFT_RIGHT)) {
				playerCar.onInputShiftRight(delta);
			}
			

			if (!controller.isOn(PlayerCommand.THROTTLE))
				playerCar.onNoInputW(delta);
			else if (controller.isOn(PlayerCommand.THROTTLE)) {
				playerCar.onInputW(delta);
			}

			if (controller.isOn(PlayerCommand.BRAKE)) {
				playerCar.onInputS(delta);
//				playerCar.moveUp(delta); // TEST
			}
		}

		/**
		 * OLD if (Gdx.input.isKeyPressed(Input.Keys.E)) {
		 * cam.position.add(cam.direction.cpy().crs(cam.up).nor().scl(moveSpeed *
		 * delta)); }
		 * 
		 * if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
		 * cam.position.add(cam.direction.cpy().crs(cam.up).nor().scl(-moveSpeed *
		 * delta)); }
		 **/
	}

	int carCount; // test

	public float oldCamDirX;
	
	private void tick(float delta) {
		mapMaker.tick(delta);

		for (Car car : cars.values()) {
			car.tick(delta);
		}

		for (Entity ent : ents) {
			ent.tick(delta);
		}

		cam.position.y = 3.14f; // Keep same height.
		cam.direction.y = -0.63f; // Keep same y-direction. Old: -0.59f
		cam.update();
		
		oldCamDirX = cam.direction.x;
	}

	private final int renderHeightLimit = 186; // Screen height limit for rendering when in window scale of 1. This is a
												// fix for cars that are behind camera, they might render in the
												// background above player.
	private final int renderWidthLimit = 299;
	private int currentRenderHeightLimit; // Screen position varies by window height.
	private int currentRenderWidthLimit; // Screen position varies by window width.

	private void calculateCurrentRenderLimits() {
		currentRenderHeightLimit = renderHeightLimit * Gdx.graphics.getHeight() / viewportHeight;
		currentRenderWidthLimit = renderWidthLimit * Gdx.graphics.getWidth() / viewportWidthStretched;
	}

	/**
	 * Returns wether a sprite is inside screen borders. Height is limited to
	 * background bottom position.
	 * 
	 * @param screenPos
	 * @param sprite
	 * @return
	 */
	public boolean spriteFitsInScreen(Vector3 screenPos, Sprite sprite) {
		if (screenPos.x < 0 - (sprite.getWidth() / 2) || screenPos.x > currentRenderWidthLimit + (sprite.getWidth() / 2)
				|| screenPos.y < 0 - (sprite.getHeight() / 2) || screenPos.y > currentRenderHeightLimit) {
			return false;
		} else {
			return true;
		}
	}

	public int renderedModels = 0; // set (get?) // not needed anymore= count sprite.size?
	public int renderedSprites = 0;

	@Override
	public void render(float delta) {
		renderedModels = 0;
		renderedSprites = 0;

		input(delta);
		tick(delta);

		calculateCurrentRenderLimits();

//		OVERNIGHT AWESOMENESS
//		cam.position.add(cam.direction.cpy().nor().scl(moveSpeed * delta));
//		cam.rotate(Vector3.Y, -rotSpeed * delta);
//		cam.position.add(cam.direction.cpy().crs(cam.up).nor().scl(-moveSpeed * delta));
//		END

//		Render begin
		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight);
		if (dbgUseFbo) {
			fbo.begin();
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		}

		renderMapBackgrounds();
		render3D(delta);
		renderFog();
		renderEnts(delta);

		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidthStretched, viewportHeight);
		if (dbgUseFbo) {
			if (dbgDisplayMap) { // Render map
				renderMap();
			}

			batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight);
			renderGUI();
			batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidthStretched, viewportHeight);

			fbo.end();

			viewport.apply(); // Else FBO draws over all screen.

			renderFBO();
		}

		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.act();
		stage.draw();
		viewport.apply();
		
		updateWndowTitle();
	}

	private void renderMapBackgrounds() {
		batch.begin();
		batch.draw(mapMaker.getLevelBgBack(), 0, viewportHeight - 64 + 5, (int) mapMaker.getBgBackPosX() + 192, 17,
				viewportWidthStretched, 64); // bgCurrentX +192 to center city on start.
		batch.draw(mapMaker.getLevelBgFront(), 0, viewportHeight - 64 + 5, (int) mapMaker.getBgFrontPosX() + 192, 17,
				viewportWidthStretched, 64); // bgCurrentX +192 to center city on start.
		batch.end();
	}

	private void render3D(float delta) {
//		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidthStretched, viewportHeight); // not needed?
		mdlBatch.begin(cam);
		mapMaker.render3D(mdlBatch, delta);
		mdlBatch.end();
	}

	private void renderFog() {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight);
		batch.begin();
		batch.setColor(Color.WHITE); // Use to tint color of fog for loaded level.
		batch.draw(mapMaker.getTexFog(), 0, viewportHeight - 75, 0, 0, viewportWidthStretched, 64);
		batch.setColor(Color.WHITE);
		batch.end();
	}

	Comparator<Sprite> spriteComparator = new Comparator<Sprite>() {
		public int compare(Sprite spr1, Sprite spr2) {
			if (spr1.getY() > spr2.getY()) {
				return -1;
			} else if (spr1.getY() < spr2.getY()) {
				return 1;
			} else {
				return 0;
			}
		}
	};

	private void renderEnts(float delta) {
		batch.setProjectionMatrix(cam.projection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		for (Car car : cars.values()) {
			car.render2D(batch, delta);
		}

//		cars.get(0).getPosition().cpy().sub(cars.get(1).getPosition().cpy());

		for (Entity ent : ents) {
			ent.render2D(batch, delta);
		}

//		Sort render order from y position.
		sprites.sort(spriteComparator);

		batch.begin();
		for (Sprite spr : sprites) {
			spr.draw(batch);
			renderedSprites++;
		}

		batch.end();

		sprites.clear();
	}

//	DONT USE SHAPERENDERER WHEN SHIPPING, DECREASES FPS A LOT.
	private void renderMap() {
		shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight); // This should appear
																								// stretched(?).

		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (int x = 0; x < mapMaker.colors2d.length; x++) {
			for (int y = 0; y < mapMaker.colors2d.length; y++) {
				if (mapMaker.colors2d[x][y] == mapMaker.greenCurb) {
					shapeRenderer.box(-((viewportWidth / 2) - x) + 128 + 64, (viewportHeight / 2) - y + 64, 0, 1, 1, 0);
				}
			}
		}

		shapeRenderer.setColor(Color.RED);
		shapeRenderer.box((viewportWidth / 2) + playerCar.getPosition().x,
				(viewportHeight / 2) - playerCar.getPosition().z, 0, 1, 1, 0);

		shapeRenderer.end();
		shapeRenderer.setColor(Color.WHITE); // Always reset color to white after using.
	}

	private void renderGUI() {
		if (playerCar != null) {
			batch.begin();
			font01.draw(batch, "HP: " + playerCar.getHp(), 0, 224);
			font01.draw(batch, "SPEED: " + playerCar.carCurrentSpeed + " | MAX: " + playerCar.carCurrentMaximumSpeed, 0, 224 - 8);
			font01.draw(batch, "TURBO: " + playerCar.currentTurbos + " | " + playerCar.hasTurbo, 0, 224 - 16);
			font01.draw(batch, playerCar.carCurrentSpeedKMpH + " km/h", 0, 224 - 24);
			batch.end();
		}
	}

	private void renderFBO() {
		batch.begin();
		batch.draw(fbo.getColorBufferTexture(), 0, 0, viewportWidthStretched, viewportHeight, 0, 0, 1, 1);
		batch.end();
	}

	private void updateWndowTitle() {
		Gdx.app.getGraphics().setTitle("Mode7Racer | " + Gdx.graphics.getFramesPerSecond() + " FPS | " + renderedModels
				+ " models | " + renderedSprites + " sprites");
	}

	@Override
	public void resize(int width, int height) {
		limitWindowMinimumWidthAndHeight();
		viewport.update(width, height);
	}

	private void limitWindowMinimumWidthAndHeight() {
		if (Gdx.graphics.getWidth() < viewportWidthStretched || Gdx.graphics.getHeight() < viewportHeight) {
			Gdx.graphics.setWindowedMode(viewportWidthStretched, viewportHeight);
		}
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
//		currentMap.destroy();
//		for (Entity entity : entities) {
//			entity.dispose();
//		}
	}

}
