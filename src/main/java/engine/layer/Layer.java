package engine.layer;

import engine.event.Event;

public interface Layer {

	default void attach() {
	}

	void update(float ts);

	void event(Event e);

	default void detach() {
	}

}
