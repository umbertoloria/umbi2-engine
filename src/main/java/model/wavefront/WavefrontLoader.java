package model.wavefront;

import engine.Resources;
import org.joml.Vector3f;

import java.util.*;

public class WavefrontLoader {

	public static Map<String, WavefrontModel> parseOBJ(String path) {
		Scanner sc = new Scanner(Resources.getResource(path));

		Map<String, WavefrontModel> models = new HashMap<>();

		String currentModelName = null;
		WavefrontModel currentModel = null;
		String currentMaterialName = null;

		// MODEL STUFF
		List<Float> positionsList = new ArrayList<>();
		List<Float> texCoordsList = new ArrayList<>();
		List<Float> normalsList = new ArrayList<>();

		// GEOMETRY STUFF
		List<Integer> indices = new ArrayList<>();

		// MATERIAL STUFF
		Map<String, WavefrontMaterial> materials = new HashMap<>();

		String line;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (line.startsWith("mtllib ")) {
				for (WavefrontMaterial additionalMaterial : parseMTL(line.substring(7))) {
					assert !materials.containsKey(additionalMaterial.name);
					materials.put(additionalMaterial.name, additionalMaterial);
				}
			} else if (line.startsWith("o ")) {

				if (currentModel != null) {
					models.put(currentModel.name, currentModel);
				}

				currentModelName = line.substring(2);
				// TODO: Adesso sto bellamente cancellando gli ultimi indici di un oggetto seguito da un nuovo oggetto
				indices.clear();

			} else if (line.startsWith("v ")) {
				addVec3To(line.substring(2), positionsList);
			} else if (line.startsWith("vt ")) {
				addVec2To(line.substring(3), texCoordsList);
			} else if (line.startsWith("vn ")) {
				addVec3To(line.substring(3), normalsList);
			} else if (line.startsWith("usemtl ")) {

				int i;
				// Se primo USEMTL di un O
				if (!positionsList.isEmpty() && !texCoordsList.isEmpty() && !normalsList.isEmpty() && indices.isEmpty()) {
					assert currentModelName != null;
					i = 0;
					// Positions
					float[] positions = new float[positionsList.size()];
					for (Float value : positionsList) positions[i++] = value;
					positionsList.clear();
					// TexCoords
					i = 0;
					float[] texCoords = new float[texCoordsList.size()];
					for (Float value : texCoordsList) texCoords[i++] = value;
					texCoordsList.clear();
					// Normals
					i = 0;
					float[] normals = new float[normalsList.size()];
					for (Float value : normalsList) normals[i++] = value;
					normalsList.clear();
					currentModel = new WavefrontModel(currentModelName, positions, texCoords, normals);
					currentModelName = null;
				} else {
					// Allora salvo la precedente geometria
					assert currentModel != null && !indices.isEmpty();
					i = 0;
					int[] indicesArray = new int[indices.size()];
					for (Integer index : indices) indicesArray[i++] = index;
					currentModel.addGeometry(new WavefrontGeometry(currentMaterialName, indicesArray));
				}
				indices.clear();
				currentMaterialName = line.substring(7);
				currentModel.addMaterial(materials.get(currentMaterialName));

			} else if (line.startsWith("f ")) {
				String[] stringVertices = line.substring(2).split(" ");
				addVertex(stringVertices[0], indices);
				addVertex(stringVertices[1], indices);
				addVertex(stringVertices[2], indices);
			}
		}
		sc.close();

		// Carico l'ultima geometria se esiste (e quindi deve esistere anche il suo relativo modello)
		if (!indices.isEmpty()) {
			assert currentModel != null;
			int i = 0;
			int[] indicesArray = new int[indices.size()];
			for (Integer index : indices) {
				indicesArray[i++] = index;
			}
			currentModel.addGeometry(new WavefrontGeometry(currentMaterialName, indicesArray));
		}

		// Carico l'ultimo modello se esiste
		if (currentModel != null) {
			models.put(currentModel.name, currentModel);
		}

		return models;
	}

	public static List<WavefrontMaterial> parseMTL(String path) {
		List<WavefrontMaterial> materials = new LinkedList<>();
		String currentMaterialName = null;
		float currentNs = 0;
		Vector3f currentKa = null;
		Vector3f currentKd = null;
		Vector3f currentKs = null;
		Vector3f currentKe = null;
		float currentNi = 0;
		float currentD = 0;
		int currentIllum = 0;

		Scanner sc = new Scanner(Resources.getResource(path));
		String line;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (line.startsWith("newmtl ")) {

				if (currentMaterialName != null) {
					materials.add(
							new WavefrontMaterial(
									currentMaterialName,
									currentNs,
									currentKa,
									currentKd,
									currentKs,
									currentKe,
									currentNi,
									currentD,
									currentIllum
							)
					);
				}

				currentMaterialName = line.substring(7);

			} else if (line.startsWith("Ns ")) {
				currentNs = Float.parseFloat(line.substring(3));
			} else if (line.startsWith("Ka ")) {
				currentKa = getVector3f(line.substring(3));
			} else if (line.startsWith("Kd ")) {
				currentKd = getVector3f(line.substring(3));
			} else if (line.startsWith("Ks ")) {
				currentKs = getVector3f(line.substring(3));
			} else if (line.startsWith("Ke ")) {
				currentKe = getVector3f(line.substring(3));
			} else if (line.startsWith("Ni ")) {
				currentNi = Float.parseFloat(line.substring(3));
			} else if (line.startsWith("d ")) {
				currentD = Float.parseFloat(line.substring(2));
			} else if (line.startsWith("illum ")) {
				currentIllum = Integer.parseInt(line.substring(6));
			}
		}

		if (currentMaterialName != null) {
			materials.add(
					new WavefrontMaterial(
							currentMaterialName,
							currentNs,
							currentKa,
							currentKd,
							currentKs,
							currentKe,
							currentNi,
							currentD,
							currentIllum
					)
			);
		}

		return materials;

	}

	private static void addVec2To(String data, List<Float> list) {
		String[] parts = data.split(" ");
		list.add(Float.parseFloat(parts[0]));
		// Capovolgo la texture
		list.add(1 - Float.parseFloat(parts[1]));
		// FIXME: Non fare qui.
	}

	private static void addVec3To(String data, List<Float> list) {
		String[] parts = data.split(" ");
		list.add(Float.parseFloat(parts[0]));
		list.add(Float.parseFloat(parts[1]));
		list.add(Float.parseFloat(parts[2]));
	}

	private static void addVertex(String data, List<Integer> indices) {
		String[] parts = data.split("/");
		indices.add(Integer.parseInt(parts[0]) - 1);
		indices.add(Integer.parseInt(parts[1]) - 1);
		indices.add(Integer.parseInt(parts[2]) - 1);
	}

	private static Vector3f getVector3f(String data) {
		String[] parts = data.split(" ");
		return new Vector3f(
				Float.parseFloat(parts[0]),
				Float.parseFloat(parts[1]),
				Float.parseFloat(parts[2])
		);
	}

}
