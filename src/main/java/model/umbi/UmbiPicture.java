package model.umbi;

import model.Geometry;

public class UmbiPicture {

	public final Geometry geometry;
	public final UmbiMaterial material;

	UmbiPicture(Geometry geometry, UmbiMaterial material) {
		this.geometry = geometry;
		this.material = material;
	}

}
