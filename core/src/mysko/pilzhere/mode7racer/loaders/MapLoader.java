package mysko.pilzhere.mode7racer.loaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class MapLoader {

	private int mapWidth;
	private int mapHeight;
	
	public MapData load(final TiledMap tilemap){
		
		final MapData mapData = new MapData();
		
		// get map dimmensions in order to center objects
		final int WIDTH  = tilemap.getProperties().get("width", Integer.class);
		final int HEIGHT  = tilemap.getProperties().get("height", Integer.class);
		final int TILE_WIDTH  = tilemap.getProperties().get("tilewidth", Integer.class);
		final int TILE_HEIGHT  = tilemap.getProperties().get("tileheight", Integer.class);
		mapWidth = WIDTH * TILE_WIDTH;
		mapHeight = HEIGHT * TILE_HEIGHT;
		
		// get map texture
		final MapLayer mapLayer = tilemap.getLayers().get("map");
		if(mapLayer == null) throw new GdxRuntimeException("layer named 'map' not found");
		if(!(mapLayer instanceof TiledMapImageLayer)) throw new GdxRuntimeException("map layer should be Image Layer");
		final TiledMapImageLayer mapImageLayer = (TiledMapImageLayer)mapLayer;
		final Texture mapTexture = mapImageLayer.getTextureRegion().getTexture();
		
		mapData.mapTexture = mapTexture;
		
		// get objects layer
		final MapLayer objectsLayer = tilemap.getLayers().get("objects");
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
			}else if (o instanceof RectangleMapObject) {
				RectangleMapObject object = (RectangleMapObject)o;
				String type = object.getProperties().get("type", null, String.class);
//				int checkpointNumber = object.getProperties().get("checkpointnumber", null, Integer.class);
				if("checkpoint".equals(type)){
					mapData.checkpoints.add(parseDef(new MapObjectDef(), object/*, checkpointNumber*/));
				}
			} else {
				throw new GdxRuntimeException("unsupported object type " + o.getClass().getSimpleName());
			}
		}
		
		if(finishLine == null) throw new GdxRuntimeException("finish lines not found");
		
		mapData.finishLine = finishLine;
		
		return mapData;
	}

	private MapObjectDef parseDef(final MapObjectDef def, final TiledMapTileMapObject object){
		// parse bounds
		TextureRegion region = object.getTile().getTextureRegion();
		final float W = region.getRegionWidth() * object.getScaleX();
		final float H = region.getRegionHeight() * object.getScaleY();
		def.bounds.set(object.getX() - mapWidth/2f, mapHeight/2f - (object.getY() + H), W, H);
		
		// parse orientation
		final float ANGLE = (object.getRotation() % 360 + 360) % 360;
		
		// flip bounding box based on rotation
		final int ROT = MathUtils.round(ANGLE / 90);
		if(ROT == 1){
			def.bounds.set(def.bounds.x, def.bounds.y + def.bounds.height, def.bounds.height, def.bounds.width);
		}else if(ROT == 2){
			def.bounds.set(def.bounds.x - def.bounds.width, def.bounds.y + def.bounds.height, def.bounds.width, def.bounds.height);
		}else if(ROT == 3){
			def.bounds.set(def.bounds.x - def.bounds.height, def.bounds.y + def.bounds.height - def.bounds.width, def.bounds.height, def.bounds.width);
		}
		
		// convert to X/Z plan
		def.orientation = (ROT + 1) % 4;
		
		return def;
	}
	
	private MapObjectDef parseDef(final MapObjectDef def, final RectangleMapObject object/*, int checkpointNumber*/){
		// parse bounds
//		TextureRegion region = object.getTile().getTextureRegion();
//		float w = region.getRegionWidth() * object.getScaleX();
//		float h = region.getRegionHeight() * object.getScaleY();
		def.bounds.set(object.getRectangle().getX() - mapWidth/2f, mapHeight/2f - (object.getRectangle().getY() + object.getRectangle().getHeight()), object.getRectangle().getWidth(), object.getRectangle().getHeight());
		
		// parse orientation
//		float angle = (object.getRotation() % 360 + 360) % 360;
		
		// flip bounding box based on rotation
//		int rot = MathUtils.round(angle / 90);
//		if(rot == 1){
//			def.bounds.set(def.bounds.x, def.bounds.y + def.bounds.height, def.bounds.height, def.bounds.width);
//		}else if(rot == 2){
//			def.bounds.set(def.bounds.x - def.bounds.width, def.bounds.y + def.bounds.height, def.bounds.width, def.bounds.height);
//		}else if(rot == 3){
//			def.bounds.set(def.bounds.x - def.bounds.height, def.bounds.y + def.bounds.height - def.bounds.width, def.bounds.height, def.bounds.width);
//		}
		
		// convert to X/Z plan
//		def.orientation = (rot + 1) % 4;
		
		return def;
	}

}
