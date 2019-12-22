package mysko.pilzhere.mode7racer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.BaseAnimationController.Transform;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.entities.Entity;
import mysko.pilzhere.mode7racer.entities.ModelInstanceBB;

public class GameScreen implements Screen {
	private final Mode7Racer game;
	private final AssetManager assMan;
	private final SpriteBatch batch;
	private final ShapeRenderer shapeRenderer;
	private final ModelBatch mdlBatch;

	private final FrameBuffer fbo;

	public PerspectiveCamera cam;
//	public OrthographicCamera cam2d;
	private Viewport viewport;
	private final int viewportWidth = 256;
	private final int viewportHeight = 224;
	private final int viewportWidthStretched = 299; // As displayed stretched from SNES output.

	private final long nanoToMs = 1000000;

	private Texture bgGrid;
	private Texture bgSample;
	public Texture texTileCurbL;
	public Texture texTileCurbR;
	public Texture texTileCurbS;
	public Texture texTileCurbN;

	public Texture texTileCurbOuterCornerNL;
	public Texture texTileCurbOuterCornerNR;
	public Texture texTileCurbOuterCornerSL;
	public Texture texTileCurbOuterCornerSR;
	public Texture texTileCurbNone;
	public Texture texTileCurbFull;
	public Texture texTileCurbInnerCornerNL;
	public Texture texTileCurbInnerCornerNR;
	public Texture texTileCurbInnerCornerSL;
	public Texture texTileCurbInnerCornerSR;

	public Texture texTileRoad01;
	public Texture texTileRoad02;
	public Texture texTileVoid;

	private Texture texFog;
	
	private Texture texCar01BigBack01;

	public Model mdlTile;
	private ModelInstance mdlInstTile;

	private Texture levelBgFront;
	private Texture levelBgBack;
	private float bgFrontPosX = 0f;
	private float bgBackPosX = 0f;

	private Model mdlLevel;
	public ModelInstance mdlInstLevel;
	
	private Model mdlCar;
	private ModelInstance mdlInstCar;
	
	private Sprite sprCar;

	public Array<Entity> entities = new Array<Entity>();

//	private BoundingBox[][] boxes = new BoundingBox[128][128];

	public ModelBatch getModelBatch() {
		return this.mdlBatch;
	}

	public GameScreen(Mode7Racer game) {
		this.game = game;
		this.assMan = this.game.getAssMan();
		this.batch = this.game.getBatch();
		this.shapeRenderer = this.game.getShapeRenderer();
		this.mdlBatch = this.game.getModelBatch();
		this.fbo = this.game.getFb01();

		final int fOV = 78; // 33 or 80
		cam = new PerspectiveCamera(fOV, 256, 224);
		cam.position.set(new Vector3(0, 0, 1.8f));
//		cam.lookAt(new Vector3(0, 1, 1));
		cam.near = 0.001f;
		cam.far = 40f;

//		cam2d = new OrthographicCamera(256, 224);
//		cam2d.position.set(new Vector3(0,0,0));
//		cam2d.near = 0.001f;
//		cam2d.far = 10;

		viewport = new FitViewport(viewportWidthStretched, viewportHeight, cam);

		bgGrid = assMan.get("bg01.png", Texture.class);
		bgSample = assMan.get("sample256.png", Texture.class);

		texTileCurbL = assMan.get("curb0101.png", Texture.class); // L
		texTileCurbR = assMan.get("curb1010.png", Texture.class); // R
		texTileCurbS = assMan.get("curb1100.png", Texture.class); // S
		texTileCurbN = assMan.get("curb0011.png", Texture.class); // N

		texTileCurbOuterCornerNL = assMan.get("curb1110.png", Texture.class); // NL
		texTileCurbOuterCornerNR = assMan.get("curb1101.png", Texture.class); // NR
		texTileCurbOuterCornerSL = assMan.get("curb1011.png", Texture.class); // SL
		texTileCurbOuterCornerSR = assMan.get("curb0111.png", Texture.class); // SR

		texTileCurbInnerCornerNL = assMan.get("curb1000.png", Texture.class); // black is NL
		texTileCurbInnerCornerNR = assMan.get("curb0100.png", Texture.class); // black is NR
		texTileCurbInnerCornerSL = assMan.get("curb0010.png", Texture.class); // black is SL
		texTileCurbInnerCornerSR = assMan.get("curb0001.png", Texture.class); // black is SR

		texTileCurbNone = assMan.get("curb0000.png", Texture.class);
		texTileCurbFull = assMan.get("curb1111.png", Texture.class);

		texTileRoad01 = assMan.get("road01.png", Texture.class);
		texTileRoad02 = assMan.get("road02.png", Texture.class);
		texTileVoid = assMan.get("void.png", Texture.class);
		texFog = assMan.get("fog01.png", Texture.class);
		texFog.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		
		texCar01BigBack01 = assMan.get("car01BigBack01.png", Texture.class);
		
		mdlCar = assMan.get("car01Big.g3db", Model.class);

		mdlTile = assMan.get("grassPlane.g3db", Model.class);
//		mdlInstTile = new ModelInstance(mdlTile);
//		mdlInstTile.transform.setTranslation(0, 0, -1);

		loadLevelFromTexture();

		levelBgBack = assMan.get("levelBg01Back.png", Texture.class);
		levelBgFront = assMan.get("levelBg01Front.png", Texture.class);
		levelBgBack.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		levelBgFront.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
	}

//	private void loadLevelFromText() {
//		FileHandle handle = Gdx.files.local("level01.txt");
//		String text = handle.readString();
//		String wordsArray[] = text.split("\\r?\\n");
//
////		Check for level line length. Should be same length.
////		TODO: Make sure it looks for EVEN LINE LENGTH!
//		for (int i = 0; i < wordsArray.length; i++) {
////			System.out.println(wordsArray[i]);
//			if (wordsArray[i].length() != wordsArray[0].length()) {
//				System.err.println("Error: Level: Lines not same length. (Line: " + (i + 1) + ")");
//			}
//		}
//
//		final int levelXOffset = wordsArray[0].length() / 2;
//		final int levelZOffset = wordsArray.length / 2;
//
//		final char death = 'X';
//		final char road = '#';
//		final char curb = 'C';
//
//		int hori = -1;
//		int vert = -1;
//
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
//	}

//	private void loadLevelFromTexture() {
//		Texture levelMap = assMan.get("level01.png", Texture.class);
//		TextureData levelMapData = levelMap.getTextureData();
//		if (!levelMapData.isPrepared()) {
//			levelMapData.prepare();
//		}
//
//		Pixmap pixmap = levelMapData.consumePixmap();
//
////		Place tiles for each char at line FROM PIXMAP
//		for (int x = 0; x < pixmap.getWidth(); x++) {
//			for (int y = 0; y < pixmap.getHeight(); y++) {
//				final int pix = pixmap.getPixel(x, y);
//				System.out.println(pix);
//
//				entities.add(new Tile(this, new Vector3((x - (pixmap.getWidth() / 2)), 0, y - (pixmap.getHeight() / 2)),
//						pix));
//			}
//
//		}
//	}

	private final int tileSize = 16;

//	Colors
	private final int redVoid01 = Color.rgba8888(Color.RED);

	private final int greenCurb = Color.rgba8888(Color.GREEN);
//	private final int forestCurbRight = Color.rgba8888(Color.FOREST);
//	private final int limeCurbSouth = Color.rgba8888(Color.LIME);
//	private final int oliveCurbNorth = Color.rgba8888(Color.OLIVE);

	private final int greyRoad = Color.rgba8888(Color.GRAY);
	private final int lightGreyRoad = Color.rgba8888(Color.LIGHT_GRAY);

	private int[][] array2d;
	
	Vector3 car3dDirection = new Vector3();
	Vector3 car3dPosition = new Vector3();
//	Transform car3dTrans = new Transform();	

	private void loadLevelFromTexture() {
		long startTime = System.nanoTime();
		System.out.println("STATUS: Building level.");

		Texture levelLevelMap = assMan.get("level02.png", Texture.class);
		TextureData loadedlevelMapData = levelLevelMap.getTextureData();
		if (!loadedlevelMapData.isPrepared()) {
			loadedlevelMapData.prepare();
		}

		Pixmap loadedLevelPixmap, generatedLevelPixmap, pixTileCurbL, pixTileCurbR, pixTileCurbN, pixTileCurbS,
				pixTileRoad01, pixTileRoad02, pixTileVoid01, pixTileCurbOuterCornerNL, pixTileCurbOuterCornerNR,
				pixTileCurbOuterCornerSL, pixTileCurbOuterCornerSR, pixTileCurbInnerCornerNL, pixTileCurbInnerCornerNR,
				pixTileCurbInnerCornerSL, pixTileCurbInnerCornerSR, pixTileCurbNone, pixTileCurbFull;

		TextureData tileCurbLData, tileCurbRData, tileCurbSData, tileCurbNData, tileRoad01Data, tileRoad02Data,
				tileVoidData, tileCurbOuterCornerNLData, tileCurbOuterCornerNRData, tileCurbOuterCornerSLData,
				tileCurbOuterCornerSRData, tileCurbInnerCornerNLData, tileCurbInnerCornerNRData,
				tileCurbInnerCornerSLData, tileCurbInnerCornerSRData, tileCurbNoneData, tileCurbFullData;

		loadedLevelPixmap = loadedlevelMapData.consumePixmap();

		generatedLevelPixmap = new Pixmap(loadedLevelPixmap.getWidth() * tileSize,
				loadedLevelPixmap.getHeight() * tileSize, Format.RGB888);
		final int loadedlevelTextureWidth = loadedLevelPixmap.getWidth();
		final int loadedlevelTextureHeight = loadedLevelPixmap.getHeight();

		tileCurbLData = texTileCurbL.getTextureData();
		if (!tileCurbLData.isPrepared()) {
			tileCurbLData.prepare();
		}

		pixTileCurbL = tileCurbLData.consumePixmap();

		tileCurbRData = texTileCurbR.getTextureData();
		if (!tileCurbRData.isPrepared()) {
			tileCurbRData.prepare();
		}

		pixTileCurbR = tileCurbRData.consumePixmap();

		tileCurbSData = texTileCurbS.getTextureData();
		if (!tileCurbSData.isPrepared()) {
			tileCurbSData.prepare();
		}

		pixTileCurbS = tileCurbSData.consumePixmap();

		tileCurbNData = texTileCurbN.getTextureData();
		if (!tileCurbNData.isPrepared()) {
			tileCurbNData.prepare();
		}

		pixTileCurbN = tileCurbNData.consumePixmap();

		tileRoad01Data = texTileRoad01.getTextureData();
		if (!tileRoad01Data.isPrepared()) {
			tileRoad01Data.prepare();
		}

		pixTileRoad01 = tileRoad01Data.consumePixmap();

		tileRoad02Data = texTileRoad02.getTextureData();
		if (!tileRoad02Data.isPrepared()) {
			tileRoad02Data.prepare();
		}

		pixTileRoad02 = tileRoad02Data.consumePixmap();

		tileVoidData = texTileVoid.getTextureData();
		if (!tileVoidData.isPrepared()) {
			tileVoidData.prepare();
		}

		pixTileVoid01 = tileVoidData.consumePixmap();

		tileCurbOuterCornerNLData = texTileCurbOuterCornerNL.getTextureData();
		if (!tileCurbOuterCornerNLData.isPrepared()) {
			tileCurbOuterCornerNLData.prepare();
		}

		pixTileCurbOuterCornerNL = tileCurbOuterCornerNLData.consumePixmap();

		tileCurbOuterCornerNRData = texTileCurbOuterCornerNR.getTextureData();
		if (!tileCurbOuterCornerNRData.isPrepared()) {
			tileCurbOuterCornerNRData.prepare();
		}

		pixTileCurbOuterCornerNR = tileCurbOuterCornerNRData.consumePixmap();

		tileCurbOuterCornerSLData = texTileCurbOuterCornerSL.getTextureData();
		if (!tileCurbOuterCornerSLData.isPrepared()) {
			tileCurbOuterCornerSLData.prepare();
		}

		pixTileCurbOuterCornerSL = tileCurbOuterCornerSLData.consumePixmap();

		tileCurbOuterCornerSRData = texTileCurbOuterCornerSR.getTextureData();
		if (!tileCurbOuterCornerSRData.isPrepared()) {
			tileCurbOuterCornerSRData.prepare();
		}

		pixTileCurbOuterCornerSR = tileCurbOuterCornerSRData.consumePixmap();

		tileCurbInnerCornerNLData = texTileCurbInnerCornerNL.getTextureData();
		if (!tileCurbInnerCornerNLData.isPrepared()) {
			tileCurbInnerCornerNLData.prepare();
		}

		pixTileCurbInnerCornerNL = tileCurbInnerCornerNLData.consumePixmap();

		tileCurbInnerCornerNRData = texTileCurbInnerCornerNR.getTextureData();
		if (!tileCurbInnerCornerNRData.isPrepared()) {
			tileCurbInnerCornerNRData.prepare();
		}

		pixTileCurbInnerCornerNR = tileCurbInnerCornerNRData.consumePixmap();

		tileCurbInnerCornerSLData = texTileCurbInnerCornerSL.getTextureData();
		if (!tileCurbInnerCornerSLData.isPrepared()) {
			tileCurbInnerCornerSLData.prepare();
		}

		pixTileCurbInnerCornerSL = tileCurbInnerCornerSLData.consumePixmap();

		tileCurbInnerCornerSRData = texTileCurbInnerCornerSR.getTextureData();
		if (!tileCurbInnerCornerSRData.isPrepared()) {
			tileCurbInnerCornerSRData.prepare();
		}

		pixTileCurbInnerCornerSR = tileCurbInnerCornerSRData.consumePixmap();

		tileCurbFullData = texTileCurbFull.getTextureData();
		if (!tileCurbFullData.isPrepared()) {
			tileCurbFullData.prepare();
		}

		pixTileCurbFull = tileCurbFullData.consumePixmap();

		tileCurbNoneData = texTileCurbNone.getTextureData();
		if (!tileCurbNoneData.isPrepared()) {
			tileCurbNoneData.prepare();
		}

		pixTileCurbNone = tileCurbNoneData.consumePixmap();

		array2d = new int[loadedlevelTextureWidth + 1][loadedlevelTextureHeight + 1];

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

				final int colorAbove = array2d[i / tileSize][(j / tileSize) + 1];

				int colorUnder = 0;
				if ((j / tileSize) - 1 >= 0) {
					colorUnder = array2d[i / tileSize][(j / tileSize) - 1];
				}

				int colorLeft = 0;
				if ((i / tileSize) - 1 >= 0) {
					colorLeft = array2d[(i / tileSize) - 1][j / tileSize];
				}

				final int colorRight = array2d[(i / tileSize) + 1][j / tileSize];

				boolean roadUp = false;
				boolean roadDown = false;
				boolean roadLeft = false;
				boolean roadRight = false;

				boolean curbUp = false;
				boolean curbDown = false;
				boolean curbLeft = false;
				boolean curbRight = false;

				if (color == redVoid01) {
					generatedLevelPixmap.drawPixel(i, j, pixTileVoid01.getPixel(tileX, tileY));
				} else if (color == greenCurb) {
//					Determine neighbour color
					if (colorAbove == greyRoad || colorAbove == lightGreyRoad) {
						roadUp = true;
					} else if (colorAbove == greenCurb) {
						curbUp = true;
					}

					if (colorUnder == greyRoad || colorUnder == lightGreyRoad) {
						roadDown = true;
					} else if (colorUnder == greenCurb) {
						curbDown = true;
					}

					if (colorLeft == greyRoad || colorLeft == lightGreyRoad) {
						roadLeft = true;
					} else if (colorLeft == greenCurb) {
						curbLeft = true;
					}

					if (colorRight == greyRoad || colorRight == lightGreyRoad) {
						roadRight = true;
					} else if (colorRight == greenCurb) {
						curbRight = true;
					}

//					Paint correct tile
					if (!roadUp && !roadDown && !roadLeft && !roadRight && !curbUp && !curbDown && !curbLeft
							&& !curbRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbFull.getPixel(tileX, tileY));
					} else if (roadUp && roadDown && roadLeft && roadRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbNone.getPixel(tileX, tileY));
					} else if (roadUp && roadDown) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbNone.getPixel(tileX, tileY));
					} else if (roadLeft && roadRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbNone.getPixel(tileX, tileY));
					}

					else if (!roadUp && curbDown && !roadLeft && curbRight) { // Outer corners
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbOuterCornerSL.getPixel(tileX, tileY));
					} else if (!roadUp && curbDown && curbLeft && !roadRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbOuterCornerSR.getPixel(tileX, tileY));
					} else if (curbUp && !roadDown && !roadLeft && curbRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbOuterCornerNL.getPixel(tileX, tileY));
					} else if (curbUp && !roadDown && curbLeft && !roadRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbOuterCornerNR.getPixel(tileX, tileY));
					}

					else if (!roadUp && roadDown && !roadLeft && roadRight) { // Inner corners
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbInnerCornerSL.getPixel(tileX, tileY));
					} else if (!roadUp && roadDown && roadLeft && !roadRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbInnerCornerSR.getPixel(tileX, tileY));
					} else if (roadUp && !roadDown && !roadLeft && roadRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbInnerCornerNL.getPixel(tileX, tileY));
					} else if (roadUp && !roadDown && roadLeft && !roadRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbInnerCornerNR.getPixel(tileX, tileY));
					}

					else if (roadUp && !roadDown && !roadLeft && !roadRight) { // Next to road
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbN.getPixel(tileX, tileY));
					} else if (roadDown && !roadUp && !roadLeft && !roadRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbS.getPixel(tileX, tileY));
					} else if (roadLeft && !roadUp && !roadDown && !roadRight) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbR.getPixel(tileX, tileY));
					} else if (roadRight && !roadUp && !roadDown && !roadLeft) {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbL.getPixel(tileX, tileY));
					} else {
						generatedLevelPixmap.drawPixel(i, j, pixTileCurbFull.getPixel(tileX, tileY));
					}
//						boxes[i / tileSize][j / tileSize] = new BoundingBox(new Vector3(), new Vector3(1, 0, 1));
//						System.err.println("BB ADDED!");
//					}

				} else if (color == greyRoad) {
//					System.out.println("GREY");
					generatedLevelPixmap.drawPixel(i, j, pixTileRoad01.getPixel(tileX, tileY));
				} else if (color == lightGreyRoad) {
					generatedLevelPixmap.drawPixel(i, j, pixTileRoad02.getPixel(tileX, tileY));
				}
//				else {
//					generatedLevelPixmap.drawPixel(i, j, Color.YELLOW.toIntBits());
//					System.out.println("OTHER");
//				}
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
//		mdlInstLevel.transform.rotate(Vector3.X, 180);
//		mdlInstLevel.transform.rotate(Vector3.Z, 0);
		
//		mdlCar = modelBuilder.createBox((texCar01BigBack01.getHeight() / 16) / 2, (texCar01BigBack01.getWidth() / 16) / 2, 0,
//				new Material(TextureAttribute.createDiffuse(texCar01BigBack01)),
//				Usage.Position | Usage.TextureCoordinates);
		mdlInstCar = new ModelInstance(mdlCar);
		mdlInstCar.materials.first().set(new BlendingAttribute(1.0f));
		mdlInstCar.materials.first().set(new FloatAttribute(FloatAttribute.AlphaTest));
		
		mdlInstCar.transform.setToTranslation(car3dPosition.cpy());
		
//		car3dDirection = new Vector3(0, 0, 1).rot(mdlInstCar.transform);
		
		sprCar = new Sprite(texCar01BigBack01);
		sprCar.setPosition(0, 0);
		

		loadedLevelPixmap.dispose();
		generatedLevelPixmap.dispose();

		pixTileCurbL.dispose();
		pixTileCurbR.dispose();
		pixTileCurbS.dispose();
		pixTileCurbN.dispose();
		pixTileRoad01.dispose();
		pixTileRoad02.dispose();
		pixTileVoid01.dispose();

		pixTileCurbOuterCornerNL.dispose();
		pixTileCurbOuterCornerNR.dispose();
		pixTileCurbOuterCornerSL.dispose();
		pixTileCurbOuterCornerSR.dispose();

		pixTileCurbInnerCornerNL.dispose();
		pixTileCurbInnerCornerNR.dispose();
		pixTileCurbInnerCornerSL.dispose();
		pixTileCurbInnerCornerSR.dispose();

		pixTileCurbFull.dispose();
		pixTileCurbNone.dispose();

		long endTime = System.nanoTime();
		System.out.println("STATUS: Building level finished. (" + (endTime - startTime) / nanoToMs + " ms)");
	}

	private final Vector3 currentModelPos = new Vector3();

	public boolean isVisible(final Camera cam, final ModelInstanceBB modelInst) {
		modelInst.transform.getTranslation(currentModelPos);
		currentModelPos.add(modelInst.center);
		return cam.frustum.sphereInFrustum(currentModelPos, modelInst.radius);
	}

	@Override
	public void show() {
	}

	private final int moveSpeed = 10; // 25
	private final int camRotSpeed = 250;
	private final float bgMoveSpeed = 2.135f;
	private final float bgMoveSpeedBoost = 1.5f;
	
	private final float magicalOffset = 1.555f;

	Vector3 carPosRenderBatch = new Vector3();
	
//	Vector3 carDir = new Vector3();
	
//	Transform carTrans = new Transform();
	
	float rotation = 0;
	
//	z distance  = 1.8f * magicalOffset;
	
	float carRotX = 1;
	float carRotY = 1;
	float carRotZ = 1;
	
	float horizontalDistance = 0;
	float verticalDistance = 0;
	
	float angle = 0;
	float deltaX = 0;
	float deltaZ = 0;
	
	Matrix4 carMat = new Matrix4();
	
	float oldAngle = 0;
	
	private void input(float delta) {
//		carDir = Vector3.Zero;
		
		rotation = 0;
		
//		carRotX = car3dTrans.rotation.x;
//		carRotY = car3dTrans.rotation.y;
//		carRotZ = car3dTrans.rotation.z;
		
		float carRotX = 0;
		float carRotY = 0;
		float carRotZ = 0;
		
//		horizontalDistance = 1.8f * magicalOffset * MathUtils.cos(cam.)
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			useFbo = !useFbo;
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			displayMap = !displayMap;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//			carRotX = carRotX - camRotSpeed / 8 * delta;
			
//			carRotX -= 1 * moveSpeed * delta;
			
//			car3dDirection.y -= 10 * delta;
			
//			cam.rotateAround(cam.position.cpy().sub(0, 3.14f, 0), Vector3.Y, camRotSpeed * delta);
			
//			float newAngle = oldAngle - 100 * delta;
//			carMat.setToRotation(Vector3.Y, newAngle);
//			carMat.trn(0, 0, -1);
//			carMat.translate(0, 0, -1);
			
			bgFrontPosX -= camRotSpeed * bgMoveSpeed * bgMoveSpeedBoost * delta;
			bgBackPosX -= camRotSpeed * bgMoveSpeed * delta;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//			carRotX = carRotX + camRotSpeed / 8 * delta;
			
//			carRotX += 1 * moveSpeed * delta;
			
//			car3dDirection.y += 10 * delta;
			
			
			
//			cam.rotateAround(cam.position.cpy().sub(0, 3.14f, 0), Vector3.Y, -camRotSpeed * delta);
			
			bgFrontPosX += camRotSpeed * bgMoveSpeed * bgMoveSpeedBoost * delta;
			bgBackPosX += camRotSpeed * bgMoveSpeed * delta;
		}
		
//		System.out.println(cam.position.cpy().sub(0, 3.14f, 0));

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//			car3dTrans.translation.add(carr, 0, -2 * carRotZ  * delta);
//			carRotZ -= 1 * moveSpeed * delta;
//			car3dTrans.translation.set(new Vector3(car3dTrans.translation.x -carRotX, car3dTrans.translation.y, car3dTrans.translation.z - carRotZ));
			
//			car3dPosition.add(car3dDirection.cpy().nor().scl(10 * delta));
			
//			carMat.getTranslation(new Vector3()).add(cam.direction.cpy().nor());
			
			carMat.setTranslation(carMat.getTranslation(new Vector3()).add(cam.direction.cpy().sub(0, cam.direction.y, 0).nor()));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//			car3dTrans.translation.add(0, 0, 2 * carRotZ * delta);
//			car3dTrans.translation.set(new Vector3(car3dTrans.translation.x + carRotX, car3dTrans.translation.y, car3dTrans.translation.z + carRotZ));
//			carRotZ += 1 * moveSpeed * delta;
			
			carMat.setTranslation(carMat.getTranslation(new Vector3()).sub(cam.direction.cpy().sub(0, cam.direction.y, 0).nor()));
		}

//		System.err.println(car3dTrans.rotation.getAngle());
		
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
//			cam.position.add(cam.direction.cpy().crs(cam.up).nor().scl(moveSpeed * delta));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
//			cam.position.add(cam.direction.cpy().crs(cam.up).nor().scl(-moveSpeed * delta));
		}
		
//		car3dPosition.set(new Vector3(car3dPosition.x + carRotX, car3dPosition.y, car3dPosition.z + carRotZ));
//		cam.position.set(car3dTrans.translation.cpy().add)
		
		
		car3dPosition.set(carMat.getTranslation(new Vector3().cpy()));
//		System.out.println(car3dTrans.rotation.getAngle());
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
	private boolean displayMap = true;

	Vector3 carVec = new Vector3(0 ,0 ,0);
	
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

//		cam.position.set(carPos.cpy().add(new Vector3(0, 3.14f, 1.8f)));
		
		cam.update();

		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight);

		if (useFbo) {
			fbo.begin();
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		}

		batch.begin();
		batch.draw(levelBgBack, 0, viewportHeight - 64 + 5, (int) bgBackPosX, 17, viewportWidthStretched, 64);
		batch.draw(levelBgFront, 0, viewportHeight - 64 + 5, (int) bgFrontPosX, 17, viewportWidthStretched, 64);
		batch.end();

		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidthStretched, viewportHeight); // not needed?
		
		mdlBatch.begin(cam);

		mdlBatch.render(mdlInstLevel);
		
		// car						
		mdlInstCar.transform.setToLookAt(cam.direction.cpy().add(new Vector3(0, -cam.direction.y, 0)).rotate(Vector3.Z, 180), Vector3.Y);


		mdlInstCar.transform.setTranslation(car3dPosition.cpy());
		mdlInstCar.transform.scale(1.25f, 1.75f, 1);
//		mdlInstCar.transform.rotate(Vector3.Z, 90);
		
		mdlBatch.render(mdlInstCar);

		mdlBatch.end();

		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight);

		batch.begin();
		batch.setColor(Color.WHITE); // Use to tint color of fog for loaded level.
		batch.draw(texFog, 0, viewportHeight - 75, 0, 0, viewportWidthStretched, 40);
		batch.setColor(Color.WHITE);
		batch.end();
		
		
//		car 2d
//		batch.setProjectionMatrix(cam.combined);
		
//		System.out.println("carVec: " + carVec);
		
		Vector3 carVec2D = new Vector3(cam.project(carVec.cpy()));
		
//		System.out.println(carVec2D);
		
		
		
		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight);
		
		batch.begin();
//		sprCar.setPosition(tmpVec.x, -tmpVec.z); // screen coordinates
//		sprCar.setPosition(viewportWidth / 2 - sprCar.getWidth() / 2, 38); // screen coordinates
//		sprCar.setPosition(carPos.x, carPos.z);
		
//		sprCar.setPosition(((int)carVec2D.x / viewportWidth), ((int)carVec2D.z / viewportHeight));
		sprCar.setPosition(carVec2D.x, carVec2D.y);
		
//		sprCar.draw(batch);
		
		batch.end();

//		System.err.println(((int)carVec2D.x / viewportWidth) + " | " + ((int)carVec2D.z / viewportHeight));
		
		batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidthStretched, viewportHeight);

		if (useFbo) {
			if (displayMap) {
				shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight); // this
																										// should
																										// appear
																										// stretched(?).

				shapeRenderer.setColor(Color.WHITE);
				shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
				for (int x = 0; x < array2d.length; x++) {
					for (int y = 0; y < array2d.length; y++) {
						if (array2d[x][y] == greenCurb) {
							shapeRenderer.box(-((viewportWidth / 2) - x) + 128 + 64, (viewportHeight / 2) - y + 64, 0,
									1, 1, 0);
						}
					}
				}

				shapeRenderer.setColor(Color.RED);
				shapeRenderer.box((viewportWidth / 2) - -carPosRenderBatch.x, (viewportHeight / 2) - carPosRenderBatch.z, 0, 1, 1,
						0);

				shapeRenderer.end();
				shapeRenderer.setColor(Color.WHITE);
			}

			fbo.end();
			viewport.apply(); // Else FBO draws over all screen.

			batch.begin();
			batch.draw(fbo.getColorBufferTexture(), 0, 0, viewportWidthStretched, viewportHeight, 0, 0, 1, 1);
			batch.end();
		}

		updateWndowTitle();
		
//		System.out.println(carPos);
	}

	private void updateWndowTitle() {
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
