package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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
import mysko.pilzhere.mode7racer.screens.GameScreen;
import mysko.pilzhere.mode7racer.utils.MapDrawer;

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

	private Model mdlLevel;
	private ModelInstanceBB mdlInstLevel;
	private Array<ModelInstanceBB> mdlInstances = new Array<ModelInstanceBB>();

	public void loadLevelFromTexture() {
		long startTime = System.currentTimeMillis();
		System.out.println("STATUS: Building level.");

//		Texture levelLevelMap = screen.assMan.get("level02.png", Texture.class);
//		Texture levelLevelMap = screen.assMan.get("level04.png", Texture.class);
		Texture levelLevelMap = screen.assMan.get("level03.png", Texture.class);

//		Load level texture
		Pixmap loadedLevelPixmap = prepareTextureData(levelLevelMap);

//		Generate world level texture
		
//		Place colors to 2dArray for each pixel from texture loaded.

		/**
		 * Need to read custom objects before painting map: As some objects such as jumps will have tiles on the floor.
		 */
		
//		TEST ADDING NEW OBJECTS
//		float xo = 0;
//		float zo = 0;
//		screen.getEntities().add(new Jump(screen, new Vector3(xo, 0, zo), 0 - loadedLevelPixmap.getWidth() / 2, 0 - loadedLevelPixmap.getHeight() / 2, 1, 1));
////		test end

		int srcWidth = loadedLevelPixmap.getWidth();
		int srcHeight = loadedLevelPixmap.getHeight();
		
		colors2d = new int[srcWidth + 1][srcHeight + 1];
		for(int y=0 ; y<srcHeight ; y++){
			for(int x=0 ; x<srcWidth ; x++){
				colors2d[x][y] = loadedLevelPixmap.getPixel(x, y);
			}
		}

//		Paint tiles to generated world pixmap.

		MapDrawer mapDrawer = new MapDrawer();
		
		mapDrawer.begin(srcWidth, srcHeight, tileSize, tileSize);
		
		for (int x = 0; x < srcWidth; x++) {

			for (int y = 0; y < srcHeight ; y++) {

				final int color = colors2d[x][y];
				final int colorAbove = colors2d[x][y+1];
				final int colorUnder = y > 0 ? colors2d[x][y-1] : 0;
				final int colorLeft  = x > 0 ? colors2d[x-1][y] : 0;
				final int colorRight = colors2d[x+1][y];

				boolean roadUp = false;
				boolean roadDown = false;
				boolean roadLeft = false;
				boolean roadRight = false;

				boolean curbUp = false;
				boolean curbDown = false;
				boolean curbLeft = false;
				boolean curbRight = false;

				if (color == redVoid) {
					mapDrawer.drawTile(x, y, texTileVoid);
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
				} else if (color == greyRoad) {
					mapDrawer.drawTile(x, y, texTileRoad01);
				} else if (color == lightGreyRoad) {
					mapDrawer.drawTile(x, y, texTileRoad02);
				}
			}
		}
		
		Texture levelTextureGenerated = mapDrawer.end();
		levelTextureGenerated.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		System.out.println("New map is: " + levelTextureGenerated.getWidth() + " x " + levelTextureGenerated.getHeight());

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

//		Load status
		long endTime = System.currentTimeMillis();
		System.out.println("STATUS: Building level finished. (" + (endTime - startTime) + " ms)");
	}

	private Pixmap prepareTextureData(Texture tex) {
		TextureData td = tex.getTextureData();
		if (!td.isPrepared()) {
			td.prepare();
		}
		return td.consumePixmap();
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
