package engine;

public interface Shader {

	enum Type {
		Float, Float2, Float3, Float4
	}

	void enable();

	void destroy();

}
