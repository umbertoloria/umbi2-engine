package model.converter;

import model.umbi.UmbiGeometry;
import model.umbi.UmbiMaterial;
import model.umbi.UmbiModel;
import model.wavefront.WavefrontGeometry;
import model.wavefront.WavefrontMaterial;
import model.wavefront.WavefrontModel;
import org.joml.Vector4f;

public class OBJ2Umbi {

	public static UmbiModel convertModel(WavefrontModel objModel) {
		UmbiModel model = new UmbiModel(objModel.name, objModel.positions, objModel.texCoords, objModel.normals);
		for (String materialName : objModel.getMaterialNames()) {
			WavefrontMaterial material = objModel.getMaterial(materialName);
			model.addMaterial(
					materialName,
					new UmbiMaterial(
							materialName,
							new Vector4f(material.kd, 1),
							new Vector4f(material.ks, 1),
							material.ns
					)
			);
		}
		for (WavefrontGeometry geometry : objModel.getGeometries()) {
			model.addGeometry(
					geometry.materialName,
					new UmbiGeometry(
							geometry.materialName,
							geometry.materialName,
							geometry.indices
					)
			);
		}
		/*
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
		}*/
		return model;
	}

}
