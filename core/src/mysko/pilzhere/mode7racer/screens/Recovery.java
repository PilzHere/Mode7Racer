package mysko.pilzhere.mode7racer.screens;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.mode7racer.entities.Entity;

public class Recovery extends Entity {
	
	public Recovery(GameScreen screen, Vector3 position, float posX, float posZ, float width, float height) {
		super(screen, position);
		
		rect = new Rectangle(posX, posZ, width, height);
	}
}