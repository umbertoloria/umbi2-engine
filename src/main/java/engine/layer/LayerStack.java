package engine.layer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LayerStack {

	private Stack<Layer> stack = new Stack<>();
	private Queue<Layer> queue = new LinkedList<>();

	public void add(Layer layer) {
		layer.attach();
		queue.add(layer);
		stack.add(layer);
	}

	public void destroy() {
		for (Layer layer : descending()) {
			layer.detach();
		}
		queue.clear();
		stack.clear();
	}

	public Iterable<Layer> ascending() {
		return queue;
	}

	public Iterable<Layer> descending() {
		return stack;
	}

}
