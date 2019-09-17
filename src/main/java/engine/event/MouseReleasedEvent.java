package engine.event;

public class MouseReleasedEvent extends Event {

	private int button;

	public MouseReleasedEvent(int button) {
		super(EventType.MouseReleased);
		this.button = button;
	}

	public String toString() {
		return "mouse released " + button;
	}

}
