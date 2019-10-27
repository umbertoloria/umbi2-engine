package render.light;

import org.joml.Vector3f;

import java.util.StringJoiner;

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

	public String toString() {
		return new StringJoiner(", ", DirectionalLight.class.getSimpleName() + "[", "]")
				.add("direction=" + direction)
				.add("ambient=" + ambient)
				.add("diffuse=" + diffuse)
				.add("specular=" + specular)
				.toString();
	}

}
