package render.cool_shaders;

import engine.Shader;
import engine.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import platform.opengl.GLShaderImpl;
import platform.opengl.GLTexture;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.lwjgl.opengl.GL20.*;

public class CachedShader implements Shader {

	private GLShaderImpl shader;
	private Map<String, Integer> locations = new HashMap<>();
	private Set<String> notFounds = new HashSet<>();

	public CachedShader(String vertexShaderSource, String fragmentShaderSource) {
		shader = new GLShaderImpl(vertexShaderSource, fragmentShaderSource);
	}

	public void enable() {
		shader.enable();
	}

	public void destroy() {
		shader.destroy();
	}

	private int getLocation(String name) {
		if (!locations.containsKey(name)) {
			int location = shader.getLocation(name);
			if (location >= 0) {
				locations.put(name, location);
			} else {
				if (notFounds.add(name)) {
					System.err.println("Uniform '" + name + "' non trovato!");
				}
				return -1;
			}
		}
		return locations.get(name);
	}

	public void set(String name, boolean value) {
		glUniform1i(getLocation(name), value ? GL_TRUE : GL_FALSE);
	}

	public void set(String name, int value) {
		glUniform1i(getLocation(name), value);
	}

	public void set(String name, float value) {
		glUniform1f(getLocation(name), value);
	}

	public void set(String name, Vector3f vector) {
		glUniform3f(getLocation(name), vector.x, vector.y, vector.z);
	}

	public void set(String name, Vector4f vector) {
		glUniform4f(getLocation(name), vector.x, vector.y, vector.z, vector.w);
	}

	public void set(String name, Matrix4f matrix) {
		float[] elements = new float[16];
		matrix.get(elements);
		glUniformMatrix4fv(getLocation(name), false, elements);
	}

	public void set(String name, Texture texture) {
		if (!(texture instanceof GLTexture)) {
			throw new IllegalStateException("Bisogna inviare una texture fornita da OpenGL");
		}
		GLTexture glTexture = ((GLTexture) texture);
		glUniform1i(getLocation(name), glTexture.getSlot());
	}

}
