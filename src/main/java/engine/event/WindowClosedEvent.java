package engine.event;

public class WindowClosedEvent extends Event {

	public WindowClosedEvent() {
		super(EventType.WindowClosed);
	}

	public String toString() {
		return "window closed";
	}

}
