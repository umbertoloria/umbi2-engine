package render.cool_meshes;

import engine.Mesh;
import engine.Shader;
import model.Geometry;

/**
 I vertici di una ColouredMesh sono composti da:
 - position (vec3)
 - normal (vec3) */
public class ColouredMesh extends Mesh {

	private static Layout layout = new Layout(
			new Element("position", Shader.Type.Float3),
			new Element("normal", Shader.Type.Float3)
	);

	public ColouredMesh(Geometry geometry) {
		super(geometry.getVertices(false, true), geometry.getIndices(), layout);
	}

}
