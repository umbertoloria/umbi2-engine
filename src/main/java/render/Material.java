package render;

import engine.Shader;

public interface Material<S extends Shader> {

	S getShader();

	void bind(S shader);

	void unbind();

	default void destroy() {
		getShader().destroy();
	}

}
