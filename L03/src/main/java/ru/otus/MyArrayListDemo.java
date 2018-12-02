package ru.otus;

import java.util.Collections;

public class MyArrayListDemo {

    public static void main(String[] args) {
        MyArrayList<Integer> firstList = new MyArrayList<>();
        MyArrayList<Integer> secondList = new MyArrayList<>(100);

        System.out.println("First list initialized:\n" + firstList);
        System.out.println("Second list initialized:\n" + secondList + "\n");

        for (int i = 100; i > 0; i--) {
            firstList.add(i);
            secondList.add(i * 2);
        }

        System.out.println("First list filled:\n" + firstList);
        System.out.println("Second list filled:\n" + secondList + "\n");

        Collections.addAll(secondList, 555, 666, 777, 888, 999);
        System.out.println("Add 5 elements to second list:\n" + secondList + "\n");

        Collections.copy(secondList, firstList);
        System.out.println("Copy first list to second list:\n" + secondList + "\n");

        Collections.sort(secondList);
        System.out.println("Sorted second list:\n" + secondList);
    }
}
