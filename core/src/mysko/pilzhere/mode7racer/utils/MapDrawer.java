package mysko.pilzhere.mode7racer.utils;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class MapDrawer {
	
	private FrameBuffer generatedFBO;
	private Batch batch;
	
	public void begin(final int gridWidth, final int gridHeight, final int tileWidth, final int tileHeight) {
		
		final int width = gridWidth * tileWidth;
		final int height = gridHeight * tileHeight;
		
		generatedFBO = new FrameBuffer(Format.RGB888, width, height, false){
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
