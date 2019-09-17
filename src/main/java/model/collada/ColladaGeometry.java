package model.collada;

import model.Geometry;
import org.joml.Vector4f;

public class ColladaGeometry {

	private String name;
	private int[] singleIndices;
	// Usati prima per generare la geometry che viene passata, ma possono servire per ricostruire il formato integrale.
	private Geometry geometry;
	private String materialName;
	private Vector4f materialDiffuse = new Vector4f();

	public ColladaGeometry(String name, int[] singleIndices, Geometry geometry, String materialName,
	                       Vector4f materialDiffuse) {
		this.name = name;
		this.singleIndices = singleIndices;
		this.geometry = geometry;
		this.materialName = materialName;
		this.materialDiffuse.set(materialDiffuse);
	}

	public String getName() {
		return name;
	}

	public int[] getSingleIndices() {
		return singleIndices;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public String getMaterialName() {
		return materialName;
	}

	public Vector4f getMaterialDiffuse() {
		return new Vector4f(materialDiffuse);
	}

}
