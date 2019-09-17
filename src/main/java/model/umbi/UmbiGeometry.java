package model.umbi;

public class UmbiGeometry {

	public final String name;
	public final String materialName;
	public final int[] indices;

	public UmbiGeometry(String name, String materialName, int[] indices) {
		this.name = name;
		this.materialName = materialName;
		this.indices = indices;
	}

	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("\"").append(name).append("\": {\n\t");
		str.append(JSONUtils.jsonString("material", materialName));
		str.append(",\n\t\"indices\": [\n");
		for (int i = 0; i < indices.length; i += 3) {
			str.append("\t\t");
			JSONUtils.jsonVec3iAppend(str, indices[i], indices[i + 1], indices[i + 2]);
			str.append(",\n");
		}
		str.delete(str.length() - 2, str.length() - 1);
		str.append("\t]\n");
		str.append("}");
		return str.toString();
	}

}
