package render.cool_shaders;

import engine.Shader;
import engine.ShaderBuilder;
import engine.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import platform.opengl.GLShaderImpl;
import render.light.DirectionalLight;
import render.light.PointLight;

public class MaterialShader implements Shader {

	private static MaterialShader materialShader;
	private static int references = 0;

	public static MaterialShader load() {
		if (materialShader == null) {
			materialShader = new MaterialShader();
		} else if (references == 0) {
			System.out.println("Probabilmente ho esaurito tutte le referenze, e già fatto destroy.");
			System.out.println("Quindi ho un MaterialShader non NULL però destroyed. Che faccio?");
			throw new RuntimeException();
		}
		references++;
		return materialShader;
	}

	private static final int LIGHTS_COUNT = 30;
	private GLShaderImpl shader;
	private final int loc_projectionView;
	private final int loc_transformation;
	private final int[] loc_lightPositions = new int[LIGHTS_COUNT];
	private final int[] loc_lightColor = new int[LIGHTS_COUNT];
	private final int[] loc_lightIntensity = new int[LIGHTS_COUNT];
	private final int[] loc_lightConstantAttenuation = new int[LIGHTS_COUNT];
	private final int[] loc_lightLinearAttenuation = new int[LIGHTS_COUNT];
	private final int[] loc_lightQuadraticAttenuation = new int[LIGHTS_COUNT];
	private final int loc_viewPosition;
	private final int loc_pointLightsCount;
	private final int loc_ambientLight;
	private final int loc_materialSkin;
	private final int loc_materialEnableAO;
	private final int loc_materialAmbientOcclusion;
	private final int loc_materialSpecularColor;
	private final int loc_materialSpecularIntensity;
	private final int loc_materialShininess;

	private MaterialShader() {
		shader = new GLShaderImpl(
				ShaderBuilder.genShader("shaders", "material.vert"),
				ShaderBuilder.genShader("shaders", "material.frag")
		);
		loc_projectionView = shader.getLocation("projectionView");
		loc_transformation = shader.getLocation("transformation");
		for (int i = 0; i < loc_lightPositions.length; i++) {
			loc_lightPositions[i] = shader.getLocation("lights[" + i + "].position");
			loc_lightColor[i] = shader.getLocation("lights[" + i + "].color");
			loc_lightIntensity[i] = shader.getLocation("lights[" + i + "].intensity");
			loc_lightConstantAttenuation[i] = shader.getLocation("lights[" + i + "].constant");
			loc_lightLinearAttenuation[i] = shader.getLocation("lights[" + i + "].linear");
			loc_lightQuadraticAttenuation[i] = shader.getLocation("lights[" + i + "].quadratic");
		}
		loc_viewPosition = shader.getLocation("viewPosition");
		loc_pointLightsCount = shader.getLocation("lightsCount");
		loc_ambientLight = shader.getLocation("ambientLight");
		loc_materialSkin = shader.getLocation("material.skin");
		loc_materialEnableAO = shader.getLocation("material.enableAO");
		loc_materialAmbientOcclusion = shader.getLocation("material.ambientOcclusion");
		loc_materialSpecularColor = shader.getLocation("material.specularColor");
		loc_materialSpecularIntensity = shader.getLocation("material.specularIntensity");
		loc_materialShininess = shader.getLocation("material.shininess");
	}

	public void enable() {
		shader.enable();
	}

	public void destroy() {
		references--;
		if (references == 0) {
			shader.destroy();
		}
	}

	////////////// MATRICES //////////////

	public void setProjectionView(Matrix4f projectionView) {
		shader.set(loc_projectionView, projectionView);
	}

	public void setTransformation(Matrix4f transformation) {
		shader.set(loc_transformation, transformation);
	}

	////////////// LIGHTING //////////////

	public void setPointLight(int index, PointLight light) {
		if (index < LIGHTS_COUNT) {
			shader.set(loc_lightPositions[index], light.getPosition());
			shader.set(loc_lightColor[index], light.getDiffuse());
			shader.set(loc_lightIntensity[index], light.getSpecular());
			shader.set(loc_lightConstantAttenuation[index], light.getConstantAttenuation());
			shader.set(loc_lightLinearAttenuation[index], light.getLinearAttenuation());
			shader.set(loc_lightQuadraticAttenuation[index], light.getQuadraticAttenuation());
		}
	}

	public void setPointLightsCount(int count) {
		shader.set(loc_pointLightsCount, count);
	}

	public void setViewPosition(Vector3f viewPosition) {
		shader.set(loc_viewPosition, viewPosition);
	}

	public void setDirectionalLight(DirectionalLight directionalLight) {
		shader.set(loc_ambientLight, directionalLight.getDirection());
		shader.set(loc_ambientLight, directionalLight.getDiffuse());
	}

	////////////// MATERIAL //////////////

	public void setMaterialSkin(Texture texture) {
		shader.set(loc_materialSkin, texture);
	}

	public void setMaterialEnableAO(boolean flag) {
		shader.set(loc_materialEnableAO, flag);
	}

	public void setMaterialAmbientOcclusion(Texture texture) {
		shader.set(loc_materialAmbientOcclusion, texture);
	}

	public void setMaterialSpecularColor(Texture texture) {
		shader.set(loc_materialSpecularColor, texture);
	}

	public void setMaterialSpecularIntensity(Texture texture) {
		shader.set(loc_materialSpecularIntensity, texture);
	}

	public void setMaterialShininess(float shininess) {
		shader.set(loc_materialShininess, shininess);
	}

//	public void setMaterialSpecularIntensity(Texture texture) {
//		shader.set(loc_materialEmission, texture);
//	}
//
//	public void setMaterialShininess(float shininess) {
//		shader.set(loc_materialShininess, shininess);
//	}
//
//	public void setMaterialEmissiveness(float emissiveness) {
//		shader.set(loc_materialEmissiveness, emissiveness);
//	}

}
