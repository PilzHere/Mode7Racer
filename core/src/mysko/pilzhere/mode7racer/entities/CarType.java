package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;

import mysko.pilzhere.mode7racer.managers.AssetsManager;

public class CarType {

	public String name;
	public String engineUnit;
	public int maxPower;
	public int maxSpeed;
	public int weight;
	public Interpolation curve;

	public Texture getDefaultSprite(AssetsManager assMan) {
		return null;
	}
}
