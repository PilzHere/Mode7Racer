package mysko.pilzhere.mode7racer.loaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class MapLoader {

	private int mapWidth;
	private int mapHeight;
	
	public MapData load(TiledMap tilemap){
		
		final MapData mapData = new MapData();
		
		// get map dimmensions in order to center objects
		int width  = tilemap.getProperties().get("width", Integer.class);
		int height  = tilemap.getProperties().get("height", Integer.class);
		int tileWidth  = tilemap.getProperties().get("tilewidth", Integer.class);
		int tileHeight  = tilemap.getProperties().get("tileheight", Integer.class);
		mapWidth = width * tileWidth;
		mapHeight = height * tileHeight;
		
		// get map texture
		MapLayer mapLayer = tilemap.getLayers().get("map");
		if(mapLayer == null) throw new GdxRuntimeException("layer named 'map' not found");
		if(!(mapLayer instanceof TiledMapImageLayer)) throw new GdxRuntimeException("map layer should be Image Layer");
		TiledMapImageLayer mapImageLayer = (TiledMapImageLayer)mapLayer;
		Texture mapTexture = mapImageLayer.getTextureRegion().getTexture();
		
		mapData.mapTexture = mapTexture;
		
		// get objects layer
		MapLayer objectsLayer = tilemap.getLayers().get("objects");
		if(objectsLayer == null) throw new GdxRuntimeException("layer named 'objects' not found");
		
		// objects
		MapObjectDef finishLine = null;
		
		// parse objects
		for(MapObject o : objectsLayer.getObjects()){
			if(o instanceof TiledMapTileMapObject){
				TiledMapTileMapObject object = (TiledMapTileMapObject)o;
				String type = object.getTile().getProperties().get("type", null, String.class);
				if("finish".equals(type)){
					if(finishLine != null) throw new GdxRuntimeException("multiple finish lines found");
					finishLine = parseDef(new MapObjectDef(), object);
				}
				else if("mine".equals(type)){
					mapData.mines.add(parseDef(new MapObjectDef(), object));
				}
				else if("recovery".equals(type)){
					mapData.recoveries.add(parseDef(new MapObjectDef(), object));
				}
				else if("ramp".equals(type)){
					mapData.ramps.add(parseDef(new MapObjectDef(), object));
				}
				else{
					throw new GdxRuntimeException("unsupported object type: " + type);
				}
			}else{
				throw new GdxRuntimeException("unsupported object type " + o.getClass().getSimpleName());
			}
		}
		
		if(finishLine == null) throw new GdxRuntimeException("finish lines not found");
		
		mapData.finishLine = finishLine;
		
		return mapData;
	}

	private MapObjectDef parseDef(MapObjectDef def, TiledMapTileMapObject object){
		// parse bounds
		TextureRegion region = object.getTile().getTextureRegion();
		float w = region.getRegionWidth() * object.getScaleX();
		float h = region.getRegionHeight() * object.getScaleY();
		def.bounds.set(object.getX() - mapWidth/2f, mapHeight/2f - (object.getY() + h), w, h);
		
		// parse orientation
		float angle = (object.getRotation() % 360 + 360) % 360;
		
		// flip bounding box based on rotation
		int rot = MathUtils.round(angle / 90);
		if(rot == 1){
			def.bounds.set(def.bounds.x, def.bounds.y + def.bounds.height, def.bounds.height, def.bounds.width);
		}else if(rot == 2){
			def.bounds.set(def.bounds.x - def.bounds.width, def.bounds.y + def.bounds.height, def.bounds.width, def.bounds.height);
		}else if(rot == 3){
			def.bounds.set(def.bounds.x - def.bounds.height, def.bounds.y + def.bounds.height - def.bounds.width, def.bounds.height, def.bounds.width);
		}
		
		// convert to X/Z plan
		def.orientation = (rot + 1) % 4;
		
		return def;
	}

}
