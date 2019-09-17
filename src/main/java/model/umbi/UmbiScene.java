package model.umbi;

import org.joml.Vector2f;
import org.joml.Vector3f;
import render.light.Light;

import java.util.*;

public class UmbiScene {

	private Map<String, List<UmbiEntity>> entities = new HashMap<>();
	private List<Light> lights = new LinkedList<>();
	private Vector3f entryPosition;
	private Vector2f entryRotation;

	void addEntity(String file, UmbiEntity entity) {
		if (!entities.containsKey(file)) {
			entities.put(file, new LinkedList<>());
		}
		entities.get(file).add(entity);
	}

	void addLight(Light light) {
		lights.add(light);
	}

	void setEntryPoint(Vector3f position, Vector2f rotation) {
		this.entryPosition = position;
		this.entryRotation = rotation;
	}

	public Set<String> getRequiredModelFiles() {
		return entities.keySet();
	}

	public List<UmbiEntity> getEntities(String model) {
		return entities.get(model);
	}

	public List<Light> getLights() {
		return lights;
	}

	public Vector3f getEntryPointPosition() {
		return entryPosition;
	}

	public Vector2f getEntryPointRotation() {
		return entryRotation;
	}

	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("{\n");
		str.append("\t\"entities\": {\n");
		for (String modelFile : entities.keySet()) {
			str.append("\t\t\"").append(modelFile).append("\": [\n");
			for (UmbiEntity entity : entities.get(modelFile)) {
				Scanner sc = new Scanner(entity.toJSON());
				while (sc.hasNextLine()) {
					str.append('\t');
					str.append('\t');
					str.append('\t');
					str.append(sc.nextLine());
					str.append('\n');
				}
				str.delete(str.length() - 1, str.length());
				str.append(",\n");
			}
			str.delete(str.length() - 2, str.length() - 1);
			str.append("\t\t]\n");
		}
		str.append("\t}");
		if (lights.size() > 0) {
			str.append(",\n\t\"lights\": [\n");
			for (Light light : lights) {
				Scanner sc = new Scanner(UmbiJSON.lightToJSON(light));
				while (sc.hasNextLine()) {
					str.append('\t');
					str.append('\t');
					str.append(sc.nextLine());
					str.append('\n');
				}
				str.delete(str.length() - 1, str.length());
				str.append(",\n");
			}
			str.delete(str.length() - 2, str.length() - 1);
			str.append("\t]");
		}
		str.append("\n}");
		return str.toString();
	}

}
