package render.cool_meshes;

import engine.Mesh;
import engine.Shader;
import model.Geometry;

/**
 I vertici di una TexturedMesh sono composti da:
 - position (vec3)
 - texCoord (vec2) */
public class TexturedMesh extends Mesh {

	private static Layout layout = new Layout(
			new Element("position", Shader.Type.Float3),
			new Element("texCoord", Shader.Type.Float2)
	);

	public TexturedMesh(Geometry geometry) {
		super(geometry.getVertices(true, false), geometry.getIndices(), layout);
	}

}
