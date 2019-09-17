package engine.event;

public class MousePressedEvent extends Event {

	private int button;

	public MousePressedEvent(int button) {
		super(EventType.MousePressed);
		this.button = button;
	}

	public String toString() {
		return "mouse pressed " + button;
	}

}
