package mysko.pilzhere.mode7racer.entities;

import java.util.Comparator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import mysko.pilzhere.mode7racer.entities.colliders.Checkpoint;
import mysko.pilzhere.mode7racer.entities.colliders.Curb;
import mysko.pilzhere.mode7racer.entities.colliders.Edge;
import mysko.pilzhere.mode7racer.entities.colliders.FinishLine;
import mysko.pilzhere.mode7racer.entities.colliders.Jump;
import mysko.pilzhere.mode7racer.entities.colliders.Recovery;
import mysko.pilzhere.mode7racer.loaders.MapData;
import mysko.pilzhere.mode7racer.loaders.MapObjectDef;
import mysko.pilzhere.mode7racer.managers.AssetsManager;
import mysko.pilzhere.mode7racer.screens.GameScreen;
import mysko.pilzhere.mode7racer.utils.MapDrawer;

public class MapMaker extends Entity {
	public ModelInstanceBB getMdlInstUG() {
		return mdlInstUG;
	}

	public Array<Checkpoint> getCheckpoints() {
		return checkpoints;
	}

	public int[][] getColors2d() {
		return colors2d;
	}

	public Array<ModelInstanceBB> getMdlInstances() {
		return mdlInstances;
	}

	public boolean isBlink() {
		return blink;
	}

	public void setBlink(boolean blink) {
		this.blink = blink;
	}

	private float bgFrontPosX = 0, bgBackPosX = 0;

	public float getBgFrontPosX() {
		return bgFrontPosX;
	}

	public void setBgFrontPosX(float bgFrontPosX) {
		this.bgFrontPosX = bgFrontPosX;
	}

	public float getBgBackPosX() {
		return bgBackPosX;
	}

	public void setBgBackPosX(float bgBackPosX) {
		this.bgBackPosX = bgBackPosX;
	}

	public Texture getLevelBgFront() {
		return levelBgFront;
	}

	public Texture getLevelBgBack() {
		return levelBgBack;
	}

	public Texture getTexFog() {
		return texFog;
	}

	private final AssetsManager assMan;

	public MapMaker(GameScreen screen) {
		super(screen, Vector3.Zero);

		assMan = screen.assMan;

		setupTextures();
	}

	private Texture texFog;

	private Texture texTileCurbL, texTileCurbR, texTileCurbS, texTileCurbN;
	private Texture texTileCurbOuterCornerNL, texTileCurbOuterCornerNR, texTileCurbOuterCornerSL,
			texTileCurbOuterCornerSR;
	private Texture texTileCurbNone, texTileCurbFull;
	private Texture texTileCurbInnerCornerNL, texTileCurbInnerCornerNR, texTileCurbInnerCornerSL,
			texTileCurbInnerCornerSR;

	private Texture texTileEdge, texTileRoad01, texTileRoad02;
//	private Texture texTileVoid;

	private Texture texTileJumpHori01, texTileJumpHoriLeft01, texTileJumpHoriRight01;

	private Texture levelBgFront, levelBgBack;

	private void setupTextures() {
		texTileCurbL = assMan.get(assMan.CURB_0101); // L
		texTileCurbR = assMan.get(assMan.CURB_1010); // R
		texTileCurbS = assMan.get(assMan.CURB_1100); // S
		texTileCurbN = assMan.get(assMan.CURB_0011); // N

		texTileCurbOuterCornerNL = assMan.get(assMan.CURB_1110); // NL
		texTileCurbOuterCornerNR = assMan.get(assMan.CURB_1101); // NR
		texTileCurbOuterCornerSL = assMan.get(assMan.CURB_1011); // SL
		texTileCurbOuterCornerSR = assMan.get(assMan.CURB_0111); // SR

		texTileCurbInnerCornerNL = assMan.get(assMan.CURB_1000); // black is NL
		texTileCurbInnerCornerNR = assMan.get(assMan.CURB_0100); // black is NR
		texTileCurbInnerCornerSL = assMan.get(assMan.CURB_0010); // black is SL
		texTileCurbInnerCornerSR = assMan.get(assMan.CURB_0001); // black is SR

		texTileCurbNone = assMan.get(assMan.CURB_0000);
		texTileCurbFull = assMan.get(assMan.CURB_1111);

		texTileEdge = assMan.get(assMan.EDGE);

		texTileRoad01 = assMan.get(assMan.ROAD_01);
		texTileRoad02 = assMan.get(assMan.ROAD_02);
//		texTileVoid = assMan.get(assMan.void01);
//		texTileVoid = assMan.get(assMan.voidAlpha01);

		texTileJumpHori01 = assMan.get(assMan.JUMP_HORIZONTAL_01);
		texTileJumpHoriLeft01 = assMan.get(assMan.JUMP_HORIZONTAL_LEFT_01);
		texTileJumpHoriRight01 = assMan.get(assMan.JUMP_HORIZONTAL_RIGHT_01);

		texFog = assMan.get(assMan.BACKGROUND_FOG);
		texFog.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);

		levelBgBack = assMan.get(assMan.BACKGROUND_01_BACK);
		levelBgFront = assMan.get(assMan.BACKGROUND_01_FRONT);
		levelBgBack.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		levelBgFront.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
	}

//	Colors
	private final int RED_VOID = Color.rgba8888(Color.RED);
	public final int GREEN_CURB = Color.rgba8888(Color.GREEN);
//	private final int forestCurbRight = Color.rgba8888(Color.FOREST);
//	private final int limeCurbSouth = Color.rgba8888(Color.LIME);
//	private final int oliveCurbNorth = Color.rgba8888(Color.OLIVE);
	private final int GRAY_ROAD = Color.rgba8888(Color.GRAY);
	private final int LIGHT_GRAY_ROAD = Color.rgba8888(Color.LIGHT_GRAY);
	private final int BLACK_EDGE = Color.rgba8888(Color.BLACK);

//	private final int YELLOW_JUMP = Color.rgba8888(Color.YELLOW);

	private int[][] colors2d;

	private final int TILE_SIZE = 16;

	private Model mdlLevel;
	private Model mdlLevelUG;
	private ModelInstanceBB mdlInstLevel;
	private Array<ModelInstanceBB> mdlInstances = new Array<ModelInstanceBB>();
	private ModelInstanceBB mdlInstUG;

//	boolean entityDrawn;

	public final Vector3 LAP_ORIGO = Vector3.Zero;
	private final Vector3 LAP_BEGIN_AND_END = new Vector3();
	float angle;

	private Array<Checkpoint> checkpoints = new Array<Checkpoint>();

	public void loadLevelFromTexture() {
//		Texture levelLevelMap = assMan.get("level02.png", Texture.class);
//		Texture levelLevelMap = assMan.get("level04.png", Texture.class);
//		Texture levelLevelMap = assMan.get("level03.png", Texture.class);
		Texture levelLevelMap = assMan.get(assMan.MAP_MUTE_CITY, Texture.class);

		final MapData MAP_DATA = new MapData();
		MAP_DATA.mapTexture = levelLevelMap;

		loadLevelFromTexture(MAP_DATA, true);
	}

	private FinishLine currentFinishLine;

	public void loadLevelFromTexture(final MapData mapData, final boolean useUnderground) {
		final long startTime = System.currentTimeMillis();
		System.out.println("STATUS: Building level.");

		final Texture levelLevelMap = mapData.mapTexture;

//		Load level texture
		Pixmap loadedLevelPixmap = prepareTextureData(levelLevelMap);

//		Generate world level texture

//		Place colors to 2dArray for each pixel from texture loaded.

		/**
		 * Need to read custom objects before painting map: As some objects such as
		 * jumps will have tiles on the floor.
		 */

//		Using mapdata to place Objects.
		if (mapData.recoveries.notEmpty()) {
			for (MapObjectDef mapObjDef : mapData.recoveries) {
				screen.getEntities().add(new Recovery(screen, new Vector3(mapObjDef.bounds.x, 0, mapObjDef.bounds.y),
						mapObjDef.bounds.x, mapObjDef.bounds.y, mapObjDef.bounds.width, mapObjDef.bounds.height));
			}
		}

		if (mapData.ramps.notEmpty()) {
			for (MapObjectDef mapObjDef : mapData.ramps) {
				screen.getEntities().add(new Jump(screen, new Vector3(mapObjDef.bounds.x, 0, mapObjDef.bounds.y),
						mapObjDef.bounds.x, mapObjDef.bounds.y, mapObjDef.bounds.width, mapObjDef.bounds.height));
			}
		}

		if (mapData.finishLine != null) {
			screen.getEntities()
					.add(currentFinishLine = new FinishLine(screen,
							new Vector3(mapData.finishLine.bounds.x, 0, mapData.finishLine.bounds.y),
							mapData.finishLine.bounds.x, mapData.finishLine.bounds.y, mapData.finishLine.bounds.width,
							mapData.finishLine.bounds.height));
			LAP_BEGIN_AND_END.set(currentFinishLine.position.cpy());

			final float degrees = MathUtils.atan2(LAP_BEGIN_AND_END.z - LAP_ORIGO.z, LAP_BEGIN_AND_END.x - LAP_ORIGO.x);
			System.err.println("finishline degrees: " + degrees);
		}

//		Make sure checkpoints are placed in order in .tmx file.
		if (mapData.checkpoints.notEmpty()) {
			for (MapObjectDef mapObjDef : mapData.checkpoints) {
				checkpoints.add(new Checkpoint(screen, new Vector3(mapObjDef.bounds.x, 0, mapObjDef.bounds.y),
						mapObjDef.bounds.x, mapObjDef.bounds.y, mapObjDef.bounds.width, mapObjDef.bounds.height));
				System.err.println("Checkpint addded!");
			}
		}

		final int LOADED_MAP_WIDTH = loadedLevelPixmap.getWidth();
		final int LOADED_MAP_HEIGHT = loadedLevelPixmap.getHeight();

		colors2d = new int[LOADED_MAP_WIDTH + 1][LOADED_MAP_HEIGHT + 1];
		for (int y = 0; y < LOADED_MAP_HEIGHT; y++) {
			for (int x = 0; x < LOADED_MAP_WIDTH; x++) {
				colors2d[x][y] = loadedLevelPixmap.getPixel(x, y);
			}
		}

//		Paint tiles to generated world fbo.

		final MapDrawer mapDrawer = new MapDrawer();

		mapDrawer.begin(LOADED_MAP_WIDTH, LOADED_MAP_HEIGHT, TILE_SIZE, TILE_SIZE);

		for (int x = 0; x < LOADED_MAP_WIDTH; x++) {
			for (int y = 0; y < LOADED_MAP_HEIGHT; y++) {

				final int COLOR = colors2d[x][y];
				final int COLOR_ABOVE = colors2d[x][y + 1];
				final int COLOR_UNDER = y > 0 ? colors2d[x][y - 1] : 0;
				final int COLOR_LEFT = x > 0 ? colors2d[x - 1][y] : 0;
				final int COLOR_RIGHT = colors2d[x + 1][y];

				boolean roadUp = false;
				boolean roadDown = false;
				boolean roadLeft = false;
				boolean roadRight = false;

				boolean curbUp = false;
				boolean curbDown = false;
				boolean curbLeft = false;
				boolean curbRight = false;

//				Check if objects exists at current pixel.

				if (COLOR == RED_VOID) {
//					mapDrawer.drawTile(x, y, texTileVoid); // No need for this if using FBO alpha.
				} else if (COLOR == BLACK_EDGE) {
					mapDrawer.drawTile(x, y, texTileEdge);
				} else if (COLOR == GREEN_CURB) {
//					Determine neighbour color
					if (COLOR_ABOVE == GRAY_ROAD || COLOR_ABOVE == LIGHT_GRAY_ROAD) {
						roadUp = true;
					} else if (COLOR_ABOVE == GREEN_CURB) {
						curbUp = true;
					}

					if (COLOR_UNDER == GRAY_ROAD || COLOR_UNDER == LIGHT_GRAY_ROAD) {
						roadDown = true;
					} else if (COLOR_UNDER == GREEN_CURB) {
						curbDown = true;
					}

					if (COLOR_LEFT == GRAY_ROAD || COLOR_LEFT == LIGHT_GRAY_ROAD) {
						roadLeft = true;
					} else if (COLOR_LEFT == GREEN_CURB) {
						curbLeft = true;
					}

					if (COLOR_RIGHT == GRAY_ROAD || COLOR_RIGHT == LIGHT_GRAY_ROAD) {
						roadRight = true;
					} else if (COLOR_RIGHT == GREEN_CURB) {
						curbRight = true;
					}

//					Paint correct tile
					if (!roadUp && !roadDown && !roadLeft && !roadRight && !curbUp && !curbDown && !curbLeft
							&& !curbRight) {
						mapDrawer.drawTile(x, y, texTileCurbFull);
					} else if (roadUp && roadDown && roadLeft && roadRight) {
						mapDrawer.drawTile(x, y, texTileCurbNone);
					} else if (roadUp && roadDown) {
						mapDrawer.drawTile(x, y, texTileCurbNone);
					} else if (roadLeft && roadRight) {
						mapDrawer.drawTile(x, y, texTileCurbNone);
					}

					else if (!roadUp && curbDown && !roadLeft && curbRight) { // Outer corners
						mapDrawer.drawTile(x, y, texTileCurbOuterCornerSL);
					} else if (!roadUp && curbDown && curbLeft && !roadRight) {
						mapDrawer.drawTile(x, y, texTileCurbOuterCornerSR);
					} else if (curbUp && !roadDown && !roadLeft && curbRight) {
						mapDrawer.drawTile(x, y, texTileCurbOuterCornerNL);
					} else if (curbUp && !roadDown && curbLeft && !roadRight) {
						mapDrawer.drawTile(x, y, texTileCurbOuterCornerNR);
					}

					else if (!roadUp && roadDown && !roadLeft && roadRight) { // Inner corners
						mapDrawer.drawTile(x, y, texTileCurbInnerCornerSL);
					} else if (!roadUp && roadDown && roadLeft && !roadRight) {
						mapDrawer.drawTile(x, y, texTileCurbInnerCornerSR);
					} else if (roadUp && !roadDown && !roadLeft && roadRight) {
						mapDrawer.drawTile(x, y, texTileCurbInnerCornerNL);
					} else if (roadUp && !roadDown && roadLeft && !roadRight) {
						mapDrawer.drawTile(x, y, texTileCurbInnerCornerNR);
					}

					else if (roadUp && !roadDown && !roadLeft && !roadRight) { // Next to road
						mapDrawer.drawTile(x, y, texTileCurbN);
					} else if (roadDown && !roadUp && !roadLeft && !roadRight) {
						mapDrawer.drawTile(x, y, texTileCurbS);
					} else if (roadLeft && !roadUp && !roadDown && !roadRight) {
						mapDrawer.drawTile(x, y, texTileCurbR);
					} else if (roadRight && !roadUp && !roadDown && !roadLeft) {
						mapDrawer.drawTile(x, y, texTileCurbL);
					} else {
						mapDrawer.drawTile(x, y, texTileCurbFull);
					}
				} else if (COLOR == GRAY_ROAD) {
					mapDrawer.drawTile(x, y, texTileRoad01);
				} else if (COLOR == LIGHT_GRAY_ROAD) {
					mapDrawer.drawTile(x, y, texTileRoad02);
				}
//				else if (color == yellowJump) {
//					mapDrawer.drawTile(x, y, texTileJumpHori01);
//				}

			}
		}

		// Draw entities
		final float MAP_CENTER_X = loadedLevelPixmap.getWidth() / 2f;
		final float MAP_CENTER_Y = loadedLevelPixmap.getHeight() / 2f;

		for (MapObjectDef mapObjDef : mapData.ramps) {
			mapDrawer.drawTiles(mapObjDef.bounds.x + MAP_CENTER_X, mapObjDef.bounds.y + MAP_CENTER_Y,
					mapObjDef.bounds.width, mapObjDef.bounds.height, mapObjDef.orientation, texTileJumpHori01,
					texTileJumpHoriLeft01, texTileJumpHoriRight01);
		}

		for (MapObjectDef mapObjDef : mapData.recoveries) {
			mapDrawer.drawTiles(mapObjDef.bounds.x + MAP_CENTER_X, mapObjDef.bounds.y + MAP_CENTER_Y,
					mapObjDef.bounds.width, mapObjDef.bounds.height, mapObjDef.orientation, texTileJumpHori01);
		}

		if (mapData.finishLine != null) {
			mapDrawer.drawTiles(mapData.finishLine.bounds.x + MAP_CENTER_X, mapData.finishLine.bounds.y + MAP_CENTER_Y,
					mapData.finishLine.bounds.width, mapData.finishLine.bounds.height, mapData.finishLine.orientation,
					texTileJumpHori01);
		}

		final Texture levelTextureGenerated = mapDrawer.end();
		levelTextureGenerated.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		System.out.println(
				"STATUS: New map is: " + levelTextureGenerated.getWidth() + " x " + levelTextureGenerated.getHeight());

//		Place objects such as Curbs and Eges by color.
		for (int x = 0; x < loadedLevelPixmap.getWidth(); x++) {
			for (int z = 0; z < loadedLevelPixmap.getHeight(); z++) {
				final int CURRENT_COLOR = loadedLevelPixmap.getPixel(x, z);
				if (CURRENT_COLOR == GREEN_CURB) { // use switch insead?
					screen.getEntities().add(new Curb(screen, new Vector3(), x - loadedLevelPixmap.getWidth() / 2,
							z - loadedLevelPixmap.getHeight() / 2, 1, 1));
//					System.err.println("Curb added at x: " + (x - loadedLevelPixmap.getWidth() / 2) + " | y: " + (z - loadedLevelPixmap.getHeight() / 2));
				} else if (CURRENT_COLOR == BLACK_EDGE) {
					screen.getEntities().add(new Edge(screen, new Vector3(), x - loadedLevelPixmap.getWidth() / 2,
							z - loadedLevelPixmap.getHeight() / 2, 1, 1));
//					System.err.println("Edge added at x: " + (x - loadedLevelPixmap.getWidth() / 2) + " | y: " + (z - loadedLevelPixmap.getHeight() / 2));
				}
			}
		}

		ModelBuilder modelBuilder = new ModelBuilder();
		mdlLevel = modelBuilder.createBox(loadedLevelPixmap.getWidth(), 0, loadedLevelPixmap.getHeight(),
				new Material(TextureAttribute.createDiffuse(levelTextureGenerated)),
				Usage.Position | Usage.TextureCoordinates);

		final Material mat = mdlLevel.materials.first();
		mat.set(new BlendingAttribute(1.0f));
//		mat.set(new FloatAttribute(FloatAttribute.AlphaTest));

		mdlInstances.add(mdlInstLevel = new ModelInstanceBB(mdlLevel));
		mdlInstLevel.transform.rotate(Vector3.Y, 90);

		if (!useUnderground) { // underground will be same layer as road. Make sure world edge tiles don't
								// dontain road tiles.
//		World edges
//		Top
			setupWorldEdgeTiles(-loadedLevelPixmap.getWidth(), loadedLevelPixmap.getHeight());
			setupWorldEdgeTiles(0, loadedLevelPixmap.getHeight());
			setupWorldEdgeTiles(loadedLevelPixmap.getWidth(), loadedLevelPixmap.getHeight());

//		Left - right
			setupWorldEdgeTiles(-loadedLevelPixmap.getWidth(), 0);
			setupWorldEdgeTiles(loadedLevelPixmap.getWidth(), 0);

//		Bottom
			setupWorldEdgeTiles(-loadedLevelPixmap.getWidth(), -loadedLevelPixmap.getHeight());
			setupWorldEdgeTiles(0, -loadedLevelPixmap.getHeight());
			setupWorldEdgeTiles(loadedLevelPixmap.getWidth(), -loadedLevelPixmap.getHeight());
		} else {
			final Texture TEX = assMan.get(assMan.BACKGROUND_UNDERGROUND_CITY_02);
			TEX.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

			mdlLevelUG = modelBuilder.createBox(256, 0, 256, new Material(TextureAttribute.createDiffuse(TEX)),
					Usage.Position | Usage.TextureCoordinates);

			mdlInstUG = new ModelInstanceBB(mdlLevelUG);
			mdlInstUG.transform.setToTranslation(new Vector3(0, -4, 0));
			mdlInstUG.transform.rotate(Vector3.Y, 90); // Have to rotate after positioning for some reason...

			final TextureAttribute TA = (TextureAttribute) mdlInstUG.materials.first().get(TextureAttribute.Diffuse);
			TA.scaleU = 2;
			TA.scaleV = 2;

//			mdlInstances.add(mdlInstUG);
		}

//		Dispose
		loadedLevelPixmap.dispose();

//		Load status
		final long endTime = System.currentTimeMillis();
		System.out.println("STATUS: Building level finished. (" + (endTime - startTime) + " ms)");
	}

	private Pixmap prepareTextureData(Texture tex) {
		final TextureData td = tex.getTextureData();
		if (!td.isPrepared()) {
			td.prepare();
		}
		return td.consumePixmap();
	}

	private void setupWorldEdgeTiles(final float x, final float y) {
		final ModelInstanceBB mdlBB = new ModelInstanceBB(mdlLevel);
		mdlBB.transform.setToTranslation(new Vector3(x, 0, y));
		mdlBB.transform.rotate(Vector3.Y, 90); // Have to rotate after positioning for some reason...
		mdlInstances.add(mdlBB);
	}

	private boolean blink;
	private Array<Car> carsRanks = new Array<Car>();

	@Override
	public void tick(final float delta) {
//		if (blink) {
//			System.out.println("Map Blink!");
//		}

//			Blink all curbs ehre!		
		for (ModelInstanceBB mdlInstBB : mdlInstances) {
			if (blink) {
				mdlInstBB.materials.get(0).set(ColorAttribute.createDiffuse(Color.RED));
			} else {
				mdlInstBB.materials.get(0).set(ColorAttribute.createDiffuse(Color.WHITE));
			}
		}

		for (Car car : screen.cars.values()) {
			carsRanks.add(car);
		}

		carsRanks.sort(sortByLapsAndCheckpointsReachedAndDistanceToNextCheckpoint);

		for (Car car : screen.cars.values()) {
			for (int i = 0; i < carsRanks.size; i++) {
				if (car.equals(carsRanks.get(i))) {
					car.setRank(i + 1);
				}
			}
		}
		
//		IMPLEMENT SPAWNPOINTS FOR MAP WITH TILED. THAT WAY WE CAN TEST IF RANKS WORK BETTER.

		carsRanks.clear();
	}
//	}

	Comparator<Car> sortByLapsAndCheckpointsReachedAndDistanceToNextCheckpoint = new Comparator<Car>() {
		public int compare(final Car car1, final Car car2) {
			if (car1.getLap() > car2.getLap()) {
//				System.out.println("a -1");
				return -1;
			} else if (car1.getLap() < car2.getLap()) {
//				System.out.println("a 1");
				return 1;
			} else { // on same lap
				if (car1.getCurrentCheckpoint() > car2.getCurrentCheckpoint()) {
//					System.out.println("b -1");
					return -1;
				} else if (car1.getCurrentCheckpoint() < car2.getCurrentCheckpoint()) {
//					System.out.println("b 1");
					return 1;
				} else { // on same checkpoint
					if (car1.getDistanceToNextCheckpoint() > car2.getDistanceToNextCheckpoint()) {
//						System.out.println("c -1");
						return 1;
					} else if (car1.getDistanceToNextCheckpoint() < car2.getDistanceToNextCheckpoint()) {
//						System.out.println("c 1");
						return -1;
					} else {
//						System.out.println("c 0");
						return 0;
					}
				}
			}
		}
	};

	public void renderUndergroundLayer(final ModelBatch batch) {
		if (screen.isVisible(screen.getCam(), mdlInstUG)) {
//			screen.getCam().far = 75;
//			screen.getCam().update();

			batch.render(mdlInstUG);
			screen.renderedModels++;
		}
	}

	@Override
	public void render3D(final ModelBatch batch, final float delta) {
		for (ModelInstanceBB mdlInstBB : mdlInstances) {
			if (screen.isVisible(screen.getCam(), mdlInstBB)) {
				batch.render(mdlInstBB);
				screen.renderedModels++;
			}
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void onHit(Object object, float delta) {
	}

	@Override
	public void render2D(SpriteBatch batch, float delta) {
	}
}
