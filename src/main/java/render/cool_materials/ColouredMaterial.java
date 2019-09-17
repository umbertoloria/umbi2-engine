package render.cool_materials;

import org.joml.Vector4f;
import render.Material;
import render.cool_shaders.ColouredShader;

public class ColouredMaterial implements Material<ColouredShader> {

	private Vector4f diffuse = new Vector4f();
	private Vector4f specular = new Vector4f();
	private float shininess;

	public ColouredMaterial(Vector4f diffuse, Vector4f specular, float shininess) {
		this.diffuse.set(diffuse);
		this.specular.set(specular);
		this.shininess = shininess;
		getShader();
	}

	public ColouredShader getShader() {
		return ColouredShader.load();
	}

	public void bind(ColouredShader shader) {
		shader.setMaterialDiffuse(diffuse);
		shader.setMaterialSpecular(specular);
		shader.setMaterialShininess(shininess);
	}

	public void unbind() {
	}

	public void destroy() {
		getShader().destroy();
	}

}
