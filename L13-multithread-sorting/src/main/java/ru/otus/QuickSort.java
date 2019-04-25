package ru.otus;

import java.util.concurrent.ThreadLocalRandom;

class QuickSort implements Runnable, Sorting {
	private int[] source;
	private int leftBorder;
	private int rightBorder;

	QuickSort(int[] source, int leftBorder, int rightBorder) {
		this.source = source;
		this.leftBorder = leftBorder;
		this.rightBorder = rightBorder;
	}

	QuickSort(int[] source) {
		this.source = source;
		leftBorder = 0;
		rightBorder = source.length - 1;
	}

	@Override
	public void run() {
		System.out.println("Run thread " + Thread.currentThread().getName());
		sort(source);
	}

	@Override
	public void sort(int[] source) {
		sort(source, leftBorder, rightBorder);
	}

	private void sort(int[] source, int leftBorder, int rightBorder) {
		if (leftBorder == rightBorder) {
			return;
		}

		var randomIndex = ThreadLocalRandom.current().nextInt(leftBorder, rightBorder);
		int pivot = source[randomIndex];

		int[] result = splitAndSwap(source, leftBorder, rightBorder, pivot);

		if (result[0] < rightBorder) {
			sort(source, result[0], rightBorder);
		}

		if (leftBorder < result[1]) {
			sort(source, leftBorder, result[1]);
		}

	}

	static int[] splitAndSwap(int[] source, int leftBorder, int rightBorder, int pivot) {
		int i = leftBorder;
		int j = rightBorder;

		do {
			while (source[i] < pivot) {
				i++;
			}
			while (source[j] > pivot) {
				j--;
			}

			if (i <= j) {
				if (i < j) {
					int temp = source[i];
					source[i] = source[j];
					source[j] = temp;
				}

				i++;
				j--;
			}
		} while (i <= j);

		return new int[]{i, j};
	}
}
