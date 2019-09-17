package engine.event;

public class KeyReleasedEvent extends Event {

	private int key;

	public KeyReleasedEvent(int key) {
		super(EventType.KeyReleased);
		this.key = key;
	}

	public String toString() {
		return "key released " + key;
	}

}
