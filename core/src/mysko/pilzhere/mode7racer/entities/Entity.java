package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Entity implements Disposable {
	protected final GameScreen screen;
	
	protected String name;
	public Vector3 position = new Vector3(); //get
	public Vector3 screenPos = new Vector3(); // get
	public Rectangle rect;
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
	
	@Override
	public void dispose() {
		
	}
}
