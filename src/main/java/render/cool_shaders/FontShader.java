package render.cool_shaders;

import engine.Shader;
import engine.ShaderBuilder;
import engine.Texture;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import platform.opengl.GLShaderImpl;

public class FontShader implements Shader {

	private static FontShader textureShader;
	private static int references = 0;

	public static FontShader load() {
		if (textureShader == null) {
			textureShader = new FontShader();
		} else if (references == 0) {
			System.out.println("Probabilmente ho esaurito tutte le referenze, e già fatto destroy.");
			System.out.println("Quindi ho un FontShader non NULL però destroyed. Che faccio?");
			throw new RuntimeException();
		}
		references++;
		return textureShader;
	}

	private GLShaderImpl shader;
	private final int loc_projectionView;
	private final int loc_transformation;
	private final int loc_textureChannel;
	private final int loc_textureCoordinate;
	private final int loc_unitSize;

	private FontShader() {
		shader = new GLShaderImpl(
				ShaderBuilder.genShader("shaders", "font.vert"),
				ShaderBuilder.genShader("shaders", "font.frag")
		);
		loc_projectionView = shader.getLocation("projectionView");
		loc_transformation = shader.getLocation("transformation");
		loc_textureChannel = shader.getLocation("textureChannel");
		loc_textureCoordinate = shader.getLocation("textureCoordinate");
		loc_unitSize = shader.getLocation("unitSize");
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

	public void setTextureChannel(Texture texture) {
		shader.set(loc_textureChannel, texture);
	}

	public void setTextureCoordinate(Vector2f coordinate) {
		shader.set(loc_textureCoordinate, coordinate);
	}

	public void setUnitSize(Vector2f unitSize) {
		shader.set(loc_unitSize, unitSize);
	}
}
