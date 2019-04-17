package ru.otus;

import java.util.Arrays;
import java.util.Random;

public class ThreadDemo {
	private static final int THREAD_COUNT = 4;
	private static final int ARRAY_LENGTH = 20;
	private static final Random random = new Random();

	public static void main(String[] args) {
		int[] ints = generateArray(ARRAY_LENGTH, 100);
		System.out.println(Arrays.toString(ints));

//		int i2 = split(ints, 0, ints.length - 1);
//		int i1 = split(ints, 0, i2 - 1);
//		int i3 = split(ints, i2, ints.length - 1);

//		System.out.println(Arrays.toString(ints));

		// 0 : i1 - 1
		// i1 : i2 - 1
		// i2 : i3 - 1
		// i3 : ints.length - 1

//		System.out.println("0 : " + (i1 - 1));
//		System.out.println(i1 + " : " + (i2 - 1));
//		System.out.println(i2 + " : " + (i3 - 1));
//		System.out.println(i3 + " : " + (ints.length - 1));
//
//

//		quickSort(ints, 0, i1 - 1);
//		quickSort(ints, 0, i2 - 1);
//		quickSort(ints, 0, i3 - 1);
//		quickSort(ints, 0, ints.length - 1);

//		quickSort(ints, 0, ints.length - 1);

//		final QuickSort quickSort = new QuickSort(ints, 0, ints.length - 1);
//		quickSort.run();

		MultiThreadQuickSort multiThreadQuickSort = new MultiThreadQuickSort(THREAD_COUNT);
//		multiThreadQuickSort.sort(ints);
		multiThreadQuickSort.split(ints);

//		System.out.println(Arrays.toString(ints));
	}

	private static int[] generateArray(int size, int bound) {
		int[] ints = new int[size];
		Random random = new Random();

		for (int i = 0; i < ints.length; i++) {
			ints[i] = random.nextInt(bound);
		}

		return ints;
	}

	private static int[] getBounds() {
		return random.ints(THREAD_COUNT - 1, 0, ARRAY_LENGTH).sorted().toArray();
	}

	private static void printBunchSize(int[] bounds) {
		System.out.println(Arrays.toString(bounds));
		System.out.println(bounds[0]);
		for (int i = 1; i < bounds.length; i++) {
			System.out.println(bounds[i] - bounds[i - 1]);
		}
		System.out.println(ARRAY_LENGTH - bounds[bounds.length - 1]);
		System.out.println("===========================================");
	}
}
