package model.umbi;

import engine.Resources;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import render.light.DirectionalLight;
import render.light.Light;
import render.light.PointLight;
import render.light.SpotLight;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UmbiLoader {

	public static UmbiScene loadScene(String path) {
		try {

			UmbiScene umbiScene = new UmbiScene();
			JSONParser jsonParser = new JSONParser();
			JSONObject all = (JSONObject) jsonParser.parse(Resources.getAsString(path));

			//	@SuppressWarnings("unchecked")
			if (all.containsKey("entities")) {
				JSONObject entityModels = (JSONObject) all.get("entities");
				Set<String> modelFiles = entityModels.keySet();
				for (String modelFile : modelFiles) {
					JSONArray entities = (JSONArray) entityModels.get(modelFile);
					for (Object entityObj : entities) {
						JSONObject entity = (JSONObject) entityObj;
						UmbiEntity umbiEntity = new UmbiEntity();
						if (entity.containsKey("position")) {
							umbiEntity.setPosition(JSONUtils.getVec3f(entity, "position"));
						}
						if (entity.containsKey("rotation")) {
							umbiEntity.setRotation(JSONUtils.getVec3f(entity, "rotation"));
						}
						if (entity.containsKey("scale")) {
							umbiEntity.setScale(JSONUtils.getVec3f(entity, "scale"));
						}
						umbiScene.addEntity(modelFile, umbiEntity);
					}
				}
			}
			if (all.containsKey("lights")) {
				for (Light light : processLights(all)) {
					umbiScene.addLight(light);
				}
			}
			JSONObject entryPoint = (JSONObject) all.get("entryPoint");
			umbiScene.setEntryPoint(
					JSONUtils.getVec3f(entryPoint, "position"),
					JSONUtils.getVec2f(entryPoint, "rotation")
			);
			return umbiScene;

		} catch (ParseException e) {
			throw new RuntimeException("Non riesco a caricare questa scene");
		}
	}

	public static UmbiModel loadModel(String path) {
		try {

			JSONParser jsonParser = new JSONParser();
			JSONObject all = (JSONObject) jsonParser.parse(Resources.getAsString(path));
			UmbiModel umbiModel = new UmbiModel(
					JSONUtils.getString(all, "name"),
					JSONUtils.getVec3fBuffer(all, "positions"),
					JSONUtils.getVec2fBuffer(all, "texCoords"),
					JSONUtils.getVec3fBuffer(all, "normals")
			);
			if (all.containsKey("materials"))
				processMaterials(umbiModel, all);

			processGeometries(umbiModel, all);

			if (all.containsKey("lights")) {
				for (Light light : processLights(all)) {
					umbiModel.addLight(light);
				}
			}

			return umbiModel;

		} catch (ParseException e) {
			throw new RuntimeException("Non riesco a caricare questo modello");
		}
	}

	private static List<Light> processLights(JSONObject model) {
		List<Light> lightsList = new LinkedList<>();
		JSONArray lights = (JSONArray) model.get("lights");
		for (Object lightObj : lights) {
			String lightType = JSONUtils.getString((JSONObject) lightObj, "type");
			if (lightType.equals("directional")) {
				lightsList.add(new DirectionalLight(
						JSONUtils.getVec3f((JSONObject) lightObj, "direction"),
						JSONUtils.getVec3f((JSONObject) lightObj, "ambient"),
						JSONUtils.getVec3f((JSONObject) lightObj, "diffuse"),
						JSONUtils.getVec3f((JSONObject) lightObj, "specular")
				));
			} else if (lightType.equals("point")) {
				lightsList.add(new PointLight(
						JSONUtils.getVec3f((JSONObject) lightObj, "position"),
						JSONUtils.getVec3f((JSONObject) lightObj, "ambient"),
						JSONUtils.getVec3f((JSONObject) lightObj, "diffuse"),
						JSONUtils.getVec3f((JSONObject) lightObj, "specular"),
						JSONUtils.getFloat((JSONObject) lightObj, "constantAttenuation"),
						JSONUtils.getFloat((JSONObject) lightObj, "linearAttenuation"),
						JSONUtils.getFloat((JSONObject) lightObj, "quadraticAttenuation")
				));
			} else if (lightType.equals("spot")) {
				lightsList.add(new SpotLight(
						JSONUtils.getVec3f((JSONObject) lightObj, "position"),
						JSONUtils.getVec3f((JSONObject) lightObj, "direction"),
						JSONUtils.getFloat((JSONObject) lightObj, "cutOffAngle"),
						JSONUtils.getFloat((JSONObject) lightObj, "outerCutOffAngle"),
						JSONUtils.getVec3f((JSONObject) lightObj, "ambient"),
						JSONUtils.getVec3f((JSONObject) lightObj, "diffuse"),
						JSONUtils.getVec3f((JSONObject) lightObj, "specular"),
						JSONUtils.getFloat((JSONObject) lightObj, "constantAttenuation"),
						JSONUtils.getFloat((JSONObject) lightObj, "linearAttenuation"),
						JSONUtils.getFloat((JSONObject) lightObj, "quadraticAttenuation")
				));
			} else {
				throw new IllegalStateException("Tipo di luce sconosciuto");
			}
		}
		return lightsList;
	}

	private static void processGeometries(UmbiModel umbiModel, JSONObject model) {
		JSONObject geometries = (JSONObject) model.get("geometries");
		for (String geometryName : (Set<String>) geometries.keySet()) {
			JSONObject geometry = (JSONObject) geometries.get(geometryName);
			umbiModel.addGeometry(
					geometryName,
					new UmbiGeometry(
							geometryName,
							(String) geometry.get("material"),
							JSONUtils.getVec3iBuffer(geometry, "indices")
					)
			);
		}
	}

	private static void processMaterials(UmbiModel umbiModel, JSONObject model) {
		JSONObject materials = (JSONObject) model.get("materials");
		for (String materialName : (Set<String>) materials.keySet()) {
			JSONObject material = (JSONObject) materials.get(materialName);
			umbiModel.addMaterial(
					materialName,
					new UmbiMaterial(
							materialName,
							JSONUtils.getVec4f(material, "diffuse"),
							JSONUtils.getVec4f(material, "specular"),
							JSONUtils.getFloat(material, "shininess")
					)
			);
		}
	}


}
