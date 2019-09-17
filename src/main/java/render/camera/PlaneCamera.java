package render.camera;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PlaneCamera implements Camera {

	private Matrix4f viewMatrix = new Matrix4f();
	protected Vector3f position = new Vector3f();
	private float yaw = 0;
	private float pitch = 0;
	private Vector3f front = new Vector3f();
	private Vector3f up = new Vector3f();
	private Vector3f right = new Vector3f();

	public PlaneCamera() {
		calculateMatrices();
	}

	public void setPosition(Vector3f position) {
		this.position.set(position);
		calculateMatrices();
	}

	public void setRotation(float pitch, float yaw) {
		if (pitch > 89) pitch = 89;
		else if (pitch < -89) pitch = -89;
		this.pitch = pitch;
		this.yaw = yaw;
		calculateMatrices();
	}

	public void turnUp(float up) {
		pitch += up;
		if (pitch > 89) pitch = 89;
		else if (pitch < -89) pitch = -89;
		calculateMatrices();
	}

	public void turnRight(float right) {
		yaw += right;
		calculateMatrices();
	}

	public void move(float frontAmount, float rightAmount) {
		position.add(front.x * frontAmount, front.y * frontAmount, front.z * frontAmount);
		position.add(right.x * rightAmount, right.y * rightAmount, right.z * rightAmount);
		calculateMatrices();
	}

	public void addHeight(float amount) {
		position.y += amount;
		calculateMatrices();
	}

	private Vector3f cachedVector = new Vector3f();

	private void calculateMatrices() {

//		front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
//		front.y = (float) Math.sin(Math.toRadians(pitch));
//		front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

		float yaw_rad = (float) Math.toRadians(yaw);
		float pitch_rad = (float) Math.toRadians(pitch);

		float SIN_YAW = (float) Math.sin(yaw_rad);
		float COS_YAW = (float) Math.cosFromSin(SIN_YAW, yaw_rad);

		float SIN_PITCH = (float) Math.sin(pitch_rad);
		float COS_PITCH = (float) Math.cosFromSin(SIN_PITCH, pitch_rad);

		front.x = -SIN_YAW * COS_PITCH;
		front.y = SIN_PITCH;
		front.z = -COS_YAW * COS_PITCH;

		front.normalize();
		right = front.cross(worldUp, right).normalize();
		up = right.cross(front, up).normalize();

		viewMatrix.identity();
		cachedVector.set(position).add(front);
		viewMatrix.lookAt(position, cachedVector, up);
/*
		viewMatrix.translate(position);
		viewMatrix.rotateYXZ(
				(float) Math.toRadians(yaw),
				(float) Math.toRadians(pitch),
				0
		);
		viewMatrix.invert();
*/
	}

	public Vector3f getPosition() {
		return position;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

}
