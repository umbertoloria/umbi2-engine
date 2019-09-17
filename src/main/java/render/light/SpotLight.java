package render.light;

import org.joml.Vector3f;

public class SpotLight implements Light {

	private Vector3f position, direction;
	private float cutOffAngle, outerCutOffAngle;
	private Vector3f ambient, diffuse, specular;
	private float constant, linear, quadratic;

	public SpotLight(Vector3f position, Vector3f direction, float cutOffAngle, float outerCutOffAngle,
	                 Vector3f ambient, Vector3f diffuse, Vector3f specular, float constant, float linear,
	                 float quadratic) {
		this.position = position;
		this.direction = direction.normalize();
		this.cutOffAngle = cutOffAngle;
		this.outerCutOffAngle = outerCutOffAngle;
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

	public Vector3f getDirection() {
		return direction;
	}

	public float getCutOffAngle() {
		return cutOffAngle;
	}

	public float getOuterCutOffAngle() {
		return outerCutOffAngle;
	}

//	public float getCutOff() {
//		return (float) Math.cos(Math.toRadians(cutOffAngle));
//	}
//
//	public float getOuterCutOff() {
//		return (float) Math.cos(Math.toRadians(outerCutOffAngle));
//	}

	public Vector3f getAmbient() {
		return ambient;
	}

	public Vector3f getDiffuse() {
		return diffuse;
	}

	public Vector3f getSpecular() {
		return specular;
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

}
