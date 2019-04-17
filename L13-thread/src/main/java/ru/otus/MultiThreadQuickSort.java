package ru.otus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class MultiThreadQuickSort implements Sorting {
	private int threadQuantity;

	MultiThreadQuickSort(int threadQuantity) {
		this.threadQuantity = threadQuantity;
	}

	public void sort(int[] source) {
		var start = System.currentTimeMillis();

		QuickSort.sort(source, 0, source.length - 1);

//		var pivotIndexes = split(source);
//		var i1 = pivotIndexes[0];
//		var i2 = pivotIndexes[1];
//		var i3 = pivotIndexes[2];

//		QuickSort.sort(source, 0, i1 - 1);
//		QuickSort.sort(source, i1, i2 - 1);
//		QuickSort.sort(source, i2, i3 - 1);
//		QuickSort.sort(source, i3, source.length - 1);

//		var sorters = Arrays.asList(
//			new Thread(new QuickSort(source, 0, i1 - 1)),
//			new Thread(new QuickSort(source, i1, i2 - 1)),
//			new Thread(new QuickSort(source, i2, i3 - 1)),
//			new Thread(new QuickSort(source, i3, source.length - 1))
//		);
//
//		for (var sorter : sorters) {
//			sorter.start();
//		}
//		for (var sorter : sorters) {
//			try {
//				sorter.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

		System.out.println(System.currentTimeMillis() - start);
	}

	void split(int[] source) {
		int[] pivotIndexes = ThreadLocalRandom
				.current()
				.ints(threadQuantity - 1, 0, source.length - 1)
				.sorted()
				.toArray();

		int[] pivots = Arrays
				.stream(pivotIndexes)
				.map(index -> source[index])
				.sorted()
				.toArray();

		for (int i = 0; i < pivotIndexes.length; i++) {
			for (int j = 0; j < source.length; j++) {
				if(source[j] > source[pivotIndexes[i]]) {
					int temp = source[j];
					source[j] = source[pivotIndexes[i]];
					source[pivotIndexes[i]] = temp;
					pivotIndexes[i] = j;
				}
			}
		}

		System.out.println(Arrays.toString(pivotIndexes));
	}

	// TODO: Refactor this piece of shit with inplace swaps
//	int[] split(int[] source) {
//		var al1 = new ArrayList<Integer>();
//		var al2 = new ArrayList<Integer>();
//		var al3 = new ArrayList<Integer>();
//		var al4 = new ArrayList<Integer>();
//
//		int[] pivotIndexes = ThreadLocalRandom.current()
//				.ints(3, 0, source.length - 1)
//				.sorted()
//				.toArray();
//
//		int[] pivots = Arrays.stream(pivotIndexes).map(i -> source[i]).sorted().toArray();
//
//		for (int item : source) {
//			if (item >= pivots[2]) {
//				al4.add(item);
//			} else if (item >= pivots[1]) {
//				al3.add(item);
//			} else if (item >= pivots[0]) {
//				al2.add(item);
//			} else {
//				al1.add(item);
//			}
//		}
//
//		var mergedAl = new ArrayList<Integer>(source.length);
//		mergedAl.addAll(al1);
//		mergedAl.addAll(al2);
//		mergedAl.addAll(al3);
//		mergedAl.addAll(al4);
//
//		for (int i = 0; i < mergedAl.size(); ++i) {
//			source[i] = mergedAl.get(i);
//		}
//
//		pivotIndexes[0] = al1.size();
//		pivotIndexes[1] = pivotIndexes[0] + al2.size();
//		pivotIndexes[2] = pivotIndexes[1] + al3.size();
//
//		return pivotIndexes;
//	}


}
