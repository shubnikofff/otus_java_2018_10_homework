package ru.otus;

import java.util.Random;

public class ThreadDemo {
	private static final int THREAD_COUNT = 4;
	private static final int ARRAY_SIZE = 1_000_000_000;
	private static final int ARRAY_ELEMENT_BOUND = 1_000_000;
	private static final Random random = new Random();

	public static void main(String[] args) {
		int[] ints;

		System.out.println("Run sort in single thread...");
		ints = generateArray();
		new Sorter(new QuickSort(ints)).sort(ints);

		System.out.println("\nRun sort in " + THREAD_COUNT + " thread...");
		ints = generateArray();
		new Sorter(new MultiThreadQuickSort(THREAD_COUNT)).sort(ints);
	}

	private static int[] generateArray() {
		int[] ints = new int[ARRAY_SIZE];

		for (int i = 0; i < ints.length; i++) {
			ints[i] = random.nextInt(ARRAY_ELEMENT_BOUND);
		}

		return ints;
	}
}
