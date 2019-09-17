package render.cool_meshes;

import engine.Mesh;
import engine.Shader;
import model.Geometry;

/**
 I vertici di una MaterialMesh sono composti da:
 - position (vec3)
 - texCoord (vec2)
 - normal (vec3) */
public class MaterialMesh extends Mesh {

	private static Layout layout = new Layout(
			new Element("position", Shader.Type.Float3),
			new Element("texCoord", Shader.Type.Float2),
			new Element("normal", Shader.Type.Float3)
	);

	public MaterialMesh(Geometry geometry) {
		super(geometry.getVertices(true, true), geometry.getIndices(), layout);
	}

}
