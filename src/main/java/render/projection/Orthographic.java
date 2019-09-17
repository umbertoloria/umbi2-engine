package render.projection;

import org.joml.Matrix4f;

public class Orthographic implements Projection {

	private Matrix4f projectionMatrix = new Matrix4f();

	public Orthographic(float left, float right, float bottom, float top, float zNear, float zFar) {
		projectionMatrix.ortho(left, right, bottom, top, zNear, zFar);
	}

	public Matrix4f getProjectionMatrix(float aspect) {
		// TODO: Usare aspect
		return projectionMatrix;
	}

}
