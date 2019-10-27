package render;

import engine.Shader;
import engine.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import render.camera.Camera;
import render.cool_materials.FontMaterial;
import render.cool_materials.UIMaterial;
import render.cool_meshes.QuadMesh;
import render.projection.Projection;

import java.util.Queue;
import java.util.Set;

public class Scene2D {

	private static QuadMesh quad;
	private static Texture arialTexture;

	public static void load() {
		if (quad == null) {
			quad = new QuadMesh();
			arialTexture = Texture.load("fonts/arial.png");
		}
	}

	public static void clean() {
		if (quad != null) {
			quad.destroy();
		}
	}

	private Scene scene;

	public Scene2D(Projection projection, Camera camera) {
		scene = new Scene(projection, camera, null, null);
	}

	public Camera getCamera() {
		return scene.getCamera();
	}

	public Queue<Batch> getBatches(Shader shader) {
		return scene.getBatches(shader);
	}

	public void drawQuad(float x, float y, float width, float height, int level, Vector4f background) {
		scene.add(
				quad,
				new UIMaterial(background),
				new Matrix4f()
						.translate(x, y, -100+level)
						.scale(width, height, 1)
		);
	}

	public void drawText(String text, float x, float y, float fontSize, int level) { // Vec3 color
		char[] s = text.toCharArray();
		for (int i = 0; i < s.length; i++) {
			int ro = s[i] - 32;
			FontMaterial material = new FontMaterial(arialTexture);
			material.setTextureCoordinate(ro % 10, ro / 10);
			material.setUnitSize(1f / 10, 1f / 10);
			scene.add(
					quad,
					material,
					new Matrix4f()
							.translate(x + i, y, -100+level)
			);
		}
	}

	public Set<Shader> shaderOrder() {
		return scene.shaderOrder();
	}

	public Matrix4f getProjectionMatrix(float aspectRatio) {
		return scene.getProjectionMatrix(aspectRatio);
	}

	public Matrix4f getViewMatrix() {
		return scene.getViewMatrix();
	}

}
