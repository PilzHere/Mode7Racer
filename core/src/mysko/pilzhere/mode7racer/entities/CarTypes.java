package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;

import mysko.pilzhere.mode7racer.managers.AssetsManager;

/**
 * stats from https://fzero.fandom.com/wiki/Category:Vehicles
 * curve are not the real curves 
 */
public class CarTypes {
	public static final CarType INVISIBLE = new CarType(){
		{
			
		}
	};
	public static final CarType BLUE = new CarType(){
		{
			name = "Blue Falcon";
			engineUnit = "BF-2001x4";
			maxPower = 3200;
			maxSpeed = 457;
			weight = 1260;
			curve = Interpolation.sine;
		}
		public Texture getDefaultSprite(AssetsManager assMan) {
			return assMan.get(assMan.CAR_01_SIZE_09_FRONT_LEFT_01);
		}
	};
	public static final CarType YELLOW = new CarType(){
		{
			name = "Blue Falcon";
			engineUnit = "GF-2614x4";
			maxPower = 2950;
			maxSpeed = 438;
			weight = 1020;
			curve = new Interpolation() {
				@Override
				public float apply(float a) {
					return 1 - (float)Math.pow(1 - a, 7.0);
				}
			};
			
		}
		public Texture getDefaultSprite(AssetsManager assMan) {
			return assMan.get(assMan.CAR_02_SIZE_09_FRONT_LEFT_01);
		}
	};
	
	public static final CarType[] SELECTABLE_TYPES = {BLUE, YELLOW};
}
