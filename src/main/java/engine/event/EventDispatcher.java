package engine.event;

import java.util.function.Predicate;

public class EventDispatcher {

	private Event event;

	public EventDispatcher(Event event) {
		this.event = event;
	}

	public void dispatch(Event.EventType type, Predicate<Object> func) {
		if (!event.isHandled() && event.getType().equals(type)) {
			event.handle(func.test(null));
		}
	}

}
