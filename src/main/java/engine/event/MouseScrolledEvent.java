package engine.event;

public class MouseScrolledEvent extends Event {

	private float x, y;

	public MouseScrolledEvent(float x, float y) {
		super(EventType.MouseScrolled);
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "mouse scrolled of " + x + " and " + y;
	}

}
