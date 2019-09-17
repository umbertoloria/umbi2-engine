package model;

import engine.Triangolo;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class Geometry {

	private float[] vertices;
	private int[] indices;
	private boolean hasTexCoords, hasNormals;

	public Geometry(float[] vertices, int[] indices, boolean hasTexCoords, boolean hasNormals) {
		this.vertices = vertices;
		this.indices = indices;
		this.hasTexCoords = hasTexCoords;
		this.hasNormals = hasNormals;
	}

	private Map<Integer, float[]> verticesCache = new HashMap<>();

	private static final int TCOORDS = 0b01;
	private static final int NORMALS = 0b10;

	public float[] getVertices(boolean texCoords, boolean normals) {
		int code = 0;
		if (texCoords) code |= TCOORDS;
		if (normals) code |= NORMALS;
		if (!verticesCache.containsKey(code)) {
			int size = 3;
			if ((code & TCOORDS) != 0) size += 2;
			if ((code & NORMALS) != 0) size += 3;
			int j = 0;
			float[] result = new float[vertices.length / 8 * size];
			for (int i = 0; i < vertices.length; i += 8) {
				result[j++] = vertices[i];
				result[j++] = vertices[i + 1];
				result[j++] = vertices[i + 2];
				if (texCoords) {
					result[j++] = vertices[i + 3];
					result[j++] = vertices[i + 4];
				}
				if (normals) {
					result[j++] = vertices[i + 5];
					result[j++] = vertices[i + 6];
					result[j++] = vertices[i + 7];
				}
			}

			verticesCache.put(code, result);
		}
		return verticesCache.get(code);
	}

	public int[] getIndices() {
		return indices;
	}

	public Triangolo[] getTriangles() {
		assert hasTexCoords && hasNormals;
		Triangolo[] result = new Triangolo[indices.length / 3];
		for (int i = 0; i < result.length; i++) {
			Vector3f a = new Vector3f(
					vertices[indices[i * 3] * 3],
					vertices[indices[i * 3] * 3 + 1],
					vertices[indices[i * 3] * 3 + 2]
			);
			Vector3f b = new Vector3f(
					vertices[indices[i * 3 + 1] * 3 + 3],
					vertices[indices[i * 3 + 1] * 3 + 4],
					vertices[indices[i * 3 + 1] * 3 + 5]
			);
			Vector3f c = new Vector3f(
					vertices[indices[i * 3 + 2] * 3 + 6],
					vertices[indices[i * 3 + 2] * 3 + 7],
					vertices[indices[i * 3 + 2] * 3 + 8]
			);
			result[i] = new Triangolo(a, b, c);
		}
		return result;
	}

}
