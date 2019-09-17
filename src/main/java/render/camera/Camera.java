package render.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface Camera {

	Vector3f worldUp = new Vector3f(0, 1, 0);

	Vector3f getPosition();

	Matrix4f getViewMatrix();

}
