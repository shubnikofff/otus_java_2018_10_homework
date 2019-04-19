package ru.otus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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


	void multiSort(int[] source) {
		var start = System.currentTimeMillis();

		List<Thread> threadPool = getThreadPool(source);

		threadPool.forEach(Thread::start);

		threadPool.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		System.out.println(System.currentTimeMillis() - start);
	}

	private List<Thread> getThreadPool(int[] source) {


//		int[] pivotIndexes = ThreadLocalRandom
//				.current()
//				.ints(threadQuantity - 1, 0, source.length - 1)
//				.sorted()
//				.toArray();

//		int[] pivots = Arrays
//				.stream(pivotIndexes)
//				.map(index -> source[index])
//				.sorted()
//				.toArray();
		int[] pivots = ThreadLocalRandom.current()
				.ints(threadQuantity - 1, 0, source.length - 1)
				.map(index -> source[index])
				.sorted()
				.toArray();

//		int[][] result = new int[pivots.length][2];
//		System.out.println(Arrays.toString(pivots));
		List<Thread> threadPool = new ArrayList<>(threadQuantity);

		int leftBorder = 0;
//		int[] result = new int[2];
		for (int pivot : pivots) {
//			System.out.println(Arrays.toString(QuickSort.splitAndSwap(source, 0, source.length - 1, pivot)));
			int[] result = QuickSort.splitAndSwap(source, 0, source.length - 1, pivot);
			threadPool.add(new Thread(new QuickSort(source, leftBorder, result[0]), String.valueOf(leftBorder)));

//			Thread thread = new Thread(new QuickSort(source, leftBorder, result[0]), "Thread with pivot>>>" + pivot);
//			thread.start();
//			try {
//				thread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			leftBorder = result[0];
		}

		threadPool.add(new Thread(new QuickSort(source, leftBorder, source.length - 1), String.valueOf(leftBorder)));


		return threadPool;
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
