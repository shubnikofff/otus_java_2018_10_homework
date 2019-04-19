package ru.otus;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuickSortTest {

	@Test
	public void testSortCorrect() {
		int[] ints = {5, 1, 6, 8, 9, 2, 3, 3, 10, 4};
		int[] sortedInts = {1, 2, 3, 3, 4, 5, 6, 8, 9, 10};

		QuickSort quickSort = new QuickSort(ints);
		quickSort.sort(ints);

		assertArrayEquals(sortedInts, ints);
	}
}
