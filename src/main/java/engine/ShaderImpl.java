package engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface ShaderImpl {

	void enable();

	int getLocation(String name);

	void set(int location, boolean value);

	void set(int location, int value);

	void set(int location, float value);

	void set(int location, Vector2f vector);

	void set(int location, Vector3f vector);

	void set(int location, Vector4f vector);

	void set(int location, Matrix4f matrix);

	void set(int location, Texture texture);

	void destroy();

}
