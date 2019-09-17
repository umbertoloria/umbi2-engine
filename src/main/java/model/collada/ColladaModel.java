package model.collada;

import java.util.ArrayList;
import java.util.List;

public class ColladaModel {

	public final float[] positions, texCoords, normals;

	ColladaModel(float[] positions, float[] texCoords, float[] normals) {
		this.positions = positions;
		this.texCoords = texCoords;
		this.normals = normals;
	}

	private List<ColladaGeometry> geometries = new ArrayList<>();

	public void add(ColladaGeometry geometry) {
		geometries.add(geometry);
	}

	public List<ColladaGeometry> getGeometries() {
		return geometries;
	}

}
