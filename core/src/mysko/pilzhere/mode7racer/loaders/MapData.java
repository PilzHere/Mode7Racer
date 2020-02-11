package mysko.pilzhere.mode7racer.loaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class MapData {
	public Texture mapTexture;
	public MapObjectDef finishLine;
	public final Array<MapObjectDef> mines = new Array<MapObjectDef>();
	public final Array<MapObjectDef> recoveries = new Array<MapObjectDef>();
	public final Array<MapObjectDef> ramps = new Array<MapObjectDef>();
	public final Array<MapObjectDef> checkpoints = new Array<MapObjectDef>();
}
