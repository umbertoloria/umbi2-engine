package render.cool_shaders;

import engine.Shader;
import engine.ShaderBuilder;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import platform.opengl.GLShaderImpl;
import render.Scene;
import render.light.DirectionalLight;
import render.light.PointLight;
import render.light.SpotLight;

public class ColouredShader implements Shader {

	private static ColouredShader colouredShader;
	private static int references = 0;

	public static ColouredShader load() {
		if (colouredShader == null) {
			colouredShader = new ColouredShader();
		} else if (references == 0) {
			System.out.println("Probabilmente ho esaurito tutte le referenze, e già fatto destroy.");
			System.out.println("Quindi ho un ColouredShader non NULL però destroyed. Che faccio?");
			throw new RuntimeException();
		}
		references++;
		return colouredShader;
	}

	private static final int POINT_LIGHTS_COUNT = 30;
	private static final int DIRECTIONAL_LIGHTS_COUNT = 30;
	private static final int SPOT_LIGHTS_COUNT = 30;
	private GLShaderImpl shader;

	private final int loc_projectionView;
	private final int loc_transformation;
	private final int loc_viewPosition;

	private final int[] loc_directionalLightDirection = new int[DIRECTIONAL_LIGHTS_COUNT];
	private final int[] loc_directionalLightAmbient = new int[DIRECTIONAL_LIGHTS_COUNT];
	private final int[] loc_directionalLightDiffuse = new int[DIRECTIONAL_LIGHTS_COUNT];
	private final int[] loc_directionalLightSpecular = new int[DIRECTIONAL_LIGHTS_COUNT];
	private final int loc_directionalLightsCount;

	private final int[] loc_pointLightPositions = new int[POINT_LIGHTS_COUNT];
	private final int[] loc_pointLightConstantAttenuation = new int[POINT_LIGHTS_COUNT];
	private final int[] loc_pointLightLinearAttenuation = new int[POINT_LIGHTS_COUNT];
	private final int[] loc_pointLightQuadraticAttenuation = new int[POINT_LIGHTS_COUNT];
	private final int[] loc_pointLightAmbient = new int[POINT_LIGHTS_COUNT];
	private final int[] loc_pointLightDiffuse = new int[POINT_LIGHTS_COUNT];
	private final int[] loc_pointLightSpecular = new int[POINT_LIGHTS_COUNT];
	private final int loc_pointLightsCount;

	private final int[] loc_spotLightPositions = new int[SPOT_LIGHTS_COUNT];
	private final int[] loc_spotLightDirection = new int[SPOT_LIGHTS_COUNT];
	private final int[] loc_spotLightCutOffAngle = new int[SPOT_LIGHTS_COUNT];
	private final int[] loc_spotLightOuterCutOffAngle = new int[SPOT_LIGHTS_COUNT];
	private final int[] loc_spotLightConstantAttenuation = new int[SPOT_LIGHTS_COUNT];
	private final int[] loc_spotLightLinearAttenuation = new int[SPOT_LIGHTS_COUNT];
	private final int[] loc_spotLightQuadraticAttenuation = new int[SPOT_LIGHTS_COUNT];
	private final int[] loc_spotLightAmbient = new int[SPOT_LIGHTS_COUNT];
	private final int[] loc_spotLightDiffuse = new int[SPOT_LIGHTS_COUNT];
	private final int[] loc_spotLightSpecular = new int[SPOT_LIGHTS_COUNT];
	private final int loc_spotLightsCount;
	private final int loc_lightEffect;

	private final int loc_materialDiffuse;
	private final int loc_materialSpecular;
	private final int loc_materialShininess;

	private ColouredShader() {
		shader = new GLShaderImpl(
				ShaderBuilder.genShader("shaders", "coloured.vert"),
				ShaderBuilder.genShader("shaders", "coloured.frag")
		);
		loc_projectionView = shader.getLocation("projectionView");
		loc_transformation = shader.getLocation("transformation");
		loc_viewPosition = shader.getLocation("viewPosition");

		for (int i = 0; i < DIRECTIONAL_LIGHTS_COUNT; i++) {
			loc_directionalLightDirection[i] = shader.getLocation("directionalLights[" + i + "].direction");

			loc_directionalLightAmbient[i] = shader.getLocation("directionalLights[" + i + "].ambient");
			loc_directionalLightDiffuse[i] = shader.getLocation("directionalLights[" + i + "].diffuse");
			loc_directionalLightSpecular[i] = shader.getLocation("directionalLights[" + i + "].specular");
		}
		loc_directionalLightsCount = shader.getLocation("directionalLightsCount");

		for (int i = 0; i < POINT_LIGHTS_COUNT; i++) {
			loc_pointLightPositions[i] = shader.getLocation("pointLights[" + i + "].position");

			loc_pointLightConstantAttenuation[i] = shader.getLocation("pointLights[" + i + "].constant");
			loc_pointLightLinearAttenuation[i] = shader.getLocation("pointLights[" + i + "].linear");
			loc_pointLightQuadraticAttenuation[i] = shader.getLocation("pointLights[" + i + "].quadratic");

			loc_pointLightAmbient[i] = shader.getLocation("pointLights[" + i + "].ambient");
			loc_pointLightDiffuse[i] = shader.getLocation("pointLights[" + i + "].diffuse");
			loc_pointLightSpecular[i] = shader.getLocation("pointLights[" + i + "].specular");
		}
		loc_pointLightsCount = shader.getLocation("pointLightsCount");

		for (int i = 0; i < SPOT_LIGHTS_COUNT; i++) {
			loc_spotLightPositions[i] = shader.getLocation("spotLights[" + i + "].position");
			loc_spotLightDirection[i] = shader.getLocation("spotLights[" + i + "].direction");
			loc_spotLightCutOffAngle[i] = shader.getLocation("spotLights[" + i + "].cutOffAngle");
			loc_spotLightOuterCutOffAngle[i] = shader.getLocation("spotLights[" + i + "].outerCutOffAngle");

			loc_spotLightConstantAttenuation[i] = shader.getLocation("spotLights[" + i + "].constant");
			loc_spotLightLinearAttenuation[i] = shader.getLocation("spotLights[" + i + "].linear");
			loc_spotLightQuadraticAttenuation[i] = shader.getLocation("spotLights[" + i + "].quadratic");

			loc_spotLightAmbient[i] = shader.getLocation("spotLights[" + i + "].ambient");
			loc_spotLightDiffuse[i] = shader.getLocation("spotLights[" + i + "].diffuse");
			loc_spotLightSpecular[i] = shader.getLocation("spotLights[" + i + "].specular");
		}
		loc_spotLightsCount = shader.getLocation("spotLightsCount");
		loc_lightEffect = shader.getLocation("lightEffect");

		loc_materialDiffuse = shader.getLocation("material.diffuse");
		loc_materialSpecular = shader.getLocation("material.specular");
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

	public void setViewPosition(Vector3f viewPosition) {
		shader.set(loc_viewPosition, viewPosition);
	}

	////////////// LIGHTING //////////////

	public void setDirectionalLight(int index, DirectionalLight directionalLight) {
		if (index < DIRECTIONAL_LIGHTS_COUNT) {
			shader.set(loc_directionalLightDirection[index], directionalLight.getDirection());

			shader.set(loc_directionalLightAmbient[index], directionalLight.getAmbient());
			shader.set(loc_directionalLightDiffuse[index], directionalLight.getDiffuse());
			shader.set(loc_directionalLightSpecular[index], directionalLight.getSpecular());
		}
	}

	public void setDirectionalLightsCount(int count) {
		shader.set(loc_directionalLightsCount, count);
	}

	public void setPointLight(int index, PointLight light) {
		if (index < POINT_LIGHTS_COUNT) {
			shader.set(loc_pointLightPositions[index], light.getPosition());

			shader.set(loc_pointLightAmbient[index], light.getAmbient());
			shader.set(loc_pointLightDiffuse[index], light.getDiffuse());
			shader.set(loc_pointLightSpecular[index], light.getSpecular());

			shader.set(loc_pointLightConstantAttenuation[index], light.getConstantAttenuation());
			shader.set(loc_pointLightLinearAttenuation[index], light.getLinearAttenuation());
			shader.set(loc_pointLightQuadraticAttenuation[index], light.getQuadraticAttenuation());
		}
	}

	public void setPointLightsCount(int count) {
		shader.set(loc_pointLightsCount, count);
	}

	public void setSpotLight(int index, SpotLight spotLight) {
		if (index < DIRECTIONAL_LIGHTS_COUNT) {
			shader.set(loc_spotLightPositions[index], spotLight.getPosition());
			shader.set(loc_spotLightDirection[index], spotLight.getDirection());
			shader.set(loc_spotLightCutOffAngle[index], spotLight.getCutOffAngle());
			shader.set(loc_spotLightOuterCutOffAngle[index], spotLight.getOuterCutOffAngle());

			shader.set(loc_spotLightAmbient[index], spotLight.getAmbient());
			shader.set(loc_spotLightDiffuse[index], spotLight.getDiffuse());
			shader.set(loc_spotLightSpecular[index], spotLight.getSpecular());

			shader.set(loc_spotLightConstantAttenuation[index], spotLight.getConstantAttenuation());
			shader.set(loc_spotLightLinearAttenuation[index], spotLight.getLinearAttenuation());
			shader.set(loc_spotLightQuadraticAttenuation[index], spotLight.getQuadraticAttenuation());
		}
	}

	public void setSpotLightsCount(int count) {
		shader.set(loc_spotLightsCount, count);
	}

	public void setLightEffect(Scene.LightEffect lightEffect) {
		if (lightEffect == Scene.LightEffect.LAMBERTIAN) {
			shader.set(loc_lightEffect, 0);
		} else if (lightEffect == Scene.LightEffect.HALF_LAMBERT) {
			shader.set(loc_lightEffect, 1);
		} else if (lightEffect == Scene.LightEffect.CEL_SHADING) {
			shader.set(loc_lightEffect, 2);
		}
	}

	////////////// MATERIAL //////////////

	public void setMaterialDiffuse(Vector4f diffuse) {
		shader.set(loc_materialDiffuse, diffuse);
	}

	public void setMaterialSpecular(Vector4f diffuse) {
		shader.set(loc_materialSpecular, diffuse);
	}

	public void setMaterialShininess(float shininess) {
		shader.set(loc_materialShininess, shininess);
	}

}
