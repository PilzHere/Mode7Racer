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
import mysko.pilzhere.mode7racer.entities.colliders.Jump;
import mysko.pilzhere.mode7racer.loaders.MapData;
import mysko.pilzhere.mode7racer.managers.AssetsManager;
import mysko.pilzhere.mode7racer.screens.GameScreen;
import mysko.pilzhere.mode7racer.utils.MapDrawer;

public class MapMaker extends Entity {
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

	private Texture texTileRoad01, texTileRoad02;
	private Texture texTileVoid;

	private Texture texTileJumpHori01, texTileJumpHoriLeft01, texTileJumpHoriRight01;

	private Texture levelBgFront, levelBgBack;

	private void setupTextures() {
		texTileCurbL = assMan.get(assMan.curb0101); // L
		texTileCurbR = assMan.get(assMan.curb1010); // R
		texTileCurbS = assMan.get(assMan.curb1100); // S
		texTileCurbN = assMan.get(assMan.curb0011); // N

		texTileCurbOuterCornerNL = assMan.get(assMan.curb1110); // NL
		texTileCurbOuterCornerNR = assMan.get(assMan.curb1101); // NR
		texTileCurbOuterCornerSL = assMan.get(assMan.curb1011); // SL
		texTileCurbOuterCornerSR = assMan.get(assMan.curb0111); // SR

		texTileCurbInnerCornerNL = assMan.get(assMan.curb1000); // black is NL
		texTileCurbInnerCornerNR = assMan.get(assMan.curb0100); // black is NR
		texTileCurbInnerCornerSL = assMan.get(assMan.curb0010); // black is SL
		texTileCurbInnerCornerSR = assMan.get(assMan.curb0001); // black is SR

		texTileCurbNone = assMan.get(assMan.curb0000);
		texTileCurbFull = assMan.get(assMan.curb1111);

		texTileRoad01 = assMan.get(assMan.road01);
		texTileRoad02 = assMan.get(assMan.road02);
		texTileVoid = assMan.get(assMan.void01);

		texTileJumpHori01 = assMan.get(assMan.jumpHori01);
		texTileJumpHoriLeft01 = assMan.get(assMan.jumpHoriLeft01);
		texTileJumpHoriRight01 = assMan.get(assMan.jumpHoriRight01);

		texFog = assMan.get(assMan.bgFog);
		texFog.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);

		levelBgBack = assMan.get(assMan.bg01Back);
		levelBgFront = assMan.get(assMan.bg01Front);
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

	private final int yellowJump = Color.rgba8888(Color.YELLOW);

	public int[][] colors2d; // get

	private final int tileSize = 16;

	private Model mdlLevel;
	private ModelInstanceBB mdlInstLevel;
	private Array<ModelInstanceBB> mdlInstances = new Array<ModelInstanceBB>();

//	boolean entityDrawn;

	public void loadLevelFromTexture() {
//		Texture levelLevelMap = assMan.get("level02.png", Texture.class);
//		Texture levelLevelMap = assMan.get("level04.png", Texture.class);
//		Texture levelLevelMap = assMan.get("level03.png", Texture.class);
		Texture levelLevelMap = assMan.get(assMan.mapMuteCity, Texture.class);
		
		final MapData mapData = new MapData();
		mapData.mapTexture = levelLevelMap;
		
		loadLevelFromTexture(mapData);
	}
	
	public void loadLevelFromTexture(final MapData mapData) {
		long startTime = System.currentTimeMillis();
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

//		TEST ADDING NEW OBJECTS
		Vector3 pos = new Vector3();
		pos.set(0, 0, 0);
		screen.getEntities().add(new Jump(screen, pos.cpy(), 0 + pos.x,
				0 + pos.z, 1, 1));
		pos.set(0, 0, 1);
		screen.getEntities().add(new Jump(screen, pos.cpy(), 0 + pos.x,
				0 + pos.z, 1, 1));
		pos.set(1, 0, 0);
		screen.getEntities().add(new Jump(screen, pos.cpy(), 0 + pos.x,
				0 + pos.z, 1, 1));
		pos.set(1, 0, 1);
		screen.getEntities().add(new Jump(screen, pos.cpy(), 0 + pos.x,
				0 + pos.z, 1, 1));
		
//		pos.set(-1, 0, 1);
//		screen.getEntities().add(new Jump(screen, pos.cpy(), 0 + pos.x,
//				0 + pos.z, 1, 1));
//		
		pos.set(0, 0, -1);
		screen.getEntities().add(new Jump(screen, pos.cpy(), 0 + pos.x,
				0 + pos.z, 1, 1));
		
//		test end

		final int srcWidth = loadedLevelPixmap.getWidth();
		final int srcHeight = loadedLevelPixmap.getHeight();

		colors2d = new int[srcWidth + 1][srcHeight + 1];
		for (int y = 0; y < srcHeight; y++) {
			for (int x = 0; x < srcWidth; x++) {
				colors2d[x][y] = loadedLevelPixmap.getPixel(x, y);
			}
		}

//		Paint tiles to generated world pixmap.

		MapDrawer mapDrawer = new MapDrawer();

		mapDrawer.begin(srcWidth, srcHeight, tileSize, tileSize);

		for (int x = 0; x < srcWidth; x++) {
			for (int y = 0; y < srcHeight; y++) {

				final int color = colors2d[x][y];
				final int colorAbove = colors2d[x][y + 1];
				final int colorUnder = y > 0 ? colors2d[x][y - 1] : 0;
				final int colorLeft = x > 0 ? colors2d[x - 1][y] : 0;
				final int colorRight = colors2d[x + 1][y];

				boolean roadUp = false;
				boolean roadDown = false;
				boolean roadLeft = false;
				boolean roadRight = false;

				boolean curbUp = false;
				boolean curbDown = false;
				boolean curbLeft = false;
				boolean curbRight = false;
				
//				Check if objects exists at current pixel.
				boolean entityDrawn = false;
				for (Entity ent : screen.getEntities()) {
					if (ent instanceof Jump) {						
						if (ent.position.x + (loadedLevelPixmap.getWidth() / 2) == x && ent.position.z + (loadedLevelPixmap.getHeight() / 2) == y) {
							System.out.println("Drawing jump tile at X: " + x + " | Y: " + y);
							mapDrawer.drawTile(x, y, texTileJumpHori01);
							entityDrawn = true;
						}
					}
				}

				if (!entityDrawn) {
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
//				else if (color == yellowJump) {
//					mapDrawer.drawTile(x, y, texTileJumpHori01);
//				}

				}

			}
		}

		Texture levelTextureGenerated = mapDrawer.end();
		levelTextureGenerated.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		System.out.println(
				"STATUS: New map is: " + levelTextureGenerated.getWidth() + " x " + levelTextureGenerated.getHeight());

//		Place objects such as Curbs and Eges by color.
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
		
	}
}
