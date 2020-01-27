package mysko.pilzhere.mode7racer.loaders;

import com.badlogic.gdx.math.Rectangle;

public class MapObjectDef {
	/** object bounds in X/Z plan (origin at map center) with rotation applied */
	public final Rectangle bounds = new Rectangle();
	
	/** object orientation in X/Z plan (0, 1, 2 or 3), can be multiplied by 90 to have angle in degrees */
	public int orientation;
}