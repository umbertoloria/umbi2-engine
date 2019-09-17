package model.collada;

import org.joml.Vector3f;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

class CXMLScene {

	static Map<String, Vector3f> loadLightPositions(Node root) {

		Node sceneNode = XMLUtils.getFirstChildWithTagName(root, "scene");
		assert sceneNode != null;
		Node instanceVisualSceneNode = XMLUtils.getFirstChildWithTagName(sceneNode, "instance_visual_scene");
		assert instanceVisualSceneNode != null;
		String visualSceneName = XMLUtils.getValueOfAttribute(instanceVisualSceneNode, "url").substring(1);

		Node libraryVisualScenesNode = XMLUtils.getFirstChildWithTagName(root, "library_visual_scenes");
		assert libraryVisualScenesNode != null;

		Node visualSceneNode = XMLUtils.getFirstChildWithTagNameAndAttribNameWithValue(libraryVisualScenesNode,
				"visual_scene", "id", visualSceneName);
		assert visualSceneNode != null;

		Map<String, Vector3f> lightPositions = new HashMap<>();

		for (Node node : XMLUtils.getChildsWithTagName(visualSceneNode, "node")) {
			Boh boh = loadLightInstance(node);
			if (boh != null) {
				lightPositions.put(boh.lightId, boh.position);
			}
		}

		return lightPositions;
	}

	private static Boh loadLightInstance(Node nodeNode) {
		Node instanceLightNode = XMLUtils.getFirstChildWithTagName(nodeNode, "instance_light");
		if (instanceLightNode != null) {
			Node matrixNode = XMLUtils.getFirstChildWithTagNameAndAttribNameWithValue(nodeNode, "matrix", "sid",
					"transform");
			assert matrixNode != null;
			String transformationMatrix = matrixNode.getFirstChild().getNodeValue();
			String lightName = XMLUtils.getValueOfAttribute(instanceLightNode, "url").substring(1);
			return new Boh(lightName, transformationMatrix);
		} else {
			return null;
		}
	}

	private static class Boh {

		final String lightId;
		final Vector3f position;

		Boh(String lightId, String transformationMatrix) {
			this.lightId = lightId;
			String[] parts = transformationMatrix.split(" ");
			position = new Vector3f(
					Float.parseFloat(parts[3]),
					Float.parseFloat(parts[7]),
					Float.parseFloat(parts[11])
			);
		}

	}

}
