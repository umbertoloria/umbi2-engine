package render;

import engine.Mesh;
import org.joml.Matrix4f;

public class Batch {

	public final Mesh mesh;
	public final Material material;
	public final Matrix4f transformation;

	public Batch(Mesh mesh, Material material, Matrix4f transformation) {
		this.mesh = mesh;
		this.material = material;
		this.transformation = transformation;
	}

}
