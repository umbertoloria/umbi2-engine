package model.collada;

import org.w3c.dom.Node;

import java.util.Locale;
import java.util.Scanner;

class CXMLSource {

	// FIXME: convert to static

	private final String id;
	private float[] data;

	CXMLSource(Node sourceNode) {

		id = XMLUtils.getValueOfAttribute(sourceNode, "id");
		Node technique_common = XMLUtils.getFirstChildWithTagName(sourceNode, "technique_common");
		assert technique_common != null;
		Node accessor = XMLUtils.getFirstChildWithTagName(technique_common, "accessor");
		assert accessor != null;
		String accessorSource = XMLUtils.getValueOfAttribute(accessor, "source").substring(1);
		int accessorCount = Integer.parseInt(XMLUtils.getValueOfAttribute(accessor, "count"));
		int accessorStride = Integer.parseInt(XMLUtils.getValueOfAttribute(accessor, "stride"));

//		vectorComponentCount = accessorStride;
//		Node[] params = XMLUtils.getChildsWithTagName(accessor, "param");
//		assert accessorStride == params.length;

		Node float_array = XMLUtils.getFirstChildWithTagName(sourceNode, "float_array");
		assert float_array != null;
		String float_arrayId = XMLUtils.getValueOfAttribute(float_array, "id");
		assert float_arrayId.equals(accessorSource);
		int float_arrayCount = Integer.parseInt(XMLUtils.getValueOfAttribute(float_array, "count"));
		assert float_arrayCount == accessorCount * accessorStride;

//		numbersCount = float_arrayCount;

		String float_arrayData = float_array.getFirstChild().getNodeValue();
		int i = 0;
		data = new float[float_arrayCount];
		Scanner sc = new Scanner(float_arrayData);
		sc.useLocale(Locale.US);
		while (sc.hasNextFloat()) {
			data[i++] = sc.nextFloat();
		}
		sc.close();

	}

	String getId() {
		return id;
	}

	float[] getData() {
		return data;
	}

}
