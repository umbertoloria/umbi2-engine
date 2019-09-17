package model.umbi;

import render.light.DirectionalLight;
import render.light.Light;
import render.light.PointLight;

class UmbiJSON {

	static String lightToJSON(Light light) {
		StringBuilder str = new StringBuilder();
		if (light instanceof PointLight) {
			PointLight lig = (PointLight) light;
			str.append("{\n\t\"type\": \"point\",\n\t");
			str.append(JSONUtils.jsonVec3("position", lig.getPosition()));
			str.append(",\n\t");
			str.append(JSONUtils.jsonVec3("ambient", lig.getAmbient())).append(",\n\t");
			str.append(JSONUtils.jsonVec3("diffuse", lig.getDiffuse())).append(",\n\t");
			str.append(JSONUtils.jsonVec3("specular", lig.getSpecular())).append(",\n\t");
			str.append(JSONUtils.jsonFloat("constantAttenuation", lig.getConstantAttenuation()));
			str.append(",\n\t");
			str.append(JSONUtils.jsonFloat("linearAttenuation", lig.getLinearAttenuation()));
			str.append(",\n\t");
			str.append(JSONUtils.jsonFloat("quadraticAttenuation", lig.getQuadraticAttenuation()));
			str.append("\n}");
		} else if (light instanceof DirectionalLight) {
			DirectionalLight lig = (DirectionalLight) light;
			str.append("{\n\t\"type\": \"directional\",\n\t");
			str.append(JSONUtils.jsonVec3("direction", lig.getDirection()));
			str.append(",\n\t");
			str.append(JSONUtils.jsonVec3("ambient", lig.getAmbient()));
			str.append(",\n\t");
			str.append(JSONUtils.jsonVec3("diffuse", lig.getDiffuse()));
			str.append(",\n\t");
			str.append(JSONUtils.jsonVec3("specular", lig.getSpecular()));
			str.append("\n}");
		}
		return str.toString();
	}

}
