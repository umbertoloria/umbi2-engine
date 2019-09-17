package model.wavefront;

import java.util.*;

public class WavefrontModel {

	public final String name;
	public final float[] positions, texCoords, normals;
	private Map<String, WavefrontMaterial> materials = new HashMap<>();
	private List<WavefrontGeometry> geometries = new LinkedList<>();

	public WavefrontModel(String name, float[] positions, float[] texCoords, float[] normals) {
		this.name = name;
		this.positions = positions;
		this.texCoords = texCoords;
		this.normals = normals;
	}

	public void addGeometry(WavefrontGeometry geometry) {
		geometries.add(geometry);
//		System.out.println("add geometry " + geometry.getPseudoName());
	}

	public List<WavefrontGeometry> getGeometries() {
		return geometries;
	}

	public void addMaterial(WavefrontMaterial material) {
		materials.put(material.name, material);
	}

	public Set<String> getMaterialNames() {
		return materials.keySet();
	}

	public WavefrontMaterial getMaterial(String name) {
		return materials.get(name);
	}

}
