package engine.input;

import org.joml.Vector2f;
import platform.opengl.GLInput;

public abstract class Input {

	private static Input input;

	public static void selectOpenGL(long window) {
		if (input != null) {
			throw new IllegalStateException("Questa funzione può essere chiamata solo prima di eseguire il motore");
		}
		input = new GLInput(window);
	}

	public static boolean isKeyPressed(Key key) {
		return input.isKeyPressedImpl(key);
	}

	public static boolean isMousePressed(MouseButton button) {
		return input.isMousePressedImpl(button);
	}

	public static Vector2f getMousePosition() {
		return input.getMousePositionImpl();
	}

	// INTERFACE

	public abstract boolean isKeyPressedImpl(engine.input.Key key);

	public abstract boolean isMousePressedImpl(MouseButton button);

	public abstract Vector2f getMousePositionImpl();

}
