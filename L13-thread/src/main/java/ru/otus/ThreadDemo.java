package ru.otus;

import java.util.Arrays;
import java.util.Random;

public class ThreadDemo {
	public static void main(String[] args) {
		int[] array = generateArray(50);
		System.out.println(Arrays.toString(array));
	}

	private static int[] generateArray(int size) {
		int[] ints = new int[size];
		Random random = new Random();

		for (int i = 0; i < ints.length; i++) {
			ints[i] = random.nextInt();
		}

		return ints;
	}
}
