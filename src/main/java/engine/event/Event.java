package engine.event;

public class Event<T extends Event.EventType> {

	public enum EventType {
		WindowClosed, WindowResized,
		KeyPressed, KeyReleased,
		MousePressed, MouseReleased, MouseMoved, MouseScrolled
	}

	private boolean handled = false;
	private EventType type;

	Event(EventType type) {
		this.type = type;
	}

	EventType getType() {
		return type;
	}

	void handle(boolean handled) {
		if (handled) {
			this.handled = true;
		}
	}

	public boolean isHandled() {
		return handled;
	}

}
