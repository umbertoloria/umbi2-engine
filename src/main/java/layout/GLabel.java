package layout;

import render.Scene2D;

public class GLabel extends GComponent {

	private String text;
	private int fontSize = 1;

	public GLabel(String text) {
		this.text = text;
		setWidth(text.length() * fontSize);
		setHeight(fontSize);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void draw(Scene2D scene, int x, int y, int z) {
		super.draw(scene, x, y, z);
		scene.drawText(text, x, y, fontSize, z + 1);
	}

	public void pack(int x, int y) {
		setX(x);
		setY(y);
		setWidth(text.length() * fontSize);
		setHeight(fontSize);
	}

}
