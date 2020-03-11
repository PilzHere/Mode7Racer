package mysko.pilzhere.mode7racer.screens;

import java.util.Comparator;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.constants.GameConstants;
import mysko.pilzhere.mode7racer.entities.Car;
import mysko.pilzhere.mode7racer.entities.CarType;
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
		return this.MDL_BATCH;
	}

	public final int bgMoveSpeed = 80; // 2.135f
	public final float bgMoveSpeedBoost = 2.0f; // 1.5f
	public final float camDesiredDistFromCar = 2.3f;

	private final Mode7Racer game;
	public final AssetsManager assMan; // get
	private final SpriteBatch BATCH;
	private final ShapeRenderer SHAPE_RENDERER;
	private final ModelBatch MDL_BATCH;

	private final FrameBuffer FBO;

	private PerspectiveCamera cam;
	private Viewport viewport; // get
//	public final int viewportWidth = 256;
//	public final int viewportHeight = 224;
//	public final int viewportWidthStretched = 299; // As displayed on TV (stretched) from SNES output.

	public Car playerCar; // test
	private MapMaker mapMaker;
	private Array<Entity> ents = new Array<Entity>();
	public Array<Sprite> sprites = new Array<Sprite>(); // get
	public HashMap<Integer, Car> cars = new HashMap<Integer, Car>();

	private BitmapFont font01;

	private Stage stage;
	private GameHUD hud;

	private Music backgroundMusic;
	
	public GameScreen(Mode7Racer game) {
		this.game = game;
		this.assMan = this.game.getAssMan();
		this.BATCH = this.game.getBatch();
		this.SHAPE_RENDERER = this.game.getShapeRenderer();
		this.MDL_BATCH = this.game.getModelBatch();
		this.FBO = this.game.getFb01();

//		font01 = assMan.get(assMan.font01_16);
		font01 = assMan.get(assMan.FONT_01_08);

		final int FOV = 78; // 33 or 80
		cam = new PerspectiveCamera(FOV, 256, 224);
		cam.position.set(new Vector3(0, 25, 2.3f)); // old was 0, 0, 2.3f
		cam.lookAt(new Vector3(0, 0, 2.3f)); // Wasnt here before intro...
		cam.near = 0.001f;
		cam.far = 40f;

		viewport = new FitViewport(GameConstants.VIEWPORT_WIDTH_STRETCHED, GameConstants.VIEWPORT_HEIGHT, cam);

		mapMaker = new MapMaker(this);

		// old map loading
//		currentMap.loadLevelFromTexture();

		// new map loading
		final MapData MAP_DATA = new MapLoader().load(assMan.get(assMan.MAP_SILENCE, TiledMap.class));
		mapMaker.loadLevelFromTexture(MAP_DATA, true);

//		cars.put(0, new Car(this, new Vector3(0, 0, 0), true, false));
//		cars.put(1, new Car(this, new Vector3(0, 0, -2), false, true));
//
//		playerCar = cars.get(0);

		stage = new Stage(new FitViewport(GameConstants.VIEWPORT_WIDTH_STRETCHED, GameConstants.VIEWPORT_HEIGHT));
		stage.addActor(hud = new GameHUD(game, game.getSkin()));
		
		hud.spawnTitle("MUTE CITY I"); // TODO dynamic title
		
//		play intro music
		updateBackgroundMusic(assMan.MUSIC_ZOOM, false);
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

	private boolean debugUseFbo = true;
	private boolean debugDisplayMap = false;

	private void input(float delta) {

		final ControllerBase controller = game.INPUTS.getController(0);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			debugUseFbo = !debugUseFbo;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
			debugDisplayMap = !debugDisplayMap;
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			game.setGameVolumeMute(!game.isGameVolumeMute());
		}

		if (hud.isSettingsOpened())
			return;

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

	private boolean havePlacedVehicles;
	private Vector3 camStartPosition = new Vector3();
	private boolean leftStartPosition;
	private final float CAM_DIR_Y_PLAY = -0.63f;
	private final float CAM_POS_Y_PLAY = 3.14f;
	private final float CAM_INTRO_MOVE_DOWN_SPEED = 12f;
	private final float CAM_INTRO_DIR_Z_DEST = -1.6f;
	private final float CAM_INTRO_DIR_Z_SPEED = 1.0f;
	private boolean camShowIntro = true;
	private boolean camIntroPosYReached;
	private boolean camIntroDirectionZReached;

	private boolean backgroundMusicSet = false;
	private boolean playFinishMusic = false;
	
	private void tick(float delta) {
		backgroundMusic.setVolume(game.getGameVolume());
		
		mapMaker.tick(delta);

		for (Car car : cars.values()) {
			car.tick(delta);
		}

		for (Entity ent : ents) {
			ent.tick(delta);
		}

		if (!camShowIntro) {
			if (!havePlacedVehicles) {
				placeVehicles();
			}

			cam.position.y = CAM_POS_Y_PLAY; // Keep same height.

			if (!leftStartPosition) {
				if (!cam.position.idt(camStartPosition.cpy())) { // Adjust only if NOT in start position at spawn.
					cam.direction.y = CAM_DIR_Y_PLAY; // Keep same y-direction.
					leftStartPosition = true;
				}
			} else {
				cam.direction.y = CAM_DIR_Y_PLAY; // Keep same y-direction.
			}
			
			if (!backgroundMusicSet && !playFinishMusic) {
				updateBackgroundMusic(assMan.MUSIC_SILENCE, true);
				
				backgroundMusicSet = true;
			}
		} else {
			if (!camIntroPosYReached) {
				camIntroMoveCamToDestinationY(delta);
			} else {
				if (!camIntroDirectionZReached) {
					camIntroMoveCamToDirectionZ(delta);
				}
			}

			if (camIntroPosYReached && camIntroDirectionZReached) {
				renderFog = true;
				camShowIntro = false;
			}
		}
		
		if (!playFinishMusic) {
			if (playerCar != null) {
				if (playerCar.getLap() >= 3) {
					updateBackgroundMusic(assMan.MUSIC_ENDING_THEME, true);
					playFinishMusic = true;
				}
			}
		}

//		cam.update();
	}
	
	private void updateBackgroundMusic(final String music, final boolean loop) {
		if (backgroundMusic != null)
			backgroundMusic.stop();
		
		backgroundMusic = assMan.get(music);
		backgroundMusic.setVolume(game.getGameVolume());
		backgroundMusic.setLooping(loop);
		backgroundMusic.play();
	}

	private void camIntroMoveCamToDirectionZ(float delta) {
		if (cam.direction.z > CAM_INTRO_DIR_Z_DEST) {
			cam.direction.z -= CAM_INTRO_DIR_Z_SPEED * delta;
		} else if (cam.direction.z < CAM_INTRO_DIR_Z_DEST) {
			cam.direction.z = CAM_INTRO_DIR_Z_DEST;

			camStartPosition.set(cam.position.cpy());

			camIntroDirectionZReached = true;
		}
	}

	private void camIntroMoveCamToDestinationY(float delta) {
		if (cam.position.y > CAM_POS_Y_PLAY) {
			cam.position.y -= CAM_INTRO_MOVE_DOWN_SPEED * delta;
		} else if (cam.position.y < CAM_POS_Y_PLAY) {
			cam.position.y = CAM_POS_Y_PLAY;
			camIntroPosYReached = true;
		}
	}

	private void placeVehicles() {
		cars.put(0, new Car(this, new Vector3(0, 0, 0), false, CarType.BLUE));
		cars.put(1, new Car(this, new Vector3(0, 0, -2), true, CarType.YELLOW));

		playerCar = cars.get(0);

		havePlacedVehicles = true;
	}

	private final int RENDER_HEIGHT_LIMIT = 186; // Screen height limit for rendering when in window scale of 1. This is a
												// fix for cars that are behind camera, they might render in the
												// background above player.
	private final int RENDER_WIDTH_LIMIT = 299;
	private int currentRenderHeightLimit; // Screen position varies by window height.
	private int currentRenderWidthLimit; // Screen position varies by window width.

	private void calculateCurrentRenderLimits() {
		currentRenderHeightLimit = RENDER_HEIGHT_LIMIT * Gdx.graphics.getHeight() / GameConstants.VIEWPORT_HEIGHT;
		currentRenderWidthLimit = RENDER_WIDTH_LIMIT * Gdx.graphics.getWidth() / GameConstants.VIEWPORT_WIDTH_STRETCHED;
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

	public int renderedModels = 0; // set (get?)
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
		BATCH.getProjectionMatrix().setToOrtho2D(0, 0, GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
		if (debugUseFbo) {
			FBO.begin();
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		}

		renderMapBackgrounds();
		render3D(delta);
		renderFog();
		renderEnts(delta);

		BATCH.getProjectionMatrix().setToOrtho2D(0, 0, GameConstants.VIEWPORT_WIDTH_STRETCHED,
				GameConstants.VIEWPORT_HEIGHT);
		if (debugUseFbo) {
			if (debugDisplayMap) { // Render map
				renderMap();
			}

			BATCH.getProjectionMatrix().setToOrtho2D(0, 0, GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
			renderGUI();
			BATCH.getProjectionMatrix().setToOrtho2D(0, 0, GameConstants.VIEWPORT_WIDTH_STRETCHED,
					GameConstants.VIEWPORT_HEIGHT);

			FBO.end();

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
		BATCH.begin();
		BATCH.draw(mapMaker.getLevelBgBack(), 0, GameConstants.VIEWPORT_HEIGHT - 64 + 5,
				(int) mapMaker.getBgBackPosX() + 192, 17, GameConstants.VIEWPORT_WIDTH_STRETCHED, 64); // bgCurrentX
																										// +192 to
																										// center city
																										// on start.
		BATCH.draw(mapMaker.getLevelBgFront(), 0, GameConstants.VIEWPORT_HEIGHT - 64 + 5,
				(int) mapMaker.getBgFrontPosX() + 192, 17, GameConstants.VIEWPORT_WIDTH_STRETCHED, 64); // bgCurrentX
																										// +192 to
																										// center city
																										// on start.
		BATCH.end();
	}

	private TextureAttribute ta;

	private void render3D(float delta) {
//		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidthStretched, viewportHeight); // not needed?

		if (playerCar != null && mapMaker.getMdlInstUG() != null) {
			mapMaker.getMdlInstUG().transform.setToTranslation(
					(playerCar.getPosition().cpy().sub(0, playerCar.getPosition().y, 0).sub(0, 4, 0)));

			ta = (TextureAttribute) mapMaker.getMdlInstUG().materials.first().get(TextureAttribute.Diffuse);
			ta.offsetU = playerCar.getPosition().z / 64; // Should be 48 but 32 makes it feel faster.
			ta.offsetV = -playerCar.getPosition().x / 64; // Should be 48 but 32 makes it feel faster.
		}

		if (mapMaker.getMdlInstUG() != null) {
			if (camShowIntro) {
				if (cam.direction.z < 0) { // Else face-fighting modelbatches.
					cam.far = 90;
				}
			} else {
				cam.far = 90;
			}

			cam.update();

			MDL_BATCH.begin(cam);
			mapMaker.renderUndergroundLayer(MDL_BATCH);
			MDL_BATCH.end();

		}

		cam.far = 40;
		cam.update();

		MDL_BATCH.begin(cam);
		mapMaker.render3D(MDL_BATCH, delta);
		MDL_BATCH.end();
	}

	private boolean renderFog;

	private void renderFog() {
		if (renderFog) {
			BATCH.getProjectionMatrix().setToOrtho2D(0, 0, GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
			BATCH.begin();
			BATCH.setColor(Color.WHITE); // Use to tint color of fog for loaded level.
			BATCH.draw(mapMaker.getTexFog(), 0, GameConstants.VIEWPORT_HEIGHT - 75, 0, 0,
					GameConstants.VIEWPORT_WIDTH_STRETCHED, 64);
			BATCH.end();
		}
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
		BATCH.setProjectionMatrix(cam.projection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
//		batch.setProjectionMatrix(cam.projection.setToOrtho2D(0, 0, GameConstants.VIEWPORT_WIDTH_STRETCHED, GameConstants.VIEWPORT_HEIGHT));

		if (!cars.isEmpty()) {
			for (Car car : cars.values()) {
				car.render2D(BATCH, delta);
			}
		}

		if (ents.notEmpty()) {
			for (Entity ent : ents) {
				ent.render2D(BATCH, delta);
			}
		}

		if (sprites.notEmpty()) {
//			Sort render order from y position.
			sprites.sort(spriteComparator);

			BATCH.begin();
			for (Sprite spr : sprites) {
				spr.draw(BATCH);
				renderedSprites++;
			}

			BATCH.end();

			sprites.clear();
		}
	}

//	DONT USE SHAPERENDERER WHEN SHIPPING, DECREASES FPS A LOT.
	private void renderMap() {
		SHAPE_RENDERER.getProjectionMatrix().setToOrtho2D(0, 0, GameConstants.VIEWPORT_WIDTH,
				GameConstants.VIEWPORT_HEIGHT); // This should appear
		// stretched(?).

		SHAPE_RENDERER.setColor(Color.WHITE);
		SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
		for (int x = 0; x < mapMaker.getColors2d().length; x++) {
			for (int y = 0; y < mapMaker.getColors2d().length; y++) {
				if (mapMaker.getColors2d()[x][y] == mapMaker.GREEN_CURB) {
					SHAPE_RENDERER.box(-((GameConstants.VIEWPORT_WIDTH / 2) - x) + 128 + 64,
							(GameConstants.VIEWPORT_HEIGHT / 2) - y + 64, 0, 1, 1, 0);
				}
			}
		}

		SHAPE_RENDERER.setColor(Color.RED);
		SHAPE_RENDERER.box((GameConstants.VIEWPORT_WIDTH / 2) + playerCar.getPosition().x,
				(GameConstants.VIEWPORT_HEIGHT / 2) - playerCar.getPosition().z, 0, 1, 1, 0);

		SHAPE_RENDERER.end();
		SHAPE_RENDERER.setColor(Color.WHITE); // Always reset color to white after using.
	}

	private void renderGUI() {
		if (playerCar != null) {
			BATCH.begin();
			font01.draw(BATCH, "HP: " + playerCar.getHp(), 0, 224);
			font01.draw(BATCH, "SPEED: " + playerCar.getCurrentSpeed() + " | MAX: " + playerCar.getCurrentMaximumSpeed(), 0,
					224 - 8);
			font01.draw(BATCH, "TURBO: " + playerCar.getCurrentTurbos() + " | " + playerCar.getHasTurbo(), 0, 224 - 16);
			font01.draw(BATCH, playerCar.getCurrentSpeedKMpH() + " km/h", 0, 224 - 24);
			font01.draw(BATCH, "LAPS: " + playerCar.getLap(), 0, 224 - 32);
			font01.draw(BATCH, "CHECKPOINT: " + playerCar.getCurrentCheckpoint(), 0, 224 - 40);
			font01.draw(BATCH, "RANK: " + playerCar.getRank(), 0, 224 - 48);
			BATCH.end();
		}
	}

	private void renderFBO() {
		BATCH.begin();
		BATCH.draw(FBO.getColorBufferTexture(), 0, 0, GameConstants.VIEWPORT_WIDTH_STRETCHED,
				GameConstants.VIEWPORT_HEIGHT, 0, 0, 1, 1);
		BATCH.end();
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
		if (Gdx.graphics.getWidth() < GameConstants.VIEWPORT_WIDTH_STRETCHED
				|| Gdx.graphics.getHeight() < GameConstants.VIEWPORT_HEIGHT) {
			Gdx.graphics.setWindowedMode(GameConstants.VIEWPORT_WIDTH_STRETCHED, GameConstants.VIEWPORT_HEIGHT);
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
