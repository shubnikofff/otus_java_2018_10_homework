package ru.otus;

import java.util.concurrent.ThreadLocalRandom;

class QuickSort implements Runnable {
	private int[] source;
	private int initialLeftBorder;
	private int initialRightBorder;

	QuickSort(int[] source, int leftBorder, int rightBorder) {
		this.source = source;
		initialLeftBorder = leftBorder;
		initialRightBorder = rightBorder;
	}

	@Override
	public void run() {
		sort(initialLeftBorder, initialRightBorder);
	}

	int sort(int leftBorder, int rightBorder) {
		int i = leftBorder;
		int j = rightBorder;
		int pivotIndex = ThreadLocalRandom.current().nextInt(leftBorder, rightBorder);
		int pivot = source[pivotIndex];
		System.out.println("Pivot: " + pivot);

		do {
			while (source[i] < pivot) {
				i++;
			}
			while (source[j] > pivot) {
				j--;
			}

			if (i <= j) {
				if(i < j) {
					System.out.println("Swap " + source[i] + " and " + source[j]);
					int temp = source[i];
					source[i] = source[j];
					source[j] = temp;
				}

				i++;
				j--;
			}
		} while (i <= j);

		if (i < rightBorder) {
			sort(i, rightBorder);
		}

		if (leftBorder < j) {
			sort(leftBorder, j);
		}

		return pivotIndex;
	}
}
