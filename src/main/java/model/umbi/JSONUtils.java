package model.umbi;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;

class JSONUtils {

	// DECODING

	static Vector2f getVec2f(JSONObject obj, Object key) {
		String data = obj.get(key).toString();
		data = data.substring(1);
		data = data.substring(0, data.length() - 1);
		String[] parts = data.split(",");
		Vector2f result = new Vector2f();
		result.x = Float.parseFloat(parts[0]);
		result.y = Float.parseFloat(parts[1]);
		return result;
	}

	static Vector3f getVec3f(JSONObject obj, Object key) {
		String data = obj.get(key).toString();
		data = data.substring(1);
		data = data.substring(0, data.length() - 1);
		String[] parts = data.split(",");
		Vector3f result = new Vector3f();
		result.x = Float.parseFloat(parts[0]);
		result.y = Float.parseFloat(parts[1]);
		result.z = Float.parseFloat(parts[2]);
		return result;
	}

	static Vector4f getVec4f(JSONObject obj, Object key) {
		String data = obj.get(key).toString();
		data = data.substring(1);
		data = data.substring(0, data.length() - 1);
		String[] parts = data.split(",");
		Vector4f result = new Vector4f();
		result.x = Float.parseFloat(parts[0]);
		result.y = Float.parseFloat(parts[1]);
		result.z = Float.parseFloat(parts[2]);
		result.w = Float.parseFloat(parts[3]);
		return result;
	}

	static float getFloat(JSONObject obj, Object key) {
		return Float.parseFloat(obj.get(key).toString());
	}

	static String getString(JSONObject obj, Object key) {
		return obj.get(key).toString();
	}

	static float[] getVec3fBuffer(JSONObject obj, Object key) {
		JSONArray channelSource = (JSONArray) obj.get(key);
		int dataCount = channelSource.size();
		float[] dataArray = new float[dataCount * 3];
		for (int i = 0; i < dataCount; i++) {
			String data = channelSource.get(i).toString();
			data = data.substring(1);
			data = data.substring(0, data.length() - 1);
			String[] parts = data.split(",");
			dataArray[i * 3] = Float.parseFloat(parts[0]);
			dataArray[i * 3 + 1] = Float.parseFloat(parts[1]);
			dataArray[i * 3 + 2] = Float.parseFloat(parts[2]);
		}
		return dataArray;
	}

	static int[] getVec3iBuffer(JSONObject obj, Object key) {
		JSONArray channelSource = (JSONArray) obj.get(key);
		int dataCount = channelSource.size();
		int[] dataArray = new int[dataCount * 3];
		for (int i = 0; i < dataCount; i++) {
			String data = channelSource.get(i).toString();
			data = data.substring(1);
			data = data.substring(0, data.length() - 1);
			String[] parts = data.split(",");
			dataArray[i * 3] = Integer.parseInt(parts[0]);
			dataArray[i * 3 + 1] = Integer.parseInt(parts[1]);
			dataArray[i * 3 + 2] = Integer.parseInt(parts[2]);
		}
		return dataArray;
	}

	static float[] getVec2fBuffer(JSONObject model, Object key) {
		JSONArray channelSource = (JSONArray) model.get(key);
		int dataCount = channelSource.size();
		float[] dataArray = new float[dataCount * 2];
		for (int i = 0; i < dataCount; i++) {
			String data = channelSource.get(i).toString();
			data = data.substring(1);
			data = data.substring(0, data.length() - 1);
			String[] parts = data.split(",");
			dataArray[i * 2] = Float.parseFloat(parts[0]);
			dataArray[i * 2 + 1] = Float.parseFloat(parts[1]);
		}
		return dataArray;
	}

	// ENCODING

	static String jsonVec3(String name, Vector3f vec) {
		String str = "\"" + name + "\": [";
		if (vec.x == (int) vec.x) str += (int) vec.x;
		else str += vec.x;
		str += ", ";
		if (vec.y == (int) vec.y) str += (int) vec.y;
		else str += vec.y;
		str += ", ";
		if (vec.z == (int) vec.z) str += (int) vec.z;
		else str += vec.z;
		str += "]";
		return str;
	}

	static String jsonVec4(String name, Vector4f vec) {
		String str = "\"" + name + "\": [";
		if (vec.x == (int) vec.x) str += (int) vec.x;
		else str += vec.x;
		str += ", ";
		if (vec.y == (int) vec.y) str += (int) vec.y;
		else str += vec.y;
		str += ", ";
		if (vec.z == (int) vec.z) str += (int) vec.z;
		else str += vec.z;
		str += ", ";
		if (vec.w == (int) vec.w) str += (int) vec.w;
		else str += vec.w;
		str += "]";
		return str;
	}

	static String jsonFloat(String name, float value) {
		String str = "\"" + name + "\": ";
		if (value == (int) value) str += (int) value;
		else str += value;
		return str;
	}

	static String jsonString(String name, String str) {
		return "\"" + name + "\": \"" + str + "\"";
	}

	static void jsonVec2fAppend(StringBuilder str, float x, float y) {
		str.append("[");
		if (x == (int) x) str.append((int) x);
		else str.append(x);
		str.append(", ");
		if (y == (int) y) str.append((int) y);
		else str.append(y);
		str.append("]");
	}

	static void jsonVec3fAppend(StringBuilder str, float x, float y, float z) {
		str.append("[");
		if (x == (int) x) str.append((int) x);
		else str.append(x);
		str.append(", ");
		if (y == (int) y) str.append((int) y);
		else str.append(y);
		str.append(", ");
		if (z == (int) z) str.append((int) z);
		else str.append(z);
		str.append("]");
	}

	static void jsonVec3iAppend(StringBuilder str, int x, int y, int z) {
		str.append("[");
		str.append(x);
		str.append(", ");
		str.append(y);
		str.append(", ");
		str.append(z);
		str.append("]");
	}

	static void indent(StringBuilder str) {
		Scanner sc = new Scanner(str.toString());
		str.delete(0, str.length());
		while (sc.hasNextLine()) {
			str.append("\n\t");
			str.append(sc.nextLine());
		}
		str.delete(0, 1);
	}

}
