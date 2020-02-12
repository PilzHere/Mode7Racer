package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.mode7racer.screens.GameScreen;

public abstract class Entity {
	public Rectangle getRect() {
		return rect;
	}

	public Vector3 getScreenPos() {
		return screenPos;
	}

	public Vector3 getPosition() {
		return position;
	}

	protected final GameScreen screen;

	protected String name;
	protected Vector3 position = new Vector3();
	protected Vector3 screenPos = new Vector3();
//	protected float distFromCam;
//	protected Sprite sprite;
	protected Rectangle rect;
	protected boolean destroy;

	public Entity(final GameScreen screen, final Vector3 position) {
		this.screen = screen;
		this.position = position;
	}

	public abstract void tick(final float delta);

	public abstract void onHit(final Object object, final float delta);

	public abstract void render3D(final ModelBatch batch, final float delta);

	public abstract void render2D(final SpriteBatch batch, final float delta);

	public abstract void destroy();
}
