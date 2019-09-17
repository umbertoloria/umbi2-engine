package render.cool_materials;

import engine.Texture;
import render.Material;
import render.cool_shaders.MaterialShader;

public class MaterialMaterial implements Material<MaterialShader> {

	// FIXME: Rimettimi impiedi!
	private Texture skin, ambientOcclusion, specularColor, specularIntensity;
	private float shininess; // emissiveness;

	// TODO: Cambiare nome da Skin a Diffuse (oppure a Base)
	public MaterialMaterial(Texture skin, Texture ao, Texture spColor, Texture spIntensity, float shininess) {
		this.skin = skin;
		this.ambientOcclusion = ao;
		this.specularColor = spColor;
		this.specularIntensity = spIntensity;
		this.shininess = shininess;
		getShader();
	}

	public void setAmbientOcclusion(Texture texture) {
		ambientOcclusion = texture;
	}

	public MaterialShader getShader() {
		return MaterialShader.load();
	}

	public void bind(MaterialShader shader) {
		skin.bind();
		if (ambientOcclusion != null)
			ambientOcclusion.bind();
		if (specularColor != null)
			specularColor.bind();
		if (specularIntensity != null)
			specularIntensity.bind();
		shader.setMaterialSkin(skin);
		shader.setMaterialEnableAO(ambientOcclusion != null);
		shader.setMaterialAmbientOcclusion(ambientOcclusion);
		shader.setMaterialSpecularColor(specularColor);
		shader.setMaterialSpecularIntensity(specularIntensity);
		shader.setMaterialShininess(shininess);
	}

	public void unbind() {
		skin.unbind();
		if (ambientOcclusion != null)
			ambientOcclusion.unbind();
		if (specularColor != null)
			specularColor.unbind();
		if (specularIntensity != null)
			specularIntensity.unbind();
	}

	public void destroy() {
		skin.destroy();
		if (ambientOcclusion != null)
			ambientOcclusion.destroy();
		if (specularColor != null)
			specularColor.destroy();
		if (specularIntensity != null)
			specularIntensity.destroy();
		getShader().destroy();
	}

}
