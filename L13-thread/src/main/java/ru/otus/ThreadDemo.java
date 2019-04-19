package ru.otus;

public class ThreadDemo {
	private static final int THREAD_COUNT = 4;
	private static final int ARRAY_SIZE = 100_000_000;
	private static final int ARRAY_ELEMENT_BOUND = 1_000_000;

	public static void main(String[] args) {
		int[] ints;
		ArrayGenerator arrayGenerator = new ArrayGenerator(ARRAY_SIZE, ARRAY_ELEMENT_BOUND);

		System.out.println("Run sort in single thread...");
		ints = arrayGenerator.generate();
		new Sorter(new QuickSort(ints)).sort(ints);

		System.out.println("\nRun sort in " + THREAD_COUNT + " thread...");
		ints = arrayGenerator.generate();
		new Sorter(new MultiThreadQuickSort(THREAD_COUNT)).sort(ints);
	}
}
