package ru.otus;

public class Main {
    private final static int ARRAY_SIZE = 20_000;
    private final static int HUGE_ARRAY_SIZE = 100_000_000;

    public static void main(String[] args) {
        System.out.println("Short size: " + Measurer.measureShort());
        System.out.println("Int size: " + Measurer.measureInt());
        System.out.println("Long size: " + Measurer.measureLong());
        System.out.println("Object size: " + Measurer.measure(() -> new String(new char[0]), ARRAY_SIZE));
        System.out.println("Object size: " + Measurer.measure(() -> new String(new byte[0]), ARRAY_SIZE));
        System.out.println("Object size: " + Measurer.measure(() -> new MyClass(), ARRAY_SIZE));
        System.out.println("Object size: " + Measurer.measure(() -> new MyClass(), HUGE_ARRAY_SIZE));
    }

    private static class MyClass {
        private byte b = 0;
        private int i = 0;
        private long l = 1;
    }
}