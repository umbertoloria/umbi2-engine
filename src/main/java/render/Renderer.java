package render;

import engine.Mesh;
import engine.Shader;
import engine.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import render.cool_materials.ColouredMaterial;
import render.cool_materials.MaterialMaterial;
import render.cool_materials.TexturedMaterial;
import render.cool_shaders.ColouredShader;
import render.cool_shaders.MaterialShader;
import render.cool_shaders.TextureShader;
import render.light.DirectionalLight;
import render.light.Light;
import render.light.PointLight;
import render.light.SpotLight;

import java.util.List;

public class Renderer {

	private static Window window;

	public static void setWindow(Window window) {
		Renderer.window = window;
	}

	public static void clearColor(float red, float green, float blue) {
		window.setClearColor(red, green, blue);
	}

	public static void render(Scene scene) {
		window.setWireframeMode(scene.isWireframeMode());

		Matrix4f projectionMatrix = scene.getProjectionMatrix(window.getAspectRatio());
		Matrix4f viewMatrix = scene.getViewMatrix();
		Vector3f viewPosition = scene.getViewPosition();
		Matrix4f projectionViewMatrix = new Matrix4f();
		projectionViewMatrix = projectionViewMatrix.mul(projectionMatrix, projectionViewMatrix);
		projectionViewMatrix = projectionViewMatrix.mul(viewMatrix, projectionViewMatrix);

		List<Light> lights = scene.getLights();
		for (Shader shader : scene.shaderOrder()) {
			shader.enable();
			if (shader instanceof MaterialShader) {
//				FIXME: Riutilizzami plz
//				MaterialShader mshader = (MaterialShader) shader;
//				mshader.setProjectionView(camera.getProjectionView());
//				mshader.setViewPosition(camera.getPosition());
//				mshader.setDirectionalLight(scene.getDirectionalLight());
//				int nextPointLight = 0;
//				int nextDirectionalLight = 0;
//				for (Light light : lights) {
//					if (light instanceof PointLight) {
//						mshader.setPointLight(nextPointLight++, (PointLight) light);
//					} else if (light instanceof DirectionalLight) {
//						mshader.setDirectionalLight(nextDirectionalLight++, (DirectionalLight) light);
//					}
//				}
//				mshader.setPointLightsCount(nextPointLight);
			} else if (shader instanceof ColouredShader) {
				ColouredShader cshader = (ColouredShader) shader;
				cshader.setProjectionView(projectionViewMatrix);
				cshader.setViewPosition(viewPosition);
				int nextDirectionalLight = 0;
				int nextPointLight = 0;
				int nextSpotLight = 0;
				for (Light light : lights) {
					if (light instanceof DirectionalLight) {
						cshader.setDirectionalLight(nextDirectionalLight++, (DirectionalLight) light);
					} else if (light instanceof PointLight) {
						cshader.setPointLight(nextPointLight++, (PointLight) light);
					} else if (light instanceof SpotLight) {
						cshader.setSpotLight(nextSpotLight++, (SpotLight) light);
					}
				}
				cshader.setDirectionalLightsCount(nextDirectionalLight);
				cshader.setPointLightsCount(nextPointLight);
				cshader.setSpotLightsCount(nextSpotLight);
				cshader.setLightEffect(scene.getLightEffect());
			} else if (shader instanceof TextureShader) {
				TextureShader tshader = (TextureShader) shader;
				tshader.setProjectionView(projectionViewMatrix);
			}
			for (Batch batch : scene.getBatches(shader)) {
				Mesh mesh = batch.mesh;
				Material<?> material = batch.material;
				Matrix4f transformation = batch.transformation;
				if (shader instanceof MaterialShader && material instanceof MaterialMaterial) {
					MaterialShader sha = (MaterialShader) shader;
					sha.setTransformation(transformation);
					((MaterialMaterial) material).bind(sha);
				} else if (shader instanceof ColouredShader && material instanceof ColouredMaterial) {
					ColouredShader sha = (ColouredShader) shader;
					sha.setTransformation(transformation);
					((ColouredMaterial) material).bind(sha);
				} else if (shader instanceof TextureShader && material instanceof TexturedMaterial) {
					TextureShader sha = (TextureShader) shader;
					sha.setTransformation(transformation);
					((TexturedMaterial) material).bind(sha);
				}
				mesh.draw();
				material.unbind();
			}
		}
	}

}
