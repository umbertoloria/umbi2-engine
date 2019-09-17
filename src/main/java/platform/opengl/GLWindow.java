package platform.opengl;

import engine.Window;
import engine.event.*;
import engine.input.Input;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GLWindow extends Window {

	private long window;

	public GLWindow(String title, float aspectRatio, int width, int height, boolean fullscreen) {
		super(title, aspectRatio, width, height, fullscreen);
		create();
		Input.selectOpenGL(window);
	}

	public void create() {
		GLFWErrorCallback.createPrint(System.err).set();
		glfwInit();
		glfwDefaultWindowHints();
		// Compatibilità OSX
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_SAMPLES, 32);
		window = glfwCreateWindow(width, height, title, isFullscreen() ? glfwGetPrimaryMonitor() : NULL, NULL);
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			assert vidmode != null;
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}

		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
		glViewport(0, 0, width, height);

		glEnable(GL_MULTISAMPLE);
		glEnable(GL_DEPTH_TEST);
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glEnable(GL_CULL_FACE);
//		glCullFace(GL_BACK);

		glfwSetWindowSizeCallback(window, (window, wdt, hgt) -> callEventBack(new WindowResizedEvent(wdt, hgt)));
		glfwSetWindowCloseCallback(window, (win) -> callEventBack(new WindowClosedEvent()));
		glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
			if (action == GLFW_PRESS) {
				callEventBack(new MousePressedEvent(button));
			} else if (action == GLFW_RELEASE) {
				callEventBack(new MouseReleasedEvent(button));
			}
		});
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (action == GLFW_PRESS) {
				callEventBack(new KeyPressedEvent(key, false));
			} else if (action == GLFW_RELEASE) {
				callEventBack(new KeyReleasedEvent(key));
			} else if (action == GLFW_REPEAT) {
				callEventBack(new KeyPressedEvent(key, true));
			}
		});
		glfwSetScrollCallback(window,
				(window, xOffset, yOffset) -> callEventBack(new MouseScrolledEvent((float) xOffset, (float) yOffset)));
		glfwSetCursorPosCallback(window, (window, xPos, yPos) -> callEventBack(new MouseMovedEvent((float) xPos,
				(float) yPos)));
		glfwSetWindowSizeCallback(window, (window, width, height) -> {
			this.width = width;
			this.height = height;
			glViewport(0, 0, width, height);
		});
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}

	public void terminate() {
		glfwSetWindowShouldClose(window, true);
	}

	public void destroy() {
		glfwTerminate();
		window = -1;
	}

	// RENDERING

	public void setClearColor(float red, float green, float blue) {
		glClearColor(red, green, blue, 1);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	public float getTime() {
		return (float) glfwGetTime();
	}

	// OPTIONS

	public void setVSync(boolean enabled) {
		glfwSwapInterval(enabled ? 1 : 0);
	}

	public void setCursorCatched(boolean status) {
		glfwSetInputMode(window, GLFW_CURSOR, status ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
	}

	public void setAntialiasing(boolean status) {
		if (status) {
			glEnable(GL_MULTISAMPLE);
		} else {
			glDisable(GL_MULTISAMPLE);
		}
	}

	public void setWireframeMode(boolean status) {
		glPolygonMode(GL_FRONT_AND_BACK, status ? GL_LINE : GL_FILL);
	}

	public void setTitle(String title) {
		this.title = title;
		glfwSetWindowTitle(window, title);
	}

}
