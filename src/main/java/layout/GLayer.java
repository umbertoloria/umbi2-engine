package layout;

import engine.event.Event;
import engine.layer.Layer;
import org.joml.Vector4f;
import render.Renderer;
import render.Scene2D;
import render.camera.StandardCamera;
import render.projection.Orthographic;

public class GLayer implements Layer {

	private GPanel gpanel = new GPanel();

	protected GPanel getGPanel() {
		return gpanel;
	}

	public void update(float ts) {
		gpanel.setBackground(new Vector4f(1, 1, 1, 1));
		Scene2D scene = new Scene2D(
				new Orthographic(0, 100, 100, 0, 0, 101),
				new StandardCamera()
		);
		gpanel.update(ts);
		gpanel.draw(scene, 0, 0, 0);
		Renderer.render(scene);
	}

	public void event(Event e) {

	}

}
