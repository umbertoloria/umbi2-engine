package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {

	private int vao, vbo, ibo, count;
	private int attribs;

	public Mesh(float[] vertices, int[] indices, Layout layout) {
		vao = glGenVertexArrays();
		vbo = createVBO(vertices, layout);
		ibo = createIBO(indices);
		count = indices.length;
	}

	private int createVBO(float[] vertices, Layout layout) {
		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glBindVertexArray(vao);
		int nextAttrib = 0;
		for (Element elem : layout) {
			glVertexAttribPointer(nextAttrib, elem.count, elem.baseType, false, layout.stride, elem.getOffset());
			glEnableVertexAttribArray(nextAttrib++);
		}
		attribs = nextAttrib;
		return vbo;
	}

	private int createIBO(int[] indices) {
		int ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		return ibo;
	}

	public void draw() {
		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
	}

	public void destroy() {

		// Destroying IBO
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDeleteBuffers(ibo);

		// Destroying VAO and its Attributes
		for (--attribs; attribs >= 0; attribs--) {
			glDisableVertexAttribArray(attribs);
		}
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);

		// Destroying VBOs
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vbo);

	}

	public static class Layout implements Iterable<Element> {

		private final int stride;
		private List<Element> elements = new ArrayList<>();

		public Layout(Element... elems) {
			int stride = 0;
			for (Element element : elems) {
				element.setOffset(stride);
				elements.add(element);
				stride += element.size;
			}
			this.stride = stride;
		}

		public Iterator<Element> iterator() {
			return elements.iterator();
		}

	}

	public static class Element {

		final String name;
		final Shader.Type type;
		final int count;
		final int size;
		private int offset = -1;
		final int baseType;

		// TODO: Fare a meno di questo "name" e passare direttamente Shader.Type a Layer (non Element).
		public Element(String name, Shader.Type type) {
			this.name = name;
			this.type = type;
			assert type != null;
			switch (type) {
				case Float:
					count = 1;
					break;
				case Float2:
					count = 2;
					break;
				case Float3:
					count = 3;
					break;
				case Float4:
					count = 4;
					break;
				default:
					count = 0;
					break;
			}
			size = count * Float.BYTES;
			baseType = GL_FLOAT;
		}

		int getOffset() {
			return offset;
		}

		void setOffset(int offset) {
			if (this.offset == -1) {
				this.offset = offset;
			}
		}

	}

}
