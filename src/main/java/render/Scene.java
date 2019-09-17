package render;

import engine.Mesh;
import engine.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import render.camera.Camera;
import render.light.Light;
import render.projection.Projection;

import java.util.*;

public class Scene {

	public enum LightEffect {
		LAMBERTIAN, HALF_LAMBERT, CEL_SHADING
	}

	private Projection projection;
	private Camera camera;
	private boolean wireframeMode;
	private LightEffect lightEffect;
	private List<Light> lights;
	private Map<Shader, Queue<Batch>> pipeline = new HashMap<>();

	public Scene(Projection projection, Camera camera, List<Light> lights, LightEffect lightEffect) {
		this.projection = projection;
		this.camera = camera;
		this.lights = lights;
		this.lightEffect = lightEffect;
	}

	public void setWireframeMode(boolean status) {
		wireframeMode = status;
	}

	public boolean isWireframeMode() {
		return wireframeMode;
	}

	public Camera getCamera() {
		return camera;
	}

	public List<Light> getLights() {
		return lights;
	}

	public Queue<Batch> getBatches(Shader shader) {
		return pipeline.get(shader);
	}

	public void add(Mesh mesh, Material material, Matrix4f transformation) {
		Shader shader = material.getShader();
		if (!pipeline.containsKey(shader)) {
			pipeline.put(shader, new LinkedList<>());
		}
		pipeline.get(shader).add(new Batch(mesh, material, transformation));
	}

	public void add(Entity entity) {
		Matrix4f transformation = entity.getTransformation();
		for (Picture picture : entity.getPictures()) {
			add(picture.mesh, picture.material, transformation);
		}
	}

	public Set<Shader> shaderOrder() {
		return pipeline.keySet();
	}

	public Matrix4f getProjectionMatrix(float aspectRatio) {
		return projection.getProjectionMatrix(aspectRatio);
	}

	public Matrix4f getViewMatrix() {
		return camera.getViewMatrix();
	}

	public Vector3f getViewPosition() {
		return camera.getPosition();
	}

	public LightEffect getLightEffect() {
		return lightEffect;
	}

}
