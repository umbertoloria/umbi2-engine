package model.converter;

import model.collada.ColladaGeometry;
import model.collada.ColladaModel;
import model.collada.ColladaPointLight;
import model.collada.ColladaScene;
import model.umbi.UmbiGeometry;
import model.umbi.UmbiMaterial;
import model.umbi.UmbiModel;
import org.joml.Vector3f;
import org.joml.Vector4f;
import render.light.Light;
import render.light.PointLight;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DAE2Umbi {

	public static List<UmbiModel> convertModels(ColladaScene colladaScene) {
		List<UmbiModel> models = new LinkedList<>();
		for (String modelName : colladaScene.getModelNames()) {
			ColladaModel colladaModel = colladaScene.getModel(modelName);
			UmbiModel umbiModel = new UmbiModel(
					modelName, colladaModel.positions, colladaModel.texCoords, colladaModel.normals
			);
			// Caricare i materiali
			Map<String, Vector4f> materials = new HashMap<>();
			for (ColladaGeometry colladaGeometry : colladaModel.getGeometries()) {
				String materialName = colladaGeometry.getMaterialName();
				Vector4f materialDiffuse = colladaGeometry.getMaterialDiffuse();
				if (!materials.containsKey(materialName)) {
					materials.put(materialName, materialDiffuse);
				} else if (!materials.get(materialName).equals(materialDiffuse)) {
					throw new IllegalStateException("Materiali con lo stesso nome hanno colori diversi");
				}
			}
			Vector4f black = new Vector4f();
			for (String materialName : materials.keySet()) {
				umbiModel.addMaterial(materialName,
						new UmbiMaterial(materialName, materials.get(materialName), black, 0)
				);
			}
			// Caricare le geometrie
			for (ColladaGeometry colladaGeometry : colladaModel.getGeometries()) {
				umbiModel.addGeometry(
						colladaGeometry.getName(),
						new UmbiGeometry(
								colladaGeometry.getName(),
								colladaGeometry.getMaterialName(),
								colladaGeometry.getSingleIndices()
						)
				);
			}
			models.add(umbiModel);
		}
		return models;
	}

	public static List<Light> convertLights(ColladaScene colladaScene) {
		List<Light> lights = new LinkedList<>();
		// Caricare le luci
		Vector3f black = new Vector3f();
		for (int i = 0; i < colladaScene.getLightsCount(); i++) {
			ColladaPointLight colladaPointLight = colladaScene.getLight(i);
			lights.add(
					new PointLight(
							colladaPointLight.getPosition(),
							black,
							colladaPointLight.getColor(),
							black,
							colladaPointLight.getConstantAttenuation(),
							colladaPointLight.getLinearAttenuation(),
							colladaPointLight.getQuadraticAttenuation()
					)
			);
		}
		return lights;
	}

}
