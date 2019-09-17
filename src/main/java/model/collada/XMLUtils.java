package model.collada;

import engine.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class XMLUtils {

	public static Node getFirstChildWithTagName(Node n, String tagName) {
		NodeList childs = n.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(tagName)) {
				return child;
			}
		}
		return null;
	}

	public static Node getFirstChildWithTagNameAndAttribNameWithValue(Node n, String tagName, String attribName,
	                                                                  String attribValue) {
		NodeList childs = n.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(tagName) && getValueOfAttribute(child, attribName).equals(attribValue)) {
				return child;
			}
		}
		return null;
	}

	public static Node[] getChildsWithTagName(Node n, String tagName) {
		List<Node> nodes = new LinkedList<>();
		NodeList childs = n.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(tagName)) {
				nodes.add(child);
			}
		}
		return nodes.toArray(new Node[0]);
	}

	public static Node getRootOfXMLFile(String path) {
		try {
			DocumentBuilder dbui = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dbui.parse(Resources.getResource(path));
			return doc.getDocumentElement();
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new IllegalStateException();
		}
	}

	public static String getValueOfAttribute(Node n, String attributeName) {
		return n.getAttributes().getNamedItem(attributeName).getNodeValue();
	}

}
