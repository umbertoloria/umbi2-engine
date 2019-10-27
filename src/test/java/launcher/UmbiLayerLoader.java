package launcher;

import engine.event.Event;
import engine.input.Key;
import engine.layer.Layer;
import model.umbi.*;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import render.Entity;
import render.Picture;
import render.Renderer;
import render.Scene;
import render.camera.PlaneCamera;
import render.cool_pictures.ColouredPicture;
import render.light.Light;
import render.light.PointLight;
import render.light.SpotLight;
import render.projection.Perspective;

import java.util.LinkedList;
import java.util.List;

public class UmbiLayerLoader implements Layer {

	private PlaneCamera camera;
	private Scene scene;
	private List<Entity> entities = new LinkedList<>();

	UmbiLayerLoader(String filepath) {

		UmbiScene umbiScene = UmbiLoader.loadScene(filepath);
		List<Light> lights = new LinkedList<>(umbiScene.getLights());

		for (String requiredModelFile : umbiScene.getRequiredModelFiles()) {

			UmbiModel model = UmbiLoader.loadModel("models/" + requiredModelFile);
			UmbiPicture[] umbiPictures = model.getPictures();
			Picture[] pictures = new Picture[umbiPictures.length];
			int i = 0;
			for (UmbiPicture umbiPicture : umbiPictures) {
				pictures[i++] =
						new ColouredPicture(
								umbiPicture.geometry,
								umbiPicture.material.getDiffuse(),
								umbiPicture.material.getSpecular(),
								umbiPicture.material.getShininess()
						);
			}

			for (UmbiEntity entity : umbiScene.getEntities(requiredModelFile)) {
				Vector3f position = entity.getPosition();
				Vector3f rotation = entity.getRotation();
				Vector3f scale = entity.getScale();
				entities.add(
						new Entity(pictures)
								.setPosition(position)
								.setRotation(rotation)
								.setScale(scale)
				);
				for (Light light : model.getLights()) {
					if (light instanceof PointLight) {
						PointLight lig = (PointLight) light;
						lights.add(
								new PointLight(
										new Vector3f(lig.getPosition())
												.rotateY((float) Math.toRadians(rotation.y))
												.rotateX((float) Math.toRadians(rotation.x))
												.rotateZ((float) Math.toRadians(rotation.z))
												.mul(scale)
												.add(position),
										lig.getAmbient(),
										lig.getDiffuse(),
										lig.getSpecular(),
										lig.getConstantAttenuation(),
										lig.getLinearAttenuation(),
										lig.getQuadraticAttenuation()
								)
						);
					} else if (light instanceof SpotLight) {
						SpotLight lig = (SpotLight) light;
						lights.add(
								new SpotLight(
										new Vector3f(lig.getPosition())
												.rotateY((float) Math.toRadians(rotation.y))
												.rotateX((float) Math.toRadians(rotation.x))
												.rotateZ((float) Math.toRadians(rotation.z))
												.mul(scale)
												.add(position),
										lig.getDirection()
												.rotateY((float) Math.toRadians(rotation.y))
												.rotateX((float) Math.toRadians(rotation.x))
												.rotateZ((float) Math.toRadians(rotation.z)),
										lig.getCutOffAngle(),
										lig.getOuterCutOffAngle(),
										lig.getAmbient(),
										lig.getDiffuse(),
										lig.getSpecular(),
										lig.getConstantAttenuation(),
										lig.getLinearAttenuation(),
										lig.getQuadraticAttenuation()
								)
						);
					} else {
						lights.add(light);
					}
				}
			}

		}

		camera = new PlaneCamera();
		camera.setPosition(umbiScene.getEntryPointPosition());
		Vector2f rot = umbiScene.getEntryPointRotation();
		camera.setRotation(rot.x, rot.y);
		scene = new Scene(
				new Perspective(50, 0.001f, 1000),
				camera,
				lights,
				Scene.LightEffect.CEL_SHADING
		);
		for (Entity entity : entities) {
			scene.add(entity);
		}
	}

	private Controls2D posControls = new Controls2D(Key.A, Key.D, Key.W, Key.S, 10);
	private Controls1D heightControls = new Controls1D(Key.Shift, Key.Space, 10);
	private Controls2D rotControls = new Controls2D(Key.Left, Key.Right, Key.Up, Key.Down, 70);

	public void update(float ts) {
		rotControls.update(ts);
		float rightArrow = rotControls.getRight();
		if (rightArrow != 0) {
			camera.turnRight(-rightArrow);
		}
		float pitch = rotControls.getUp();
		if (pitch != 0) {
			camera.turnUp(pitch);
		}

		posControls.update(ts);
		float front = posControls.getUp();
		float right = posControls.getRight();
		if (front != 0 || right != 0) {
			camera.move(front, right);
		}
		heightControls.update(ts);
		float up = heightControls.getValue();
		if (up != 0) {
			camera.addHeight(up);
		}
		Renderer.render(scene);
	}

	public void event(Event e) {
	}

	public void detach() {
		for (Entity entity : entities) {
			entity.destroy();
		}
	}

}
