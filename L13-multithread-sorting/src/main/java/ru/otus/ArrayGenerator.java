package ru.otus;

import java.util.concurrent.ThreadLocalRandom;

public class ArrayGenerator {
	private int arraySize;
	private int elementBound;

	public ArrayGenerator(int arraySize, int elementBound) {
		this.arraySize = arraySize;
		this.elementBound = elementBound;
	}

	public int[] generate() {
		return ThreadLocalRandom.current()
				.ints(arraySize, 0, elementBound)
				.toArray();
	}
}
