package layout;

import render.Scene2D;

// FIXME: Tecnicamente dovrebbero essere GAbstractComponent
public class GOption extends GComponent {

	private String text;

	public GOption(String text) {
		this.text = text;
		setWidth(text.length());
		setHeight(1);
	}

	public void draw(Scene2D scene, int x, int y, int z) {
		super.draw(scene, x, y, z);
		scene.drawText(text, x, y, 1, z + 1);
	}

	public void pack(int x, int y) {
		setX(x);
		setY(y);
	}

}
