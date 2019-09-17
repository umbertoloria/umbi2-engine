package launcher;

public class Switch {

	private float now = 0;
	private float last = 0;
	private float delay;

	public Switch(float delay) {
		this.delay = delay;
	}

	public boolean timeout(float ts) {
		now += ts;
		if (last + delay <= now) {
			last = now;
			return true;
		} else {
			return false;
		}
	}

}
