package engine;

import engine.event.Event;
import platform.opengl.GLWindow;

import java.util.function.Consumer;

public abstract class Window {

	protected String title;
	protected final float aspectRatio;
	protected int width, height;
	private boolean fullscreen;
	private Consumer<Event> callback;

	public Window(String title, float aspectRatio, int width, int height, boolean fullscreen) {
		this.title = title;
		this.aspectRatio = aspectRatio;
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
	}

	public float getAspectRatio() {
		return aspectRatio;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setEventCallback(Consumer<Event> callback) {
		this.callback = callback;
	}

	protected void callEventBack(Event e) {
		if (callback != null) {
			callback.accept(e);
		}
	}

	// RUNNINGS

	public abstract void create();

	public abstract boolean shouldClose();

	public abstract void terminate();

	public abstract void destroy();

	// RENDERING

	public abstract void setClearColor(float red, float green, float blue);

	public abstract void clear();

	public abstract void update();

	public abstract float getTime();

	// OPTIONS

	public abstract void setVSync(boolean enabled);

	public abstract void setCursorCatched(boolean status);

	public abstract void setAntialiasing(boolean status);

	public abstract void setWireframeMode(boolean status);

	public abstract void setTitle(String title);

	// STATICS

	static Window create(String title, float aspectRatio, int width, int height, boolean fullscreen) {
		return new GLWindow(title, aspectRatio, width, height, fullscreen);
	}

}
