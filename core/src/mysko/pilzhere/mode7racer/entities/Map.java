package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import mysko.pilzhere.mode7racer.entities.colliders.Curb;
import mysko.pilzhere.mode7racer.entities.colliders.Edge;
import mysko.pilzhere.mode7racer.entities.colliders.Jump;
import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Map extends Entity {
	public Array<ModelInstanceBB> getMdlInstances() {
		return mdlInstances;
	}

	public boolean isBlink() {
		return blink;
	}

	public void setBlink(boolean blink) {
		this.blink = blink;
	}

	private float bgFrontPosX, bgBackPosX;

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

	public Map(GameScreen screen, Vector3 position) {
		super(screen, position);
		setupTextures();
	}

	private Texture texFog;
	private Texture texTileCurbL, texTileCurbR, texTileCurbS, texTileCurbN;
	private Texture texTileCurbOuterCornerNL, texTileCurbOuterCornerNR, texTileCurbOuterCornerSL,
			texTileCurbOuterCornerSR;
	private Texture texTileCurbNone, texTileCurbFull;
	private Texture texTileCurbInnerCornerNL, texTileCurbInnerCornerNR, texTileCurbInnerCornerSL,
			texTileCurbInnerCornerSR;
	private Texture texTileRoad01, texTileRoad02;
	private Texture texTileVoid;
	private Texture levelBgFront, levelBgBack;

	private void setupTextures() {
		texTileCurbL = screen.assMan.get("curb0101.png"); // L
		texTileCurbR = screen.assMan.get("curb1010.png"); // R
		texTileCurbS = screen.assMan.get("curb1100.png"); // S
		texTileCurbN = screen.assMan.get("curb0011.png"); // N

		texTileCurbOuterCornerNL = screen.assMan.get("curb1110.png"); // NL
		texTileCurbOuterCornerNR = screen.assMan.get("curb1101.png"); // NR
		texTileCurbOuterCornerSL = screen.assMan.get("curb1011.png"); // SL
		texTileCurbOuterCornerSR = screen.assMan.get("curb0111.png"); // SR

		texTileCurbInnerCornerNL = screen.assMan.get("curb1000.png"); // black is NL
		texTileCurbInnerCornerNR = screen.assMan.get("curb0100.png"); // black is NR
		texTileCurbInnerCornerSL = screen.assMan.get("curb0010.png"); // black is SL
		texTileCurbInnerCornerSR = screen.assMan.get("curb0001.png"); // black is SR

		texTileCurbNone = screen.assMan.get("curb0000.png");
		texTileCurbFull = screen.assMan.get("curb1111.png");

		texTileRoad01 = screen.assMan.get("road01.png");
		texTileRoad02 = screen.assMan.get("road02.png");
		texTileVoid = screen.assMan.get("void.png");

		texFog = screen.assMan.get("fog01.png");
		texFog.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);

		levelBgBack = screen.assMan.get("levelBg01Back.png");
		levelBgFront = screen.assMan.get("levelBg01Front.png");
		levelBgBack.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		levelBgFront.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
	}

//	Colors
	private final int redVoid = Color.rgba8888(Color.RED);
	public final int greenCurb = Color.rgba8888(Color.GREEN);
//	private final int forestCurbRight = Color.rgba8888(Color.FOREST);
//	private final int limeCurbSouth = Color.rgba8888(Color.LIME);
//	private final int oliveCurbNorth = Color.rgba8888(Color.OLIVE);
	private final int greyRoad = Color.rgba8888(Color.GRAY);
	private final int lightGreyRoad = Color.rgba8888(Color.LIGHT_GRAY);
	private final int blackEdge = Color.rgba8888(Color.BLACK);

	public int[][] colors2d; // get

	private final int tileSize = 16;

	private Pixmap createWorldPixmap(Pixmap pix, Pixmap loadedLevelPix) {
		pix = new Pixmap(loadedLevelPix.getWidth() * tileSize, loadedLevelPix.getHeight() * tileSize, Format.RGB888);
		loadedlevelTextureWidth = loadedLevelPix.getWidth();
		loadedlevelTextureHeight = loadedLevelPix.getHeight();

		return pix;
	}

	private int loadedlevelTextureWidth;
	private int loadedlevelTextureHeight;

	private Model mdlLevel;
	private ModelInstanceBB mdlInstLevel;
	private Array<ModelInstanceBB> mdlInstances = new Array<ModelInstanceBB>();

	public void loadLevelFromTexture() {
		long startTime = System.currentTimeMillis();
		System.out.println("STATUS: Building level.");

//		Texture levelLevelMap = screen.assMan.get("level02.png", Texture.class);
//		Texture levelLevelMap = screen.assMan.get("level04.png", Texture.class);
		Texture levelLevelMap = screen.assMan.get("level03.png", Texture.class);
		TextureData loadedlevelMapData = levelLevelMap.getTextureData();

		Pixmap loadedLevelPixmap = null, generatedLevelPixmap = null, pixTileCurbL = null, pixTileCurbR = null,
				pixTileCurbN = null, pixTileCurbS = null, pixTileRoad01 = null, pixTileRoad02 = null,
				pixTileVoid = null, pixTileCurbOuterCornerNL = null, pixTileCurbOuterCornerNR = null,
				pixTileCurbOuterCornerSL = null, pixTileCurbOuterCornerSR = null, pixTileCurbInnerCornerNL = null,
				pixTileCurbInnerCornerNR = null, pixTileCurbInnerCornerSL = null, pixTileCurbInnerCornerSR = null,
				pixTileCurbNone = null, pixTileCurbFull = null;

//		Load level texture
		loadedLevelPixmap = prepareTextureData(loadedlevelMapData, levelLevelMap, loadedLevelPixmap);

//		Generate world level texture
		generatedLevelPixmap = createWorldPixmap(generatedLevelPixmap, loadedLevelPixmap);

		TextureData tileCurbLData = null, tileCurbRData = null, tileCurbSData = null, tileCurbNData = null,
				tileRoad01Data = null, tileRoad02Data = null, tileVoidData = null, tileCurbOuterCornerNLData = null,
				tileCurbOuterCornerNRData = null, tileCurbOuterCornerSLData = null, tileCurbOuterCornerSRData = null,
				tileCurbInnerCornerNLData = null, tileCurbInnerCornerNRData = null, tileCurbInnerCornerSLData = null,
				tileCurbInnerCornerSRData = null, tileCurbNoneData = null, tileCurbFullData = null;

//		Setup tiles
		pixTileVoid = prepareTextureData(tileVoidData, texTileVoid, pixTileVoid);

		pixTileRoad01 = prepareTextureData(tileRoad01Data, texTileRoad01, pixTileRoad01);
		pixTileRoad02 = prepareTextureData(tileRoad02Data, texTileRoad02, pixTileRoad02);

		pixTileCurbL = prepareTextureData(tileCurbLData, texTileCurbL, pixTileCurbL);
		pixTileCurbR = prepareTextureData(tileCurbRData, texTileCurbR, pixTileCurbR);
		pixTileCurbS = prepareTextureData(tileCurbSData, texTileCurbS, pixTileCurbS);
		pixTileCurbN = prepareTextureData(tileCurbNData, texTileCurbN, pixTileCurbN);

		pixTileCurbOuterCornerNL = prepareTextureData(tileCurbOuterCornerNLData, texTileCurbOuterCornerNL,
				pixTileCurbOuterCornerNL);
		pixTileCurbOuterCornerNR = prepareTextureData(tileCurbOuterCornerNRData, texTileCurbOuterCornerNR,
				pixTileCurbOuterCornerNR);
		pixTileCurbOuterCornerSL = prepareTextureData(tileCurbOuterCornerSLData, texTileCurbOuterCornerSL,
				pixTileCurbOuterCornerSL);
		pixTileCurbOuterCornerSR = prepareTextureData(tileCurbOuterCornerSRData, texTileCurbOuterCornerSR,
				pixTileCurbOuterCornerSR);

		pixTileCurbInnerCornerNL = prepareTextureData(tileCurbInnerCornerNLData, texTileCurbInnerCornerNL,
				pixTileCurbInnerCornerNL);
		pixTileCurbInnerCornerNR = prepareTextureData(tileCurbInnerCornerNRData, texTileCurbInnerCornerNR,
				pixTileCurbInnerCornerNR);
		pixTileCurbInnerCornerSL = prepareTextureData(tileCurbInnerCornerSLData, texTileCurbInnerCornerSL,
				pixTileCurbInnerCornerSL);
		pixTileCurbInnerCornerSR = prepareTextureData(tileCurbInnerCornerSRData, texTileCurbInnerCornerSR,
				pixTileCurbInnerCornerSR);

		pixTileCurbFull = prepareTextureData(tileCurbFullData, texTileCurbFull, pixTileCurbFull);
		pixTileCurbNone = prepareTextureData(tileCurbNoneData, texTileCurbNone, pixTileCurbNone);

		colors2d = new int[loadedlevelTextureWidth + 1][loadedlevelTextureHeight + 1];

//		Place colors to 2dArray for each pixel from texture loaded.
		int loadedMapPixelX = -1;
		int loadedMapPixelY = -1;

		int noName = 0;
		switch (loadedLevelPixmap.getWidth()) {
		case 128:
			noName = 8;
			break;
		case 256:
			noName = 16;
			break;
		case 512:
			noName = 32;
			break;
		case 1024:
			noName = 64;
			break;
		}

		System.out.println("New map is: " + generatedLevelPixmap.getWidth() + " x " + generatedLevelPixmap.getHeight());
		
		/**
		 * Need to read custom objects before painting map: As some objects such as jumps will have tiles on the floor.
		 */
		
//		TEST ADDING NEW OBJECTS
//		float xo = 0;
//		float zo = 0;
//		screen.getEntities().add(new Jump(screen, new Vector3(xo, 0, zo), 0 - loadedLevelPixmap.getWidth() / 2, 0 - loadedLevelPixmap.getHeight() / 2, 1, 1));
////		test end

		for (int x = 0; x < generatedLevelPixmap.getWidth(); x++) {
			if (x % loadedlevelTextureWidth / noName == 0) { // noName was (tileSize / 2) when loading 128x128.
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
				colors2d[loadedMapPixelX][loadedMapPixelY] = currentColor;
			}
		}

//		Paint tiles to generated world pixmap.
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

				final int color = colors2d[(i / tileSize)][(j / tileSize)];
				final int colorAbove = colors2d[i / tileSize][(j / tileSize) + 1];

				int colorUnder = 0;
				if ((j / tileSize) - 1 >= 0) {
					colorUnder = colors2d[i / tileSize][(j / tileSize) - 1];
				}

				int colorLeft = 0;
				if ((i / tileSize) - 1 >= 0) {
					colorLeft = colors2d[(i / tileSize) - 1][j / tileSize];
				}

				final int colorRight = colors2d[(i / tileSize) + 1][j / tileSize];

				boolean roadUp = false;
				boolean roadDown = false;
				boolean roadLeft = false;
				boolean roadRight = false;

				boolean curbUp = false;
				boolean curbDown = false;
				boolean curbLeft = false;
				boolean curbRight = false;

				if (color == redVoid) {
					generatedLevelPixmap.drawPixel(i, j, pixTileVoid.getPixel(tileX, tileY));
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

//		Place objects
		for (int x = 0; x < loadedLevelPixmap.getWidth(); x++) {
			for (int z = 0; z < loadedLevelPixmap.getHeight(); z++) {
				final int currentColor = loadedLevelPixmap.getPixel(x, z);
				if (currentColor == greenCurb) { // use switch insead?
					screen.getEntities().add(new Curb(screen, new Vector3(), x - loadedLevelPixmap.getWidth() / 2,
							z - loadedLevelPixmap.getHeight() / 2, 1, 1));
//					System.err.println("Curb added at x: " + (x - loadedLevelPixmap.getWidth() / 2) + " | y: " + (z - loadedLevelPixmap.getHeight() / 2));
				} else if (currentColor == blackEdge) {
					screen.getEntities().add(new Edge(screen, new Vector3(), x - loadedLevelPixmap.getWidth() / 2,
							z - loadedLevelPixmap.getHeight() / 2, 1, 1));
//					System.err.println("Edge added at x: " + (x - loadedLevelPixmap.getWidth() / 2) + " | y: " + (z - loadedLevelPixmap.getHeight() / 2));
				}
			}
		}

		Texture levelTextureGenerated = new Texture(generatedLevelPixmap);
		levelTextureGenerated.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		ModelBuilder modelBuilder = new ModelBuilder();
		mdlLevel = modelBuilder.createBox(loadedLevelPixmap.getWidth(), 0, loadedLevelPixmap.getHeight(),
				new Material(TextureAttribute.createDiffuse(levelTextureGenerated)),
				Usage.Position | Usage.TextureCoordinates);
		mdlInstances.add(mdlInstLevel = new ModelInstanceBB(mdlLevel));
		mdlInstLevel.transform.rotate(Vector3.Y, 90);

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

//		Dispose
		loadedLevelPixmap.dispose();
		generatedLevelPixmap.dispose();

		pixTileCurbL.dispose();
		pixTileCurbR.dispose();
		pixTileCurbS.dispose();
		pixTileCurbN.dispose();
		pixTileRoad01.dispose();
		pixTileRoad02.dispose();
		pixTileVoid.dispose();

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

//		Load status
		long endTime = System.currentTimeMillis();
		System.out.println("STATUS: Building level finished. (" + (endTime - startTime) + " ms)");
	}

	private Pixmap prepareTextureData(TextureData td, Texture tex, Pixmap pix) {
		td = tex.getTextureData();
		if (!td.isPrepared()) {
			td.prepare();
		}

		return pix = td.consumePixmap();
	}

	private void setupWorldEdgeTiles(float x, float y) {
		ModelInstanceBB mdlBB = new ModelInstanceBB(mdlLevel);
		mdlBB.transform.setToTranslation(new Vector3(x, 0, y));
		mdlBB.transform.rotate(Vector3.Y, 90); // Have to rotate after positioning for some reason...
		mdlInstances.add(mdlBB);
	}

	private boolean blink;

	@Override
	public void tick(float delta) {
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
		}
//	}

	@Override
	public void render3D(ModelBatch batch, float delta) {
		for (ModelInstanceBB mdlInstBB : mdlInstances) {
			if (screen.isVisible(screen.getCam(), mdlInstBB)) {
				batch.render(mdlInstBB);
				screen.renderedModels++;
			}
		}
	}

	@Override
	public void destroy() {
//		screen.rects.clear();
	}

//	private void loadLevelFromText() {
//	FileHandle handle = Gdx.files.local("level01.txt");
//	String text = handle.readString();
//	String wordsArray[] = text.split("\\r?\\n");
//
////	Check for level line length. Should be same length.
////	TODO: Make sure it looks for EVEN LINE LENGTH!
//	for (int i = 0; i < wordsArray.length; i++) {
////		System.out.println(wordsArray[i]);
//		if (wordsArray[i].length() != wordsArray[0].length()) {
//			System.err.println("Error: Level: Lines not same length. (Line: " + (i + 1) + ")");
//		}
//	}
//
//	final int levelXOffset = wordsArray[0].length() / 2;
//	final int levelZOffset = wordsArray.length / 2;
//
//	final char death = 'X';
//	final char road = '#';
//	final char curb = 'C';
//
//	int hori = -1;
//	int vert = -1;
//
////	Place tiles for each char at line
//	for (int x = -levelXOffset; x < levelXOffset; x++) {
//		hori++;
//		for (int z = -levelZOffset; z < levelZOffset; z++) {
//			vert++;
//			if (vert > wordsArray.length - 1) {
//				vert = 0;
//			}
//			final char type = wordsArray[vert].charAt(hori);
//			System.out.println("x: " + hori + " | " + "y: " + vert + " | char: " + type);
//
//			entities.add(new Tile(this, new Vector3(x + 0.5f, 0, z), type));
//		}
//	}
//}
}
