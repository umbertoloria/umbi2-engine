package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Triangolo {

	public final Vector3f a, b, c;

	public Triangolo(Vector3f a, Vector3f b, Vector3f c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public boolean contains(Vector3f p) {
		boolean sideA = ((b.z - a.z) * (p.x - a.x) - (b.x - a.x) * (p.z - a.z)) > 0.0f;
		boolean sideB = ((c.z - b.z) * (p.x - b.x) - (c.x - b.x) * (p.z - b.z)) > 0.0f;
		if (sideA != sideB) return false;
		boolean sideC = ((a.z - c.z) * (p.x - c.x) - (a.x - c.x) * (p.z - c.z)) > 0.0f;
		return (sideA == sideC);
	}

	public float barycenter(Vector2f pos) {
		float det = (b.z - c.z) * (a.x - c.x) + (c.x - b.x) * (a.z - c.z);
		float l1 = ((b.z - c.z) * (pos.x - c.x) + (c.x - b.x) * (pos.y - c.z)) / det;
		float l2 = ((c.z - a.z) * (pos.x - c.x) + (a.x - c.x) * (pos.y - c.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * a.y + l2 * b.y + l3 * c.y;
	}

	public void mul(float scalar) {
		a.mul(scalar);
		b.mul(scalar);
		c.mul(scalar);
	}

	public Vector3f getNormal() {
		Vector3f u = new Vector3f();
		b.sub(a, u);
		Vector3f v = new Vector3f();
		c.sub(a, v);

		Vector3f normal = new Vector3f();
		normal.x = (u.y * v.z) - (u.z * v.y);
		normal.y = (u.z * v.x) - (u.x * v.z);
		normal.z = (u.x * v.y) - (u.y * v.x);

		return normal;
	}

}
