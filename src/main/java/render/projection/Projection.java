package render.projection;

import org.joml.Matrix4f;

public interface Projection {

	Matrix4f getProjectionMatrix(float aspectRatio);

}
