package ru.otus;

public class MultiThreadQuickSort implements Sorting {
	private int threadQuantity;

	public MultiThreadQuickSort(int threadQuantity) {
		this.threadQuantity = threadQuantity;
	}

	public void sort(int[] source) {
		new QuickSort(source, 0, source.length - 1).;
	}
}
