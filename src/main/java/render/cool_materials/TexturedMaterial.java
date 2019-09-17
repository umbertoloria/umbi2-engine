package render.cool_materials;

import engine.Texture;
import render.Material;
import render.cool_shaders.TextureShader;

public class TexturedMaterial implements Material<TextureShader> {

	private Texture texture;

	public TexturedMaterial(Texture texture) {
		this.texture = texture;
		getShader();
	}

	public TextureShader getShader() {
		return TextureShader.load();
	}

	public void bind(TextureShader shader) {
		texture.bind();
		shader.setTexture(texture);
	}

	public void unbind() {
		texture.unbind();
	}

	public void destroy() {
		texture.destroy();
		getShader().destroy();
	}

}
