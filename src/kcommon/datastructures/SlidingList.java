package kcommon.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SlidingList<T> {
	private List<T> elements;
	private int maxSize;
	
	public SlidingList(int size) {
		maxSize = size;
		elements = new ArrayList<T>(size);
	}

	public List<T> getElements() {
		return Collections.unmodifiableList(elements);
	}
	
	public void push(T element) {
		if (elements.size() == maxSize) {
			Collections.rotate(elements, -1);
			elements.remove(elements.size() - 1);
		}
		elements.add(element);
	}
}
