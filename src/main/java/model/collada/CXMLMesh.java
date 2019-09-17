package model.collada;

import model.GeometryBuilder;
import org.joml.Vector4f;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class CXMLMesh {

	public static ColladaModel getColladaModel(Node geometryNode, Map<String, Vector4f> mat2color) {

		String id = XMLUtils.getValueOfAttribute(geometryNode, "id");
		String name = XMLUtils.getValueOfAttribute(geometryNode, "name");

		// Prevedo di avere sempre un solo <mesh> per ogni <geometry>, ma potrei sbagliarmi...
		Node meshNode = XMLUtils.getFirstChildWithTagName(geometryNode, "mesh");
		assert meshNode != null;

		// <source>
		Map<String, CXMLSource> sources = new HashMap<>();
		Node[] sourceNodes = XMLUtils.getChildsWithTagName(meshNode, "source");
		for (Node sourceNode : sourceNodes) {
			CXMLSource source = new CXMLSource(sourceNode);
			sources.put(source.getId(), source);
		}

		// <vertices>
		Node myVertices = XMLUtils.getFirstChildWithTagName(meshNode, "vertices");
		assert myVertices != null;
		String myVerticesId = XMLUtils.getValueOfAttribute(myVertices, "id");
		Node inputSemanticPosition = XMLUtils.getFirstChildWithTagNameAndAttribNameWithValue(myVertices, "input",
				"semantic", "POSITION");
		assert inputSemanticPosition != null;
		String positionsSourceName = XMLUtils.getValueOfAttribute(inputSemanticPosition, "source").substring(1);

		// <triangles>
		Node[] trianglesNodes = XMLUtils.getChildsWithTagName(meshNode, "triangles");
		CXMLTriangles[] triangles = new CXMLTriangles[trianglesNodes.length];
		int i = 0;
		for (Node trianglesNode : trianglesNodes) {
			triangles[i++] = new CXMLTriangles(trianglesNode);
		}

		// Assumo che i canali per POSITIONS, NORMALS e TEXCOORDS siano unici. Allora me li salvo prima.
		float[] positions = null;
		float[] normals = null;
		float[] texCoords = null;
		for (CXMLTriangles triangle : triangles) {

			String vertexSourceId = triangle.getVertexSource();
			CXMLSource vertexSource = sources.get(vertexSourceId);
			if (vertexSource == null)
				vertexSource = sources.get(positionsSourceName);
			positions = vertexSource.getData();

			String normalSourceId = triangle.getNormalSource();
			CXMLSource normalSource = sources.get(normalSourceId);
			normals = normalSource.getData();

			String texCoordSourceId = triangle.getTexCoordSource();
			CXMLSource texCoordSource = sources.get(texCoordSourceId);
			texCoords = texCoordSource.getData();

			break;

		}

		ColladaModel colladaModel = new ColladaModel(positions, texCoords, normals);

		for (CXMLTriangles triangle : triangles) {
			// Il formato Collada non prevede un nome per ogni geometria, quindi usiamo quello del materiale.
			colladaModel.add(
					new ColladaGeometry(
							triangle.material, triangle.getSingleIndices(),
							GeometryBuilder.buildGeometry(positions, texCoords, normals, triangle.getSingleIndices()),
							triangle.material, mat2color.get(triangle.material)
					)
			);
		}

		return colladaModel;
	}

}
