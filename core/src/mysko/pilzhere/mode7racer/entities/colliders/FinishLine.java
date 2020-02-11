package mysko.pilzhere.mode7racer.entities.colliders;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.mode7racer.entities.Entity;
import mysko.pilzhere.mode7racer.screens.GameScreen;

public class FinishLine extends Entity{
	
	public FinishLine(final GameScreen screen, final Vector3 position, final float posX, final float posZ, final float width, final float height) {
		super(screen, position);
		
		rect = new Rectangle(posX, posZ, width, height);
	}
}
