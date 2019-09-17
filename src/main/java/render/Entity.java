package render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.List;

public class Entity {

	private List<Picture> pictures;
	private Vector3f position = new Vector3f();
	private Vector3f scale = new Vector3f(1);
	private Vector3f rotation = new Vector3f();

	public Entity(Picture... pictures) {
		this.pictures = Arrays.asList(pictures);
		for (Picture picture : this.pictures) {
			picture.addRef();
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public Entity setPosition(Vector3f position) {
		this.position.set(position);
		return this;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Entity setScale(Vector3f scale) {
		this.scale.set(scale);
		return this;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Entity setRotation(Vector3f rotation) {
		this.rotation = rotation;
		return this;
	}

	// Utils

	public List<Picture> getPictures() {
		return pictures;
	}

	public Matrix4f getTransformation() {
		Matrix4f transformation = new Matrix4f();
		transformation.translate(position);
		transformation.rotateYXZ(
				(float) Math.toRadians(rotation.y),
				(float) Math.toRadians(rotation.x),
				(float) Math.toRadians(rotation.z)
		);
		transformation.scale(scale);
		return transformation;
	}

	public void destroy() {
		for (Picture picture : pictures) {
			picture.removeRef();
		}
	}

}
