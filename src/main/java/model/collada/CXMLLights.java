package model.collada;

import org.joml.Vector3f;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

class CXMLLights {

	static Map<String, ColladaLightConfig> getLightConfigs(Node root) {
		Map<String, ColladaLightConfig> lights = new HashMap<>();
		Node libraryLightsNode = XMLUtils.getFirstChildWithTagName(root, "library_lights");
		if (libraryLightsNode != null) {
			for (Node lightNode : XMLUtils.getChildsWithTagName(libraryLightsNode, "light")) {
				lights.put(
						XMLUtils.getValueOfAttribute(lightNode, "id"),
						loadLight(lightNode)
				);
			}
		}
		return lights;
	}

	private static ColladaLightConfig loadLight(Node lightNode) {

		Node techniqueCommonNode = XMLUtils.getFirstChildWithTagName(lightNode, "technique_common");
		assert techniqueCommonNode != null;

		Node pointNode = XMLUtils.getFirstChildWithTagName(techniqueCommonNode, "point");
		assert pointNode != null;

		Node colorNode = XMLUtils.getFirstChildWithTagName(pointNode, "color");
		assert colorNode != null;
		String colorStr = colorNode.getFirstChild().getNodeValue();
		String[] colorStrParts = colorStr.split(" ");
		Vector3f color = new Vector3f(
				Float.parseFloat(colorStrParts[0]) / 1000,
				Float.parseFloat(colorStrParts[1]) / 1000,
				Float.parseFloat(colorStrParts[2]) / 1000
		);

		Node constantAttenuationNode = XMLUtils.getFirstChildWithTagName(pointNode, "constant_attenuation");
		assert constantAttenuationNode != null;
		float constantAttenuation = Float.parseFloat(constantAttenuationNode.getFirstChild().getNodeValue());

		Node linearAttenuationNode = XMLUtils.getFirstChildWithTagName(pointNode, "linear_attenuation");
		assert linearAttenuationNode != null;
		float linearAttenuation = Float.parseFloat(linearAttenuationNode.getFirstChild().getNodeValue());

		Node quadraticAttenuationNode = XMLUtils.getFirstChildWithTagName(pointNode, "quadratic_attenuation");
		assert quadraticAttenuationNode != null;
		float quadraticAttenuation = Float.parseFloat(quadraticAttenuationNode.getFirstChild().getNodeValue());

		return new ColladaLightConfig(color, constantAttenuation, linearAttenuation, quadraticAttenuation);

	}

}
