package platform.opengl;

import engine.Texture;

import static org.lwjgl.opengl.GL13.*;

public class GLTexture implements Texture {

	// Lo slot 0 lo uso come texture nulla.
	private static int nextSlot = 1;

	private int texture, slot;
	private boolean notYetDestroyed = true;

	public GLTexture(int[] argb, int width, int height) {
		assert argb.length == width * height;
//		int transparency = convertARGBtoABGR(argb[0]);
//		data[0] = 0;
//		for (int i = 1; i < width * height; i++) {
//			data[i] = convertARGBtoABGR(argb[i]);
//			if (data[i] == transparency) {
//				data[i] = 0;
//			}
//		}
		for (int i = 0; i < width * height; i++) {
			argb[i] = convertARGBtoABGR(argb[i]);
		}
		texture = glGenTextures();
		int param = GL_CLAMP_TO_EDGE; // GL_REPEAT
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, param);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, param);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, argb);
		unbind();
		slot = nextSlot++;
	}

	private static int convertARGBtoABGR(int color) {
		int a = (color & 0xff000000) >> 24;
		int r = (color & 0xff0000) >> 16;
		int g = (color & 0xff00) >> 8;
		int b = (color & 0xff);
		return a << 24 | b << 16 | g << 8 | r;
	}

	public int getSlot() {
		return slot;
	}

	public void bind() {
		glActiveTexture(GL_TEXTURE0 + slot);
		glBindTexture(GL_TEXTURE_2D, texture);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GLTexture glTexture = (GLTexture) o;
		return texture == glTexture.texture;
	}

	public void destroy() {
		if (notYetDestroyed) {
			notYetDestroyed = false;
			glDeleteTextures(texture);
		}
	}

}
