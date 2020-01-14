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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import mysko.pilzhere.mode7racer.entities.colliders.Curb;
import mysko.pilzhere.mode7racer.entities.colliders.Edge;
import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Map extends Entity {
	public Texture texTileCurbL;
	public Texture texTileCurbR;
	public Texture texTileCurbS;
	public Texture texTileCurbN;

	public Texture texTileCurbOuterCornerNL, texTileCurbOuterCornerNR, texTileCurbOuterCornerSL,
			texTileCurbOuterCornerSR;
	public Texture texTileCurbNone, texTileCurbFull;
	public Texture texTileCurbInnerCornerNL, texTileCurbInnerCornerNR, texTileCurbInnerCornerSL,
			texTileCurbInnerCornerSR;

	public Texture texTileRoad01, texTileRoad02;
	public Texture texTileVoid;

	public Texture texFog; // get

	public Texture levelBgFront, levelBgBack; // get
	public float bgFrontPosX = 0f, bgBackPosX = 0f; // getset

	private final int tileSize = 16;

	private Model mdlLevel;
//	private Model mdlLevelEdge;
	private ModelInstanceBB mdlInstLevel;

	private Array<ModelInstanceBB> mdlInstances = new Array<ModelInstanceBB>();

	public Map(GameScreen screen, Vector3 position) {
		super(screen, position);

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
	public final int redVoid01 = Color.rgba8888(Color.RED);

	public final int greenCurb = Color.rgba8888(Color.GREEN);
//	private final int forestCurbRight = Color.rgba8888(Color.FOREST);
//	private final int limeCurbSouth = Color.rgba8888(Color.LIME);
//	private final int oliveCurbNorth = Color.rgba8888(Color.OLIVE);

	public final int greyRoad = Color.rgba8888(Color.GRAY);
	public final int lightGreyRoad = Color.rgba8888(Color.LIGHT_GRAY);

	public final int blackEdge = Color.rgba8888(Color.BLACK);

	public int[][] array2d; // get

	public void loadLevelFromTexture() {
		long startTime = System.currentTimeMillis();
		System.out.println("STATUS: Building level.");

		Texture levelLevelMap = screen.assMan.get("level02.png", Texture.class);
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

//		For placing objects/rects!
		for (int x = 0; x < loadedLevelPixmap.getWidth(); x++) {
			for (int z = 0; z < loadedLevelPixmap.getHeight(); z++) {
				final int currentColor = loadedLevelPixmap.getPixel(x, z);
				if (currentColor == greenCurb) {
					screen.entities.add(new Curb(screen, new Vector3(), x - loadedLevelPixmap.getWidth() / 2,
							z - loadedLevelPixmap.getHeight() / 2, 1, 1));
//					System.err.println("Curb added at x: " + (x - loadedLevelPixmap.getWidth() / 2) + " | y: " + (z - loadedLevelPixmap.getHeight() / 2));
				} else if (currentColor == blackEdge) {
					screen.entities.add(new Edge(screen, new Vector3(), x - loadedLevelPixmap.getWidth() / 2,
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

//		mdlLevelEdge = modelBuilder.createBox(loadedLevelPixmap.getWidth(), 0, loadedLevelPixmap.getHeight(),
//				new Material(TextureAttribute.createDiffuse(levelTextureGenerated)),
//				Usage.Position | Usage.TextureCoordinates);

//		EDGES TOP
		ModelInstanceBB mdlInstLevelEdgeXm1Z1 = new ModelInstanceBB(mdlLevel);
		mdlInstLevelEdgeXm1Z1.transform.rotate(Vector3.Y, 90);
		mdlInstLevelEdgeXm1Z1.transform
				.setToTranslation(new Vector3(-loadedLevelPixmap.getWidth(), 0, loadedLevelPixmap.getHeight()));
		mdlInstances.add(mdlInstLevelEdgeXm1Z1);

		ModelInstanceBB mdlInstLevelEdgeX0Z1 = new ModelInstanceBB(mdlLevel);
		mdlInstLevelEdgeX0Z1.transform.rotate(Vector3.Y, 90);
		mdlInstLevelEdgeX0Z1.transform.setToTranslation(new Vector3(0, 0, loadedLevelPixmap.getHeight()));
		mdlInstances.add(mdlInstLevelEdgeX0Z1);

		ModelInstanceBB mdlInstLevelEdgeX1Z1 = new ModelInstanceBB(mdlLevel);
		mdlInstLevelEdgeX1Z1.transform.rotate(Vector3.Y, 90);
		mdlInstLevelEdgeX1Z1.transform
				.setToTranslation(new Vector3(loadedLevelPixmap.getWidth(), 0, loadedLevelPixmap.getHeight()));
		mdlInstances.add(mdlInstLevelEdgeX1Z1);

//		EDGES LEFT - RIGHT
		ModelInstanceBB mdlInstLevelEdgeXm1Z0 = new ModelInstanceBB(mdlLevel);
		mdlInstLevelEdgeXm1Z0.transform.rotate(Vector3.Y, 90);
		mdlInstLevelEdgeXm1Z0.transform.setToTranslation(new Vector3(-loadedLevelPixmap.getWidth(), 0, 0));
		mdlInstances.add(mdlInstLevelEdgeXm1Z0);

		ModelInstanceBB mdlInstLevelEdgeX1Z0 = new ModelInstanceBB(mdlLevel);
		mdlInstLevelEdgeX1Z0.transform.rotate(Vector3.Y, 90);
		mdlInstLevelEdgeX1Z0.transform.setToTranslation(new Vector3(loadedLevelPixmap.getWidth(), 0, 0));
		mdlInstances.add(mdlInstLevelEdgeX1Z0);

//		EDGES BOTTOM
		ModelInstanceBB mdlInstLevelEdgeXm1Zm1 = new ModelInstanceBB(mdlLevel);
		mdlInstLevelEdgeXm1Zm1.transform.rotate(Vector3.Y, 90);
		mdlInstLevelEdgeXm1Zm1.transform
				.setToTranslation(new Vector3(-loadedLevelPixmap.getWidth(), 0, -loadedLevelPixmap.getHeight()));
		mdlInstances.add(mdlInstLevelEdgeXm1Zm1);

		ModelInstanceBB mdlInstLevelEdgeX0Zm1 = new ModelInstanceBB(mdlLevel);
		mdlInstLevelEdgeX0Zm1.transform.rotate(Vector3.Y, 90);
		mdlInstLevelEdgeX0Zm1.transform.setToTranslation(new Vector3(0, 0, -loadedLevelPixmap.getHeight()));
		mdlInstances.add(mdlInstLevelEdgeX0Zm1);

		ModelInstanceBB mdlInstLevelEdgeX1Zm1 = new ModelInstanceBB(mdlLevel);
		mdlInstLevelEdgeX1Zm1.transform.rotate(Vector3.Y, 90);
		mdlInstLevelEdgeX1Zm1.transform
				.setToTranslation(new Vector3(loadedLevelPixmap.getWidth(), 0, -loadedLevelPixmap.getHeight()));
		mdlInstances.add(mdlInstLevelEdgeX1Zm1);

//		Dispose
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

//		Status
		long endTime = System.currentTimeMillis();
		System.out.println("STATUS: Building level finished. (" + (endTime - startTime) + " ms)");
	}

	public boolean blink; // getset

	@Override
	public void tick(float delta) {

		if (blink)
			System.out.println("Map Blink!");
		
//			Blink all curbs ehre!		

		for (ModelInstanceBB mdlInstBB : mdlInstances) {
			if (blink) {
				mdlInstBB.materials.get(0).set(ColorAttribute.createDiffuse(Color.RED));

			} else {
				mdlInstBB.materials.get(0).set(ColorAttribute.createDiffuse(Color.WHITE));
			}
		}
	}

	@Override
	public void render3D(ModelBatch batch, float delta) {
		for (ModelInstanceBB mdlInstBB : mdlInstances) {
			if (screen.isVisible(screen.cam, mdlInstBB)) {
				batch.render(mdlInstBB);
				screen.renderedModels++;
			}
		}
	}

	@Override
	public void destroy() {
//		screen.rects.clear();
	}

	@Override
	public void dispose() {
//		levelBgFront.dispose(); dunno
//		levelBgBack.dispose();
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
