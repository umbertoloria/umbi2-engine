package render.cool_shaders;

import engine.Shader;
import engine.ShaderBuilder;
import engine.Texture;
import org.joml.Matrix4f;
import platform.opengl.GLShaderImpl;

public class TextureShader implements Shader {

	private static TextureShader textureShader;
	private static int references = 0;

	public static TextureShader load() {
		if (textureShader == null) {
			textureShader = new TextureShader();
		} else if (references == 0) {
			System.out.println("Probabilmente ho esaurito tutte le referenze, e già fatto destroy.");
			System.out.println("Quindi ho un TextureShader non NULL però destroyed. Che faccio?");
			throw new RuntimeException();
		}
		references++;
		return textureShader;
	}

	private GLShaderImpl shader;
	private final int loc_projectionView;
	private final int loc_transformation;
	private final int loc_textureChannel;

	private TextureShader() {
		shader = new GLShaderImpl(
				ShaderBuilder.genShader("shaders", "texture.vert"),
				ShaderBuilder.genShader("shaders", "texture.frag")
		);
		loc_projectionView = shader.getLocation("projectionView");
		loc_transformation = shader.getLocation("transformation");
		loc_textureChannel = shader.getLocation("textureChannel");
	}

	public void enable() {
		shader.enable();
	}

	public void destroy() {
		references--;
		if (references == 0) {
			shader.destroy();
		}
	}

	////////////// MATRICES //////////////

	public void setProjectionView(Matrix4f projectionView) {
		shader.set(loc_projectionView, projectionView);
	}

	public void setTransformation(Matrix4f transformation) {
		shader.set(loc_transformation, transformation);
	}

	////////////// TEXTURE //////////////

	public void setTexture(Texture texture) {
		shader.set(loc_textureChannel, texture);
	}

}
