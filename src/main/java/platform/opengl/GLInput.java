package platform.opengl;

import engine.input.Input;
import engine.input.Key;
import engine.input.MouseButton;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class GLInput extends Input {

	private static Map<Key, Integer> keyAliases = new HashMap<>();

	static {
		keyAliases.put(Key.Space, GLFW_KEY_SPACE);
		keyAliases.put(Key.Shift, GLFW_KEY_LEFT_SHIFT);
		keyAliases.put(Key.W, GLFW_KEY_W);
		keyAliases.put(Key.A, GLFW_KEY_A);
		keyAliases.put(Key.S, GLFW_KEY_S);
		keyAliases.put(Key.D, GLFW_KEY_D);
		keyAliases.put(Key.I, GLFW_KEY_I);
		keyAliases.put(Key.J, GLFW_KEY_J);
		keyAliases.put(Key.K, GLFW_KEY_K);
		keyAliases.put(Key.L, GLFW_KEY_L);
		keyAliases.put(Key.Up, GLFW_KEY_UP);
		keyAliases.put(Key.Left, GLFW_KEY_LEFT);
		keyAliases.put(Key.Down, GLFW_KEY_DOWN);
		keyAliases.put(Key.Right, GLFW_KEY_RIGHT);
		keyAliases.put(Key.Num0, GLFW_KEY_0);
		keyAliases.put(Key.Num1, GLFW_KEY_1);
		keyAliases.put(Key.Num2, GLFW_KEY_2);
		keyAliases.put(Key.Num3, GLFW_KEY_3);
		keyAliases.put(Key.Num4, GLFW_KEY_4);
		keyAliases.put(Key.Num5, GLFW_KEY_5);
		keyAliases.put(Key.Num6, GLFW_KEY_6);
		keyAliases.put(Key.Num7, GLFW_KEY_7);
		keyAliases.put(Key.Num8, GLFW_KEY_8);
		keyAliases.put(Key.Num9, GLFW_KEY_9);
		keyAliases.put(Key.Z, GLFW_KEY_Z);
		keyAliases.put(Key.X, GLFW_KEY_X);
		keyAliases.put(Key.C, GLFW_KEY_C);
		keyAliases.put(Key.V, GLFW_KEY_V);
		keyAliases.put(Key.B, GLFW_KEY_B);
		keyAliases.put(Key.N, GLFW_KEY_N);
		keyAliases.put(Key.M, GLFW_KEY_M);
		keyAliases.put(Key.F, GLFW_KEY_F);
		keyAliases.put(Key.G, GLFW_KEY_G);
		keyAliases.put(Key.H, GLFW_KEY_H);
	}

	private static Map<MouseButton, Integer> mouseAliases = new HashMap<>();

	static {
		mouseAliases.put(MouseButton.Left, GLFW_MOUSE_BUTTON_1);
		mouseAliases.put(MouseButton.Right, GLFW_MOUSE_BUTTON_2);
		mouseAliases.put(MouseButton.Wheel, GLFW_MOUSE_BUTTON_3);
	}

	private long window;

	public GLInput(long window) {
		this.window = window;
	}

	public boolean isKeyPressedImpl(Key key) {
		int state = glfwGetKey(window, keyAliases.get(key));
		return state == GLFW_PRESS || state == GLFW_REPEAT;
	}

	public boolean isMousePressedImpl(MouseButton button) {
		int state = glfwGetMouseButton(window, mouseAliases.get(button));
		return state == GLFW_PRESS;
	}

	public Vector2f getMousePositionImpl() {
		double[] x = {-1};
		double[] y = {-1};
		glfwGetCursorPos(window, x, y);
		return new Vector2f((float) x[0], (float) y[0]);
	}

}
