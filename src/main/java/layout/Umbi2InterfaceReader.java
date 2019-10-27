package layout;

import model.collada.XMLUtils;
import org.joml.Vector4f;
import org.w3c.dom.Node;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Umbi2InterfaceReader {

	public static List<GComponent> load(String path) {
		List<GComponent> result = new LinkedList<>();
		Node rootNode = XMLUtils.getRootOfXMLFile(path);
		for (Node node : XMLUtils.getChilds(rootNode)) {
			result.add(parse(node));
		}
		return result;
	}

	private static GComponent parse(Node node) {
		GComponent component = null;
		String type = node.getNodeName();
		if (type.equals("panel")) {
			GPanel panel = parsePanel(node);
			for (Node child : XMLUtils.getChilds(node)) {
				panel.add(parse(child));
			}
			component = panel;
		} else if (type.equals("label")) {
			component = parseLabel(node);
		} else if (type.equals("select")) {
			GSelect select = parseSelect(node);
			for (Node child : XMLUtils.getChildsWithTagName(node, "option")) {
				select.add(parseOption(child));
			}
			component = select;
		} else {
			System.out.println("oddeo non conosco " + node);
		}
		return component;
	}

	private static GPanel parsePanel(Node node) {
		GPanel panel = new GPanel();

		Vector4f background = getBackground(node);
		if (background != null) panel.setBackground(background);

		if (XMLUtils.hasAttribute(node, "cols")) {
			panel.setColumns(Integer.parseInt(XMLUtils.getValueOfAttribute(node, "cols")));
		}

		return panel;
	}

	private static GLabel parseLabel(Node node) {
		GLabel label = new GLabel(node.getTextContent());

		Vector4f background = getBackground(node);
		if (background != null) label.setBackground(background);

		return label;
	}

	private static GSelect parseSelect(Node node) {
		GSelect select = new GSelect();
		return select;
	}

	private static GOption parseOption(Node node) {
		GOption option = new GOption(node.getTextContent());
		return option;
	}

	private static Vector4f getBackground(Node node) {
		if (XMLUtils.hasAttribute(node, "background")) {
			String background = XMLUtils.getValueOfAttribute(node, "background").substring(1);
			assert background.length() == 3 || background.length() == 4 ||
					background.length() == 6 || background.length() == 8;
			float red;
			float green;
			float blue;
			float alpha = 1;
			if (background.length() == 3) {
				String fullBackground = "#";
				fullBackground += background.charAt(0);
				fullBackground += background.charAt(0);
				fullBackground += background.charAt(1);
				fullBackground += background.charAt(1);
				fullBackground += background.charAt(2);
				fullBackground += background.charAt(2);
				Color c = Color.decode(fullBackground);
				red = c.getRed() / 255f;
				green = c.getGreen() / 255f;
				blue = c.getBlue() / 255f;
			} else if (background.length() == 4) {
				Color c = Color.decode(background.substring(0, 3));
				red = c.getRed() / 255f;
				green = c.getGreen() / 255f;
				blue = c.getBlue() / 255f;
				alpha = Integer.decode(background.substring(3));
				System.out.println("decoded " + background + " in " + alpha);
			} else if (background.length() == 6) {
				Color c = Color.decode(background);
				red = c.getRed() / 255f;
				green = c.getGreen() / 255f;
				blue = c.getBlue() / 255f;
			} else {
				Color c = Color.decode(background);
				red = c.getRed() / 255f;
				green = c.getGreen() / 255f;
				blue = c.getBlue() / 255f;
			}
			return new Vector4f(red, green, blue, alpha);
		} else {
			return null;
		}
	}

}
