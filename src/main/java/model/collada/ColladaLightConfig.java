package model.collada;

import org.joml.Vector3f;

class ColladaLightConfig {

	final Vector3f color;
	final float constantAtte;
	final float linearAtte;
	final float quadraticAtte;

	ColladaLightConfig(Vector3f color, float constantAtte, float linearAtte, float quadraticAtte) {
		this.color = color;
		this.constantAtte = constantAtte;
		this.linearAtte = linearAtte;
		this.quadraticAtte = quadraticAtte;
	}

}
