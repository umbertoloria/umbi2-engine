package engine.event;

public class KeyPressedEvent extends Event {

	private int key;
	private boolean pressing;

	public KeyPressedEvent(int key, boolean pressing) {
		super(EventType.KeyPressed);
		this.key = key;
		this.pressing = pressing;
	}

	public String toString() {
		if (pressing) {
			return "key pressing " + key;
		} else {
			return "key pressed " + key;
		}
	}

}
