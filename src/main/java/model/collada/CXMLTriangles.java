package model.collada;

import org.w3c.dom.Node;

import java.util.Scanner;

public class CXMLTriangles {

	// FIXME: convert to static

	public final String material;
	private String vertexSource, normalSource, texCoordSource;
	private int vertexOffset, normalOffset, texCoordOffset;
	private int[] singleIndices;

	public CXMLTriangles(Node trianglesNode) {
		this.material = XMLUtils.getValueOfAttribute(trianglesNode, "material");
		Node[] inputNodes = XMLUtils.getChildsWithTagName(trianglesNode, "input");
		int bufferStride = inputNodes.length;
		for (Node inputNode : inputNodes) {
			String semanticType = XMLUtils.getValueOfAttribute(inputNode, "semantic");
			String sourceId = XMLUtils.getValueOfAttribute(inputNode, "source").substring(1);
			int offset = Integer.parseInt(XMLUtils.getValueOfAttribute(inputNode, "offset"));
			if (semanticType.equalsIgnoreCase("VERTEX")) {
				vertexSource = sourceId;
				vertexOffset = offset;
			} else if (semanticType.equalsIgnoreCase("NORMAL")) {
				normalSource = sourceId;
				normalOffset = offset;
			} else if (semanticType.equalsIgnoreCase("TEXCOORD")) {
				texCoordSource = sourceId;
				texCoordOffset = offset;
			}
		}
		assert vertexSource != null && normalSource != null && texCoordSource != null;
		Node trianglesP = XMLUtils.getFirstChildWithTagName(trianglesNode, "p");
		assert trianglesP != null;
		String trianglesPData = trianglesP.getFirstChild().getNodeValue();
		int trianglesCount = Integer.parseInt(XMLUtils.getValueOfAttribute(trianglesNode, "count"));

		int i = 0;
		int vertexIndex = 0;
		singleIndices = new int[trianglesCount * 3 * 3];
		Scanner sc = new Scanner(trianglesPData);
		while (sc.hasNextInt()) {
			int generalIndex = sc.nextInt();
			if (i == vertexOffset) {
				singleIndices[vertexIndex * 3] = generalIndex;
			} else if (i == texCoordOffset) {
				singleIndices[vertexIndex * 3 + 1] = generalIndex;
			} else if (i == normalOffset) {
				singleIndices[vertexIndex * 3 + 2] = generalIndex;
			}
//			int positionIndex = sc.nextInt();
//			int normalIndex = sc.nextInt();
//			int texCoordIndex = sc.nextInt();
//			singleIndices[i++] = positionIndex;
//			singleIndices[i++] = texCoordIndex;
//			singleIndices[i++] = normalIndex;
			i++;
			if (i == bufferStride) {
				i = 0;
				vertexIndex++;
			}
		}
		sc.close();
	}

	public String getVertexSource() {
		return vertexSource;
	}

	public String getNormalSource() {
		return normalSource;
	}

	public String getTexCoordSource() {
		return texCoordSource;
	}

	public int[] getSingleIndices() {
		return singleIndices;
	}

}
