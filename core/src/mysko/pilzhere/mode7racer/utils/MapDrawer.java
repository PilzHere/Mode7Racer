package mysko.pilzhere.mode7racer.utils;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class MapDrawer {
	
	private FrameBuffer generatedFBO;
	private Batch batch;
	private final Sprite sprite = new Sprite();
	
	public void begin(final int gridWidth, final int gridHeight, final int tileWidth, final int tileHeight) {
		
		final int width = gridWidth * tileWidth;
		final int height = gridHeight * tileHeight;
		
		generatedFBO = new FrameBuffer(Format.RGBA8888, width, height, false){
			@Override
			protected void disposeColorTexture(Texture colorTexture) {
				// don't dispose color texture when disposing FBO
			}
		};
		
		batch = new SpriteBatch();		
		generatedFBO.begin();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, gridWidth, gridHeight);
		batch.begin();
	}
	
	public void drawTile(final int x, final int y, final Texture tile) {
		batch.draw(tile, x, y, 1f, 1f, 0f, 0f, 1f, 1f);
	}

	public void drawTiles(float x, float y, float w, float h, int orientation, Texture tile) {
		sprite.setSize(1, 1);
		sprite.setOriginCenter();
		sprite.setRotation(orientation * 90);
		sprite.setRegion(tile);
		for(int cy=0 ; cy<h ; cy++){
			for(int cx=0 ; cx<w ; cx++){
				sprite.setPosition(cx + x, cy + y);
				sprite.draw(batch);
			}
		}
		sprite.setTexture(null);
	}
	
	public void drawTiles(float x, float y, float width, float height, int orientation, Texture tile, Texture tileStart, Texture tileEnd) {
		sprite.setSize(1, 1);
		sprite.setOriginCenter();
		sprite.setRotation(orientation * 90);
		
		int w = (int)width;
		int h = (int)height;
		
		int startX = -1, startY = -1, endX = -1, endY = -1;
		if(orientation == 0){
			startX = 0;
			endX = w-1;
		}else if(orientation == 1){
			startY = 0;
			endY = h-1;
		}else if(orientation == 2){
			startX = w-1;
			endX = 0;
		}else if(orientation == 3){
			startY = h-1;
			endY = 0;
		}
		
		for(int cy=0 ; cy<h ; cy++){
			for(int cx=0 ; cx<w ; cx++){
				if(cx == startX || cy == startY){
					sprite.setRegion(tileStart);
				}else if(cx == endX || cy == endY){
					sprite.setRegion(tileEnd);
				}else{
					sprite.setRegion(tile);
				}
				sprite.setPosition(cx + x, cy + y);
				sprite.draw(batch);
			}
		}
		sprite.setTexture(null);
	}
	
	public Texture end() {
		batch.end();
		batch.dispose();
		batch = null;
		
		generatedFBO.end();
		
		final Texture texture = generatedFBO.getColorBufferTexture();
		generatedFBO.dispose();
		generatedFBO = null;
		
		return texture;
	}

}
