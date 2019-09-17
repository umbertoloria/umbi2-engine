package render.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class StandardCamera implements Camera {

	private Vector3f position = new Vector3f();
	private Vector3f rotation = new Vector3f();
	private Matrix4f viewMatrix = new Matrix4f();

	public void setPosition(Vector3f position) {
		this.position.set(position);
		calculateMatrices();
	}

	public void setRotation(Vector3f rotation) {
		this.rotation.set(rotation);
		calculateMatrices();
	}

	private void calculateMatrices() {
		viewMatrix.identity();
		viewMatrix.translate(position);
		viewMatrix.rotateYXZ(
				(float) Math.toRadians(rotation.y),
				(float) Math.toRadians(rotation.x),
				(float) Math.toRadians(rotation.z)
		);
		viewMatrix.invert();
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

}
