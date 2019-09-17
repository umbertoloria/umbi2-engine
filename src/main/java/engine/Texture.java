package engine;

import platform.opengl.GLTexture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public interface Texture {

	void bind();

	void unbind();

	void destroy();

	static Texture load(String path) {
		int[] argb;
		int width, height;
		try {
			InputStream is = Resources.getResource(path);
			if (is == null) {
				return null;
			}
			BufferedImage image = ImageIO.read(is);
			width = image.getWidth();
			height = image.getHeight();
			argb = new int[width * height];
			image.getRGB(0, 0, width, height, argb, 0, width);
			return new GLTexture(argb, width, height);
		} catch (IOException e) {
			throw new IllegalStateException("Non riesco a trovare questa risorsa");
		}
	}

	static Texture create(int[] argb, int width, int height) {
		return new GLTexture(argb, width, height);
	}

}
