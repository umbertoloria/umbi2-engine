package render;

import engine.Mesh;

public class Picture {

	public final Mesh mesh;
	public final Material material;
	private int references = 0;

	public Picture(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
	}

	private void destroy() {
		mesh.destroy();
		material.destroy();
	}

	public final void addRef() {
		references++;
	}

	public final void removeRef() {
		if (references == 0) {
			throw new RuntimeException("Dovrebbe già essere distrutto");
		}
		references--;
		if (references == 0) {
			destroy();
		}
	}

}
