package launcher;

import engine.input.Input;
import engine.input.Key;
import org.joml.Vector2f;

public class Controls2D {

	private Vector2f vector = new Vector2f();
	private Key left, right, up, down;
	private float speed;

	public Controls2D(Key left, Key right, Key up, Key down, float speed) {
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
		this.speed = speed;
	}

	public void update(float ts) {
		boolean upGoing = Input.isKeyPressed(this.up);
		boolean downGoing = Input.isKeyPressed(this.down);
		boolean leftGoing = Input.isKeyPressed(this.left);
		boolean rightGoing = Input.isKeyPressed(this.right);
		vector.set(0);
		if (upGoing ^ downGoing) {
			vector.y = upGoing ? 1 : -1;
		}
		if (leftGoing ^ rightGoing) {
			vector.x = rightGoing ? 1 : -1;
		}
		if (vector.length() > 0) {
			vector.normalize();
			vector.mul(speed * ts);
		}
	}

	public float getUp() {
		return vector.y;
	}

	public float getRight() {
		return vector.x;
	}

}
