package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Entity implements Disposable {
	protected final GameScreen screen;
	
	protected String name;
	protected Vector3 position;
	protected boolean destroy;
	
	public Entity(GameScreen screen) {
		this.screen = screen;
	}
	
	public void tick(float delta) {
		
	}
	
	public void render3D(float delta) {
		
	}
	
	public void render2D(float delta) {
		
	}
	
	public void destroy() {
		
	}
	
	@Override
	public void dispose() {
		
	}
}
