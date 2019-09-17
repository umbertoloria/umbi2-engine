package engine;

import java.io.InputStream;
import java.util.Scanner;

public class Resources {

	public static InputStream getResource(String path) {
		return Resources.class.getClassLoader().getResourceAsStream(path);
	}

	public static String getAsString(String path) {
		Scanner sc = new Scanner(getResource(path));
		StringBuilder str = new StringBuilder();
		while (sc.hasNextLine()) {
			str.append(sc.nextLine());
			str.append('\n');
		}
		sc.close();
		return str.toString();
	}

}
