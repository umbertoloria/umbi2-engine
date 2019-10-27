package layout;

import org.joml.Vector4f;
import render.Scene2D;

import java.util.LinkedList;
import java.util.List;

public class GSelect extends GComponent {

	private List<GOption> options = new LinkedList<>();

	public GSelect() {
		setWidth(10);
		setHeight(1);
		setBackground(new Vector4f(0, 0, 1, 1));
	}

	public void add(GOption option) {
		options.add(option);
	}

	public void draw(Scene2D scene, int x, int y, int z) {
		super.draw(scene, x, y, z);
		z++;
		scene.drawText("select", x, y, 1, z);
		if (isHover()) {
			y++;
			for (GOption option : options) {
				option.draw(scene, x, y++, z);
			}
		}
	}

	public void pack(int x, int y) {
		setX(x);
		setY(y);
	}

}
