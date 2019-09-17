package model.wavefront;

public class WavefrontGeometry {

	public final String materialName;
	public final int[] indices;

	WavefrontGeometry(String materialName, int[] indices) {
		this.materialName = materialName;
		this.indices = indices;
	}

}
