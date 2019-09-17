package engine;

import engine.event.Event;
import engine.layer.Layer;
import engine.layer.LayerStack;
import render.Renderer;

public class App {

	private Window window;
	private LayerStack layerStack = new LayerStack();

	public App(String title, int width, float aspectRatio) {
		window = Window.create(title, aspectRatio, width, (int) (width / aspectRatio), false);
		window.setAntialiasing(true);
		window.setEventCallback(this::newEvent);
		Renderer.setWindow(window);
	}

	public void add(Layer layer) {
		layerStack.add(layer);
	}

	public void run() {
		while (window.getTime() <= 0.12) {
			window.update();
		}
		float lastFrameTime = window.getTime();
		int fps = 0;
		float lastFPSCheck = lastFrameTime;
		while (!window.shouldClose()) {
			window.clear();
			float time = window.getTime();
			float timestep = time - lastFrameTime;
			lastFrameTime = time;
			for (Layer layer : layerStack.ascending()) {
				layer.update(timestep);
			}
			window.update();
			fps++;
			if (lastFPSCheck + 1 <= lastFrameTime) {
				window.setTitle("FPS: " + fps);
				fps = 0;
				lastFPSCheck = time;
			}
		}
		window.destroy();
		layerStack.destroy();
	}

	public void clearColor(float red, float green, float blue) {
		window.setClearColor(red, green, blue);
	}

	private void newEvent(Event e) {
		for (Layer layer : layerStack.descending()) {
			layer.event(e);
			if (e.isHandled())
				break;
		}
	}

}
