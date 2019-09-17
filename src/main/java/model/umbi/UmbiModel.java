package model.umbi;

import model.GeometryBuilder;
import render.light.Light;

import java.util.*;

public class UmbiModel {

	private String name;
	private float[] positions, texCoords, normals;
	private Map<String, UmbiMaterial> materials = new HashMap<>();
	private Map<String, UmbiGeometry> geometries = new HashMap<>();
	private List<Light> lights = new LinkedList<>();

	public UmbiModel(String name, float[] positions, float[] texCoords, float[] normals) {
		this.name = name;
		this.positions = positions;
		this.texCoords = texCoords;
		this.normals = normals;
	}

	public void addMaterial(String name, UmbiMaterial material) {
		if (materials.containsKey(name)) {
			throw new IllegalStateException("Material already set");
		} else {
			materials.put(name, material);
		}
	}

	public void addGeometry(String name, UmbiGeometry geometry) {
		if (geometries.containsKey(name)) {
			throw new IllegalStateException("Geometry already set");
		} else {
			geometries.put(name, geometry);
		}
	}

	public void addLight(Light light) {
		lights.add(light);
	}

	public Light[] getLights() {
		return lights.toArray(new Light[0]);
	}

	public UmbiPicture[] getPictures() {
		List<UmbiPicture> pictures = new ArrayList<>();
		for (String geometryName : geometries.keySet()) {
			UmbiGeometry umbiGeometry = geometries.get(geometryName);
			pictures.add(
					new UmbiPicture(
							GeometryBuilder.buildGeometry(positions, texCoords, normals, umbiGeometry.indices),
							materials.get(umbiGeometry.materialName)
					)
			);
		}
		return pictures.toArray(new UmbiPicture[0]);
	}

	public float[] getPositions() {
		return positions;
	}

	public float[] getTexCoords() {
		return texCoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("{\n\t\"name\": \"").append(name).append("\",\n");
		// POSITIONS
		StringBuilder app = new StringBuilder();
		{
			app.append("\"positions\": [\n");
			for (int i = 0; i < positions.length; i += 3) {
				app.append("\t");
				JSONUtils.jsonVec3fAppend(app, positions[i], positions[i + 1], positions[i + 2]);
				app.append(",\n");
			}
			app.delete(app.length() - 2, app.length() - 1);
			app.append(']');
			JSONUtils.indent(app);
			str.append(app);
		}
		// TEXTURE COORDINATES
		{
			str.append(",\n");
			app.delete(0, app.length());
			app.append("\"texCoords\": [\n");
			for (int i = 0; i < texCoords.length; i += 2) {
				app.append("\t");
				JSONUtils.jsonVec2fAppend(app, texCoords[i], texCoords[i + 1]);
				app.append(",\n");
			}
			app.delete(app.length() - 2, app.length() - 1);
			app.append(']');
			JSONUtils.indent(app);
			str.append(app);
		}
		// NORMALS
		{
			str.append(",\n");
			app.delete(0, app.length());
			app.append("\"normals\": [\n");
			for (int i = 0; i < normals.length; i += 3) {
				app.append("\t");
				JSONUtils.jsonVec3fAppend(app, normals[i], normals[i + 1], normals[i + 2]);
				app.append(",\n");
			}
			app.delete(app.length() - 2, app.length() - 1);
			app.append(']');
			JSONUtils.indent(app);
			str.append(app);
		}
		// MATERIALS
		if (!materials.isEmpty()) {
			str.append(",\n");
			app.delete(0, app.length());
			app.append("\"materials\": {\n");
			for (String materialName : materials.keySet()) {
				StringBuilder matJ = new StringBuilder(materials.get(materialName).toJSON());
				JSONUtils.indent(matJ);
				app.append(matJ);
				app.append(",\n");
			}
			app.delete(app.length() - 2, app.length() - 1);
			app.append("}");
			JSONUtils.indent(app);
			str.append(app);
		}
		// GEOMETRIES
		if (!geometries.isEmpty()) {
			str.append(",\n");
			app.delete(0, app.length());
			app.append("\"geometries\": {\n");
			for (String geometryName : geometries.keySet()) {
				UmbiGeometry geometry = geometries.get(geometryName);
				StringBuilder geoJ = new StringBuilder(geometry.toJSON());
				JSONUtils.indent(geoJ);
				app.append(geoJ);
				app.append(",\n");
			}
			app.delete(app.length() - 2, app.length() - 1);
			app.append("}");
			JSONUtils.indent(app);
			str.append(app);
		}
		// LIGHTS
		if (lights.size() > 0) {
			str.append(",\n");
			app.delete(0, app.length());
			{
				app.append("\"lights\": [\n");
				for (Light light : lights) {
					StringBuilder lig = new StringBuilder(UmbiJSON.lightToJSON(light));
					JSONUtils.indent(lig);
					app.append(lig);
					app.append(",\n");
				}
				app.delete(app.length() - 2, app.length() - 1);
				app.append("]");
			}
			JSONUtils.indent(app);
			str.append(app);
		}
		str.append("\n}");
		return str.toString();
	}

}
