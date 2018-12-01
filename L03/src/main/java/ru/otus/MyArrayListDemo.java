package ru.otus;

import java.util.Collections;

public class MyArrayListDemo {

    public static void main(String[] args) {
        MyArrayList<String> firstList = new MyArrayList();
        MyArrayList<String> secondList = new MyArrayList(103);

        for (int i = 0; i < 100; i++) {
            firstList.add("string element");
        }

        Collections.addAll(firstList, "more string element", "one more string element", "and one more...");
        Collections.copy(secondList, firstList);
        Collections.sort(secondList);

        System.out.println("First list:\n" + firstList);
        System.out.println("Second list:\n" + secondList);
    }
}
