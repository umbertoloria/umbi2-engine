package platform.opengl;

import engine.ShaderImpl;
import engine.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class GLShaderImpl implements ShaderImpl {

	private int program;
	private boolean notYetDestroyed = true;

	public GLShaderImpl(String vertexShaderSource, String fragmentShaderSource) {
		this.program = glCreateProgram();

		int vertID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertID, vertexShaderSource);
		glCompileShader(vertID);
		if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new IllegalStateException("Vertex shader non valido: " + glGetShaderInfoLog(vertID));
		}

		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragID, fragmentShaderSource);
		glCompileShader(fragID);
		if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new IllegalStateException("Fragment shader non valido: " + glGetShaderInfoLog(fragID));
		}

		glAttachShader(program, vertID);
		glAttachShader(program, fragID);
		glLinkProgram(program);
		glValidateProgram(program);
		glDeleteShader(vertID);
		glDeleteShader(fragID);
	}

	public void enable() {
		glUseProgram(program);
	}

	public int getLocation(String name) {
		return glGetUniformLocation(program, name);
	}

	public void set(int location, boolean value) {
		glUniform1i(location, value ? GL_TRUE : GL_FALSE);
	}

	public void set(int location, int value) {
		glUniform1i(location, value);
	}

	public void set(int location, float value) {
		glUniform1f(location, value);
	}

	public void set(int location, Vector3f vector) {
		glUniform3f(location, vector.x, vector.y, vector.z);
	}

	public void set(int location, Vector4f vector) {
		glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
	}

	public void set(int location, Matrix4f matrix) {
		float[] elements = new float[16];
		matrix.get(elements);
		glUniformMatrix4fv(location, false, elements);
	}

	public void set(int location, Texture texture) {
		if (texture != null) {
			if (!(texture instanceof GLTexture)) {
				throw new IllegalStateException("Bisogna inviare una texture non nulla fornita da OpenGL");
			}
			GLTexture glTexture = ((GLTexture) texture);
			glUniform1i(location, glTexture.getSlot());
		} else {
			glUniform1i(location, 0);
		}
	}

	public void destroy() {
		if (notYetDestroyed) {
			notYetDestroyed = false;
			glUseProgram(0);
			glDeleteProgram(program);
		}
	}

}
