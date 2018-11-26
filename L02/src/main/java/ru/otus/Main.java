package ru.otus;

public class Main {
    public static void main(String[] args) {
        System.out.println("Short size: " + Measurer.measureShort());
        System.out.println("Int size: " + Measurer.measureInt());
        System.out.println("Long size: " + Measurer.measureLong());
        System.out.println("Object size: " + Measurer.measure(() -> new String(new char[0])));
        System.out.println("Object size: " + Measurer.measure(() -> new String(new byte[0])));
        System.out.println("Object size: " + Measurer.measure(() -> new MyClass()));
    }

    private static class MyClass {
        private byte b = 0;
        private int i = 0;
        private long l = 1;
    }
}