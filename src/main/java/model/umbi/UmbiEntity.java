package model.umbi;

import org.joml.Vector3f;

public class UmbiEntity {

	private Vector3f position = new Vector3f();
	private Vector3f rotation = new Vector3f();
	private Vector3f scale = new Vector3f(1);

	public void setPosition(Vector3f position) {
		this.position.set(position);
	}

	public void setRotation(Vector3f rotation) {
		this.rotation.set(rotation);
	}

	public void setScale(Vector3f scale) {
		this.scale.set(scale);
	}

	public String toJSON() {
		return "{\n\t"
				+ JSONUtils.jsonVec3("position", position) + ",\n\t"
				+ JSONUtils.jsonVec3("rotation", rotation) + "\n\t"
				+ JSONUtils.jsonVec3("scale", scale) + "\n}";
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

}
