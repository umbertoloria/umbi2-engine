package launcher;

import layout.GComponent;
import layout.GLayer;
import layout.Umbi2InterfaceReader;

import java.util.List;

public class ConfigLayer extends GLayer {

	private List<GComponent> components;

	public ConfigLayer(String path) {
		components = Umbi2InterfaceReader.load(path);
	}

	public void attach() {
		for (GComponent gComponent : components) {
			getGPanel().add(gComponent);
		}
		getGPanel().pack(0, 0);
	}

}
