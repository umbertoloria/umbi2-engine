package render.cool_materials;

import engine.Texture;
import org.joml.Vector2f;
import render.Material;
import render.cool_shaders.FontShader;

public class FontMaterial implements Material<FontShader> {

	private Texture texture;
	private Vector2f unitSize = new Vector2f(1);
	private Vector2f coordinate = new Vector2f();

	public FontMaterial(Texture texture) {
		this.texture = texture;
		getShader();
	}

	public FontShader getShader() {
		return FontShader.load();
	}

	// TODO: (u, t) forse.
	public void setTextureCoordinate(int x, int y) {
		this.coordinate.set(x, y);
	}

	public void setUnitSize(float width, float height) {
		unitSize.set(width, height);
	}

	public void bind(FontShader shader) {
		texture.bind();
		shader.setTextureChannel(texture);
		shader.setTextureCoordinate(coordinate);
		shader.setUnitSize(unitSize);
	}

	public void unbind() {
		texture.unbind();
	}

	public void destroy() {
		texture.destroy();
		getShader().destroy();
	}

}
