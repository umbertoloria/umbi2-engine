package render.cool_pictures;

import model.Geometry;
import org.joml.Vector4f;
import render.Picture;
import render.cool_materials.ColouredMaterial;
import render.cool_meshes.ColouredMesh;

public class ColouredPicture extends Picture {

	public ColouredPicture(Geometry geometry, Vector4f diffuse, Vector4f specular, float shininess) {
		super(
				new ColouredMesh(geometry),
				new ColouredMaterial(diffuse, specular, shininess)
		);
	}

}
