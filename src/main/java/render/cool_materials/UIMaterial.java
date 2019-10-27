package render.cool_materials;

import org.joml.Vector4f;
import render.Material;
import render.cool_shaders.UIShader;

public class UIMaterial implements Material<UIShader> {

	private Vector4f background = new Vector4f(0);

	public UIMaterial() {
		getShader();
	}

	public UIMaterial(Vector4f background) {
		this.background.set(background);
		getShader();
	}

	public UIShader getShader() {
		return UIShader.load();
	}

	public void bind(UIShader shader) {
		shader.setBackground(background);
	}

	public void unbind() {
	}

	public void destroy() {
		getShader().destroy();
	}

	// GETTERS AND SETTERS

	public Vector4f getBackground() {
		return background;
	}

	public void setBackground(Vector4f background) {
		this.background = background;
	}

}
