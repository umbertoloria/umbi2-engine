package render.cool_meshes;

import engine.Mesh;
import engine.Shader;

public class QuadMesh extends Mesh {

	private static Layout layout = new Layout(
			new Element("position", Shader.Type.Float3),
			new Element("texCoord", Shader.Type.Float2)
	);

	public QuadMesh() {
		super(
				new float[]{
						0, 0, 0, 0, 0, // top left
						0, 1, 0, 0, 1, // bottom left
						1, 0, 0, 1, 0, // top right
						1, 1, 0, 1, 1, // bottom right
				},
				new int[]{
						0, 2, 3, // triangolo superiore
						0, 1, 3  // triangolo inferiore
				},
				layout
		);
	}

}
