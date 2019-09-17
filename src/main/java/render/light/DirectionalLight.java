package render.light;

import org.joml.Vector3f;

public class DirectionalLight implements Light {

	private Vector3f direction, ambient, diffuse, specular;

	public DirectionalLight(Vector3f direction, Vector3f ambient, Vector3f diffuse, Vector3f specular) {
		this.direction = direction;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public Vector3f getAmbient() {
		return ambient;
	}

	public Vector3f getDiffuse() {
		return diffuse;
	}

	public Vector3f getSpecular() {
		return specular;
	}

}
