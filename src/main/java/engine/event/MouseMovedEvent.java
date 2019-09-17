package engine.event;

public class MouseMovedEvent extends Event {

	private float x, y;

	public MouseMovedEvent(float x, float y) {
		super(EventType.MouseMoved);
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "mouse moved on (" + x + ", " + y + ")";
	}

}
