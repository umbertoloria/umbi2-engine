package engine.event;

public class WindowResizedEvent extends Event {

	private int width, height;

	public WindowResizedEvent(int width, int height) {
		super(EventType.WindowResized);
		this.width = width;
		this.height = height;
	}

	public String toString() {
		return "window resize " + width + "x" + height;
	}

}
