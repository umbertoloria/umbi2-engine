package layout;

import render.Scene2D;

import java.util.ArrayList;
import java.util.List;

public class GPanel extends GComponent {

	private int padding = 1;
	private int columns = 1;

	public void setColumns(int columns) {
		this.columns = columns;
	}

	private int rowGap = 1;
	private int colGap = 1;

	private List<GComponent> components = new ArrayList<>();

	public void add(GComponent component) {
		components.add(component);
		recalculateDimensions();
	}

	private void recalculateDimensions() {

		// INNER WIDTH
		int curInnerWidth = 0;
		int innerWidth = 0;

		int i = 0;
		for (GComponent component : components) {
			curInnerWidth += component.getWidth();
			if (i % columns == columns - 1) {
				innerWidth = Math.max(innerWidth, curInnerWidth);
				curInnerWidth = 0;
			} else {
				curInnerWidth += colGap;
			}
			i++;
		}

		if (components.size() % columns != 0) {
			curInnerWidth -= colGap;
			innerWidth = Math.max(innerWidth, curInnerWidth);
			// curInnerWidth = 0;
		}


		// INNER HEIGHT
		i = 0;
		int curRowMaxHeightComp = 0;
		int innerHeight = 0;
		for (GComponent component : components) {
			curRowMaxHeightComp = Math.max(curRowMaxHeightComp, component.getHeight());
			if (i % columns == columns - 1) {
				innerHeight += curRowMaxHeightComp + rowGap;
				curRowMaxHeightComp = 0;
			}
			i++;
		}

		if (components.size() % columns != 0) {
			innerHeight += curRowMaxHeightComp + rowGap;
			// curRowMaxHeightComp = 0;
		}

		innerHeight -= rowGap;

		setWidth(padding * 2 + innerWidth);
		setHeight(padding * 2 + innerHeight);
	}

	public void update(float ts) {
		super.update(ts);
		for (GComponent component : components) {
			component.update(ts);
		}
	}

	public void draw(Scene2D scene, int x, int y, int z) {
		super.draw(scene, x, y, z);
		x += padding;
		y += padding;
		z++;

		int i = 0;
		int offset = 0;
		int curRowHeight = 0;

		for (GComponent component : components) {

			component.draw(scene, x + offset, y, z);
			offset += component.getWidth() + colGap;
			curRowHeight = Math.max(curRowHeight, component.getHeight());

			if (i % columns == columns - 1) {
				offset = 0;
				y += curRowHeight + rowGap;
			}

			i++;
		}

	}

	public void pack(int x, int y) {

		int innerWidth = 0;
		int outerHeight = padding;

		int curRowWidth = 0;
		int curHeightRow = 0;

		int i = 0;
		for (GComponent component : components) {

			// Aggiusta le dimensioni proprie e dei suoi componenti
			component.pack(x + padding + curRowWidth, y + outerHeight);

			// Prelevo la (possibile) nuova larghezza
			curRowWidth += component.getWidth();

			// Prelevo la (possibile) nuova altezza
			curHeightRow = Math.max(curHeightRow, component.getHeight());

			if (i % columns == columns - 1) {
				// Finita questa riga, conosco la sua altezza
				outerHeight += curHeightRow + rowGap;
				// ...anche la sua lunghezza...
				innerWidth = Math.max(innerWidth, curRowWidth);
				// Si riprende da capo con la prossima riga
				curRowWidth = 0;
			} else {
				// La riga non è ancora finita (o forse è finita ma la tabella non è "rettangolare")
				curRowWidth += colGap;
			}

			i++;
		}

		if (components.size() % columns != 0) {
			// Questa è l'ultima cella di questa tabella non "rettangolare". Attenzione al colGap
			// TODO: testare senza sottrazione
			innerWidth = Math.max(innerWidth, curRowWidth - colGap);
			// curWidthRow = 0;
			outerHeight += curHeightRow + rowGap;
			// curHeightRow = 0;
		}
		// C'è sempre da togliere l'ultimo rowGap
		outerHeight -= rowGap;
		outerHeight += padding;

		setX(x);
		setY(y);
		setWidth(padding * 2 + innerWidth);
		setHeight(outerHeight);

	}

}
