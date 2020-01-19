package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Entity {
//	public void setDistFromCam(float distFromCam) {
//		this.distFromCam = distFromCam;
//	}

//	public float getDistFromCam() {
//		return distFromCam;
//	}

//	public Sprite getSprite() {
//		return sprite;
//	}

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

	public Entity(GameScreen screen, Vector3 position) {
		this.screen = screen;
		this.position = position;
	}

	public void tick(float delta) {
	}

	public void onHit(Object object, float delta) {
	}

	public void render3D(ModelBatch batch, float delta) {
	}

	public void render2D(SpriteBatch batch, float delta) {
	}

	public void destroy() {
	}

//	@Override
//	public int compareTo(Entity o) {
////		return (int) (screenPos.y - o.screenPos.y);
//		int compareQuantity = (int) ((Entity) o).screenPos.y;
//		
//		return (int) (this.screenPos.y - compareQuantity);
//	}
}
