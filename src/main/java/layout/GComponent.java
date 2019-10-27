package layout;

import engine.input.Input;
import org.joml.Vector2f;
import org.joml.Vector4f;
import render.Scene2D;

public abstract class GComponent {

	private int x;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	private int y;

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private int width;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	private int height;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	private Vector4f background = new Vector4f(0);

	public Vector4f getBackground() {
		return background;
	}

	public void setBackground(Vector4f background) {
		this.background.set(background);
	}

	public void draw(Scene2D scene, int x, int y, int z) {
		if (width != 0 && height != 0) {
			scene.drawQuad(x, y, width, height, z, background);
		}
	}

	// INPUTS

	public void update(float ts) {
		Vector2f mousePos = Input.getMousePosition().mul(100);
	}

	private boolean isHover;

	public boolean isHover() {
		return isHover;
	}

	public abstract void pack(int x, int y);

}
