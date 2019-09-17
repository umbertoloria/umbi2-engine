package render.projection;

import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

public class Perspective implements Projection {

	private Map<Float, Matrix4f> projectionMatrices = new HashMap<>();
	private float fovRad, near, far;

	public Perspective(float fov, float near, float far) {
		this.fovRad = (float) Math.toRadians(fov);
		this.near = near;
		this.far = far;
	}

	public Matrix4f getProjectionMatrix(float aspect) {
		if (!projectionMatrices.containsKey(aspect)) {
			projectionMatrices.put(aspect,
					new Matrix4f().perspective(fovRad, aspect, near, far)
			);
		}
		return projectionMatrices.get(aspect);
	}

}
