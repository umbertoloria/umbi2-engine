package model.collada;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

class CXMLMaterials {

	static Map<String, String> getEffectLinks(Node root) {
		Map<String, String> material2effect = new HashMap<>();
		Node libraryMaterialsNode = XMLUtils.getFirstChildWithTagName(root, "library_materials");
		assert libraryMaterialsNode != null;
		for (Node materialNode : XMLUtils.getChildsWithTagName(libraryMaterialsNode, "material")) {
			String materialId = XMLUtils.getValueOfAttribute(materialNode, "id");
			Node instanceEffectNode = XMLUtils.getFirstChildWithTagName(materialNode, "instance_effect");
			assert instanceEffectNode != null;
			String effectId = XMLUtils.getValueOfAttribute(instanceEffectNode, "url").substring(1);
			material2effect.put(materialId, effectId);
		}
		return material2effect;
	}

}
