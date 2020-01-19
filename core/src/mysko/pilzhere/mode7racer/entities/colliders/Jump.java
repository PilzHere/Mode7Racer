package mysko.pilzhere.mode7racer.entities.colliders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.mode7racer.entities.Entity;
import mysko.pilzhere.mode7racer.entities.ModelInstanceBB;
import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Jump extends Entity{
	
	private Model mdlFlat;
	private ModelInstanceBB mdlInst;
	private Texture tex;
	
	public Jump(GameScreen screen, Vector3 position, float posX, float posZ, float width, float height) {
		super(screen, position);
		
		tex = screen.assMan.get("jumpHorizontal01.png");
		
		ModelBuilder modelBuilder = new ModelBuilder();
		mdlFlat = modelBuilder.createBox(1, 0, 1,
				new Material(TextureAttribute.createDiffuse(tex)),
				Usage.Position | Usage.TextureCoordinates);
		
		
		screen.getCurrentMap().getMdlInstances().add(mdlInst = new ModelInstanceBB(mdlFlat));
		mdlInst.transform.rotate(Vector3.Y, 90);
		mdlInst.transform.setToTranslation(position.cpy().add(0.5f, 0, -0.5f)); // - in z because we rotated world model.
		
		rect = new Rectangle(posX, posZ, width, height);
	}
}
