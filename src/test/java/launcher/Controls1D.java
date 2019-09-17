package launcher;

import engine.input.Input;
import engine.input.Key;

public class Controls1D {

	private float value;
	private Key less, more;
	private float speed;

	public Controls1D(Key less, Key more, float speed) {
		this.less = less;
		this.more = more;
		this.speed = speed;
	}

	public void update(float ts) {
		boolean l = Input.isKeyPressed(less);
		boolean m = Input.isKeyPressed(more);
		value = 0;
		if (l ^ m) {
			value = speed * ts * (m ? 1 : -1);
		}
	}

	public float getValue() {
		return value;
	}

}
