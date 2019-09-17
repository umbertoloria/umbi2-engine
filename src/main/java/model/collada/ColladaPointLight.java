package model.collada;

import org.joml.Vector3f;

public class ColladaPointLight {

	private Vector3f position;
	private Vector3f color;
	private float constantAtte;
	private float linearAtte;
	private float quadraticAtte;

	ColladaPointLight(Vector3f position, ColladaLightConfig lightConfig) {
		this.position = position;
		color = lightConfig.color;
		constantAtte = lightConfig.constantAtte;
		linearAtte = lightConfig.linearAtte;
		quadraticAtte = lightConfig.quadraticAtte;
	}

	// FIXME: Evita cloni
	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public Vector3f getColor() {
		return new Vector3f(color);
	}

	public float getConstantAttenuation() {
		return constantAtte;
	}

	public float getLinearAttenuation() {
		return linearAtte;
	}

	public float getQuadraticAttenuation() {
		return quadraticAtte;
	}

}
