package model.wavefront;

import org.joml.Vector3f;

public class WavefrontMaterial {

	public final String name;
	public final float ns; // Shininess
	public final Vector3f ka; // Ambient color
	public final Vector3f kd; // Diffuse color
	public final Vector3f ks; // Specular color
	public final Vector3f ke; // Emissive color
	public final float ni;
	public final float d;
	public final int illum;

	WavefrontMaterial(String name, float ns, Vector3f ka, Vector3f kd, Vector3f ks, Vector3f ke, float ni, float d,
	                  int illum) {
		this.name = name;
		this.ns = ns;
		this.ka = ka;
		this.kd = kd;
		this.ks = ks;
		this.ke = ke;
		this.ni = ni;
		this.d = d;
		this.illum = illum;
	}

}
