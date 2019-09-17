package model.collada;

import java.util.*;

public class ColladaScene {

	private Map<String, ColladaModel> models = new HashMap<>();
	private List<ColladaPointLight> lights = new ArrayList<>();

	void addModel(String name, ColladaModel model) {
		models.put(name, model);
	}

	void addLight(ColladaPointLight light) {
		lights.add(light);
	}

	public Set<String> getModelNames() {
		return models.keySet();
	}

	public ColladaModel getModel(String name) {
		return models.get(name);
	}

	public int getLightsCount() {
		return lights.size();
	}

	public ColladaPointLight getLight(int index) {
		return lights.get(index);
	}

}
