package model.collada;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class ColladaLoader {

	////////////// MODELS //////////////

	private static final Map<String, ColladaScene> loadedModels = new HashMap<>();

	public static ColladaScene loadScene(String path) {
		if (!loadedModels.containsKey(path)) {
			loadedModels.put(path, loadScene(XMLUtils.getRootOfXMLFile(path)));
		}
		return loadedModels.get(path);
	}

	private static ColladaScene loadScene(Node root) {
		// Loading materials
		Map<String, Vector4f> mat2color = getMaterialColors(root);
		Node libraryGeometries = XMLUtils.getFirstChildWithTagName(root, "library_geometries");
		assert libraryGeometries != null;
		ColladaScene colladaScene = new ColladaScene();
		// Models
		for (Node geometryNode : XMLUtils.getChildsWithTagName(libraryGeometries, "geometry")) {
			colladaScene.addModel(
					XMLUtils.getValueOfAttribute(geometryNode, "name"),
					CXMLMesh.getColladaModel(geometryNode, mat2color)
			);
		}
		// Lights
		Map<String, ColladaLightConfig> lightConfigs = CXMLLights.getLightConfigs(root);
		// Scene
		Map<String, Vector3f> lightPositions = CXMLScene.loadLightPositions(root);
		for (String lightId : lightPositions.keySet()) {
			colladaScene.addLight(
					new ColladaPointLight(
							lightPositions.get(lightId),
							lightConfigs.get(lightId)
					)
			);
		}
		return colladaScene;
	}

	private static Map<String, Vector4f> getMaterialColors(Node root) {
		Map<String, Vector4f> mat2color = new HashMap<>();
		Map<String, Vector4f> lambertDiffuseColors = CXMLEffects.getLambertDiffuseColors(root);
		Map<String, String> material2effect = CXMLMaterials.getEffectLinks(root);
		for (String material : material2effect.keySet()) {
			mat2color.put(material, lambertDiffuseColors.get(material2effect.get(material)));
		}
		return mat2color;
	}

}
