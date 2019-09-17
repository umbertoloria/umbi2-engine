package render.light;

import org.joml.Vector3f;

public class PointLight implements Light {

	private Vector3f position;
	private Vector3f ambient, diffuse, specular;
	private float constant, linear, quadratic;

	public PointLight(Vector3f position, Vector3f ambient, Vector3f diffuse, Vector3f specular, float constant,
	                  float linear, float quadratic) {
		this.position = position;

		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;

		this.constant = constant;
		this.linear = linear;
		this.quadratic = quadratic;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getConstantAttenuation() {
		return constant;
	}

	public float getLinearAttenuation() {
		return linear;
	}

	public float getQuadraticAttenuation() {
		return quadratic;
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
