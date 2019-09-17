package engine;

import java.util.Scanner;

public class ShaderBuilder {

	public static String genShader(String folder, String file) {
		StringBuilder full = new StringBuilder();
		Scanner sc = new Scanner(Resources.getAsString(folder + "/" + file));
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.startsWith("#include \"") && line.endsWith("\"")) {
				String newFile = line.substring(10);
				newFile = newFile.substring(0, newFile.length() - 1);
				full.append(genShader(folder, newFile));
			} else {
				full.append(line);
			}
			full.append('\n');
		}
		return full.toString();
	}

}
