package ru.otus;

public class Sorter implements Sorting {
	private Sorting sorting;

	public Sorter(Sorting sorting) {
		this.sorting = sorting;
	}

	@Override
	public void sort(int[] source) {
		long startTime = System.currentTimeMillis();
		sorting.sort(source);
		System.out.println("Work time: " + (System.currentTimeMillis() - startTime));
	}
}
