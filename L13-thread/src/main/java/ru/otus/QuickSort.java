package ru.otus;

import java.util.concurrent.ThreadLocalRandom;

class QuickSort implements Runnable {
	private int[] source;
	private int leftBorder;
	private int rightBorder;

	QuickSort(int[] source, int leftBorder, int rightBorder) {
		this.source = source;
		this.leftBorder = leftBorder;
		this.rightBorder = rightBorder;
	}

	@Override
	public void run() {
		sort(source, leftBorder, rightBorder);
	}

	static void sort(int[] source, int leftBorder, int rightBorder) {
		if (leftBorder == rightBorder) {
			return;
		}

		int i = leftBorder;
		int j = rightBorder;
		var randomIndex = ThreadLocalRandom.current().nextInt(leftBorder, rightBorder);
		int pivot = source[randomIndex];

//		System.out.println("Pivot: " + pivot);

		do {
			while (source[i] < pivot) {
				i++;
			}
			while (source[j] > pivot) {
				j--;
			}

			if (i <= j) {
				if(i < j) {
//					System.out.println("Swap " + source[i] + " and " + source[j]);
					int temp = source[i];
					source[i] = source[j];
					source[j] = temp;
				}

				i++;
				j--;
			}
		} while (i <= j);

		if (i < rightBorder) {
			sort(source, i, rightBorder);
		}

		if (leftBorder < j) {
			sort(source, leftBorder, j);
		}
	}
}
