package render.cool_shaders;

import engine.Shader;
import engine.ShaderBuilder;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import platform.opengl.GLShaderImpl;

public class UIShader implements Shader {

	private static UIShader uiShader;
	private static int references = 0;

	public static UIShader load() {
		if (uiShader == null) {
			uiShader = new UIShader();
		} else if (references == 0) {
			System.out.println("Probabilmente ho esaurito tutte le referenze, e già fatto destroy.");
			System.out.println("Quindi ho un UIShader non NULL però destroyed. Che faccio?");
			throw new RuntimeException();
		}
		references++;
		return uiShader;
	}

	private GLShaderImpl shader;

	private final int loc_projectionViewTransformation;
	private final int loc_background;

	private UIShader() {
		shader = new GLShaderImpl(
				ShaderBuilder.genShader("shaders", "ui.vert"),
				ShaderBuilder.genShader("shaders", "ui.frag")
		);
		loc_projectionViewTransformation = shader.getLocation("projectionViewTransformation");
		loc_background = shader.getLocation("background");
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

	public void setProjectionViewTransformation(Matrix4f projectionViewTransformation) {
		shader.set(loc_projectionViewTransformation, projectionViewTransformation);
	}

	////////////// BACKGROUND //////////////

	public void setBackground(Vector4f background) {
		shader.set(loc_background, background);
	}

}
