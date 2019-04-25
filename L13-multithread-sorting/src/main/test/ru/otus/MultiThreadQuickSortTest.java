package ru.otus;

import org.junit.Test;

import static org.junit.Assert.*;

public class MultiThreadQuickSortTest {

	@Test
	public void testSortCorrect() {
		int[] ints = {5, 1, 6, 8, 9, 2, 3, 3, 10, 4};
		int[] sortedInts = {1, 2, 3, 3, 4, 5, 6, 8, 9, 10};

		MultiThreadQuickSort multiThreadQuickSort = new MultiThreadQuickSort(2);
		multiThreadQuickSort.sort(ints);

		assertArrayEquals(sortedInts, ints);
	}
}
