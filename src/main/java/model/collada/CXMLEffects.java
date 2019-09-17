package model.collada;

import org.joml.Vector4f;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

class CXMLEffects {

	static Map<String, Vector4f> getLambertDiffuseColors(Node root) {
		Map<String, Vector4f> effects = new HashMap<>();
		Node libraryEffectsNode = XMLUtils.getFirstChildWithTagName(root, "library_effects");
		assert libraryEffectsNode != null;
		for (Node effectNode : XMLUtils.getChildsWithTagName(libraryEffectsNode, "effect")) {
			effects.put(
					XMLUtils.getValueOfAttribute(effectNode, "id"),
					getLambertDiffuseColor(effectNode)
			);
		}
		return effects;
	}

	private static Vector4f getLambertDiffuseColor(Node effect) {

		Node profileNode = XMLUtils.getFirstChildWithTagName(effect, "profile_COMMON");
		assert profileNode != null;

		Node techniqueNode = XMLUtils.getFirstChildWithTagName(profileNode, "technique");
		assert techniqueNode != null;

		Node lambertNode = XMLUtils.getFirstChildWithTagName(techniqueNode, "lambert");
		assert lambertNode != null;

		Node diffuseNode = XMLUtils.getFirstChildWithTagName(lambertNode, "diffuse");
		assert diffuseNode != null;

		Node diffuseColorNode = XMLUtils.getFirstChildWithTagName(diffuseNode, "color");
		assert diffuseColorNode != null;
		String diffuseColor = diffuseColorNode.getFirstChild().getNodeValue();

		String[] parts = diffuseColor.split(" ");
		return new Vector4f(
				Float.parseFloat(parts[0]),
				Float.parseFloat(parts[1]),
				Float.parseFloat(parts[2]),
				Float.parseFloat(parts[3])
		);
	}

}
