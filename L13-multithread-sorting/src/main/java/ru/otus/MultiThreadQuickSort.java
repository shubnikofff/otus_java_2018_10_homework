package ru.otus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class MultiThreadQuickSort implements Sorting {
	private int threadQuantity;

	MultiThreadQuickSort(int threadQuantity) {
		this.threadQuantity = threadQuantity;
	}

	public void sort(int[] source) {
		List<Thread> threadPool = getThreadPool(source);

		threadPool.forEach(Thread::start);

		threadPool.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	private List<Thread> getThreadPool(int[] source) {
		int[] pivots = ThreadLocalRandom.current()
				.ints(threadQuantity - 1, 0, source.length - 1)
				.map(index -> source[index])
				.sorted()
				.toArray();

		List<Thread> threadPool = new ArrayList<>(threadQuantity);

		int leftBorder = 0;
		for (int pivot : pivots) {
			int[] result = QuickSort.splitAndSwap(source, 0, source.length - 1, pivot);
			threadPool.add(new Thread(new QuickSort(source, leftBorder, result[0]), String.valueOf(leftBorder)));
			leftBorder = result[0];
		}
		threadPool.add(new Thread(new QuickSort(source, leftBorder, source.length - 1), String.valueOf(leftBorder)));

		return threadPool;
	}
}
