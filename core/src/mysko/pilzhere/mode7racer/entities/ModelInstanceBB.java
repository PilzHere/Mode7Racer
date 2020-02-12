package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class ModelInstanceBB extends ModelInstance {
	public final Vector3 center = new Vector3();
	public final Vector3 dimensions = new Vector3();
	public final float radius;

	public final BoundingBox bounds = new BoundingBox();

	public ModelInstanceBB(final Model model) {
		super(model);
		calculateBoundingBox(bounds);
		bounds.getCenter(center);
		bounds.getDimensions(dimensions);
		radius = dimensions.len() / 2f;
	}
}