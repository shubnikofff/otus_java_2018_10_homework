package ru.otus;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSort implements Runnable {
	private int[] source;
	private int leftBorder;
	private int rightBorder;

	public QuickSort(int[] source, int leftBorder, int rightBorder) {
		this.source = source;
		this.leftBorder = leftBorder;
		this.rightBorder = rightBorder;
	}

	@Override
	public void run() {

	}

	private void doSort(int leftBorder, int rightBorder) {
		int i = leftBorder;
		int j = rightBorder;
		int pivot = source[ThreadLocalRandom.current().nextInt(leftBorder, rightBorder)];
		System.out.println("Pivot: " + pivot);

//		split(source, i, j);
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
			quickSort(source, i, rightBorder);
		}

		if (leftBorder < j) {
			quickSort(source, leftBorder, j);
		}
	}
}
