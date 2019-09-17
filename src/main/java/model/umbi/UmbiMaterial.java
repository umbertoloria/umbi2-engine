package model.umbi;

import org.joml.Vector4f;

public class UmbiMaterial {

	private String name;
	private Vector4f diffuse;
	private Vector4f specular;
	private float shininess;

	public UmbiMaterial(String name, Vector4f diffuse, Vector4f specular, float shininess) {
		this.name = name;
		this.diffuse = diffuse;
		this.specular = specular;
		this.shininess = shininess;
	}

	public Vector4f getDiffuse() {
		return new Vector4f(diffuse);
	}

	public Vector4f getSpecular() {
		return new Vector4f(specular);
	}

	public float getShininess() {
		return shininess;
	}

	public String toJSON() {
		return "\"" + name + "\": {\n\t"
				+ JSONUtils.jsonVec4("diffuse", diffuse) + ",\n\t"
				+ JSONUtils.jsonVec4("specular", specular) + ",\n\t"
				+ JSONUtils.jsonFloat("shininess", shininess) + "\n}";
	}

}
