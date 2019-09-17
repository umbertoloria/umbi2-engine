package model;

import java.util.*;

public class GeometryBuilder {

	private static class Vertex {

		final float posx, posy, posz;
		final float tcox, tcoy;
		final float normx, normy, normz;

		Vertex(float posx, float posy, float posz, float tcox, float tcoy, float normx, float normy, float normz) {
			this.posx = posx;
			this.posy = posy;
			this.posz = posz;
			this.tcox = tcox;
			this.tcoy = tcoy;
			this.normx = normx;
			this.normy = normy;
			this.normz = normz;
		}

		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Vertex vertex = (Vertex) o;
			return Objects.equals(posx, vertex.posx) &&
					Objects.equals(posy, vertex.posy) &&
					Objects.equals(posz, vertex.posz) &&
					Objects.equals(tcox, vertex.tcox) &&
					Objects.equals(tcoy, vertex.tcoy) &&
					Objects.equals(normx, vertex.normx) &&
					Objects.equals(normy, vertex.normy) &&
					Objects.equals(normz, vertex.normz);
		}

		public int hashCode() {
			return Objects.hash(posx, posy, posz, tcox, tcoy, normx, normy, normz);
		}

	}

	public static Geometry buildGeometry(float[] positions, float[] texCoords, float[] normals, int[] singleIndices) {
		Map<Vertex, Integer> cache = new HashMap<>();
		List<Vertex> thickVertices = new LinkedList<>();
		List<Integer> indices = new LinkedList<>();
		for (int i = 0; i < singleIndices.length; i += 3) {
			int positionIndex = singleIndices[i] * 3;
			float posx = positions[positionIndex];
			float posy = positions[positionIndex + 1];
			float posz = positions[positionIndex + 2];
			int texCoordIndex = singleIndices[i + 1] * 2;
			float tcoordx = texCoords[texCoordIndex];
			float tcoordy = texCoords[texCoordIndex + 1];
			int normalIndex = singleIndices[i + 2] * 3;
			float normx = normals[normalIndex];
			float normy = normals[normalIndex + 1];
			float normz = normals[normalIndex + 2];
			Vertex vertex = new Vertex(
					posx, posy, posz,
					tcoordx, tcoordy,
					normx, normy, normz
			);
			if (!cache.containsKey(vertex)) {
				cache.put(vertex, thickVertices.size());
				thickVertices.add(vertex);
			}
			indices.add(cache.get(vertex));
		}
		float[] vertices = new float[thickVertices.size() * 8];
		int i = 0;
		for (Vertex thickVertex : thickVertices) {
			vertices[i++] = thickVertex.posx;
			vertices[i++] = thickVertex.posy;
			vertices[i++] = thickVertex.posz;
			vertices[i++] = thickVertex.tcox;
			vertices[i++] = thickVertex.tcoy;
			vertices[i++] = thickVertex.normx;
			vertices[i++] = thickVertex.normy;
			vertices[i++] = thickVertex.normz;
		}
		int[] indicesArr = new int[indices.size()];
		i = 0;
		for (Integer index : indices) {
			indicesArr[i++] = index;
		}
		return new Geometry(vertices, indicesArr, true, true);
	}

}
