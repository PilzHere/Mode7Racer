package mysko.pilzhere.mode7racer.entities.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.mode7racer.entities.Entity;
import mysko.pilzhere.mode7racer.entities.ModelInstanceBB;
import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Tile extends Entity {
	final char tileType;
	final int color;
	private ModelInstanceBB mdlInstTile;

	public Tile(GameScreen screen, Vector3 position, char tileType) {
		super(screen);
		super.position = position;

		this.tileType = tileType;
		this.color = 0;

		final char death = 'X';
		final char road = '#';
		final char curb = 'C';

		mdlInstTile = new ModelInstanceBB(screen.mdlTile);

		Material mat = mdlInstTile.materials.get(0);
		mat.set(ColorAttribute.createDiffuse(Color.WHITE));
		if (tileType == death) {
			mat.set(TextureAttribute.createDiffuse(screen.texTileCurbLeft));
			mat.set(ColorAttribute.createDiffuse(Color.RED));
		} else if (tileType == road) {
			mat.set(TextureAttribute.createDiffuse(screen.texTileCurbLeft));
			mat.set(ColorAttribute.createDiffuse(Color.GRAY));
		} else if (tileType == curb) {
			mat.set(TextureAttribute.createDiffuse(screen.texTileCurbLeft));
		} else {
			mat.set(TextureAttribute.createDiffuse(screen.texTileCurbLeft));
			mat.set(ColorAttribute.createDiffuse(Color.BLACK));
		}

		mdlInstTile.transform.setTranslation(position);

	}

	public Tile(GameScreen screen, Vector3 position, int color) {
		super(screen);
		super.position = position;

		this.tileType = '#';
		this.color = color;

		final int death = Color.rgba8888(Color.RED);
		final int road = Color.rgba8888(Color.GRAY);
		final int curb = Color.rgba8888(Color.GREEN);

		mdlInstTile = new ModelInstanceBB(screen.mdlTile);

		Material mat = mdlInstTile.materials.get(0);
		mat.set(ColorAttribute.createDiffuse(Color.WHITE));
		if (color == death) {
			mat.set(TextureAttribute.createDiffuse(screen.texTileVoid));
//			mat.set(ColorAttribute.createDiffuse(Color.RED));
		} else if (color == road) {
			mat.set(TextureAttribute.createDiffuse(screen.texTileRoad01));
//			mat.set(ColorAttribute.createDiffuse(Color.GRAY));
		} else if (color == curb) {
			mat.set(TextureAttribute.createDiffuse(screen.texTileCurbLeft));
		} else {
			mat.set(TextureAttribute.createDiffuse(screen.texTileCurbLeft));
			mat.set(ColorAttribute.createDiffuse(Color.BLACK));
		}

		mdlInstTile.transform.setTranslation(position);

	}

	@Override
	public void tick(float delta) {

	}

	@Override
	public void render3D(float delta) {
		if (screen.isVisible(screen.cam, mdlInstTile)) {
			screen.getModelBatch().render(mdlInstTile);
			screen.renderedModels++;
		}
	}

	@Override
	public void render2D(float delta) {

	}

	@Override
	public void destroy() {
		if (super.destroy) {

		}
	}

	@Override
	public void dispose() {

	}
}
