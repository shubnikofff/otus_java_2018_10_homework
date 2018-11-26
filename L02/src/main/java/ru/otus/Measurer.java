package ru.otus;

import java.util.function.Supplier;

class Measurer {
    private final static int ARRAY_SIZE = 20_000_000;

    static long measureInt() {
        System.out.println("Measure int...");

        long memBeforeCreate = getMem();
        System.out.println("Memory: " + memBeforeCreate);

        int[] array = new int[ARRAY_SIZE];
        long memAfterCreate = getMem();
        System.out.println("Ref size: " + (memAfterCreate - memBeforeCreate) / ARRAY_SIZE);

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return (getMem() - memBeforeCreate) / array.length;
    }


    static long measureLong() {
        System.out.println("Measure long...");

        long memBeforeCreate = getMem();
        System.out.println("Memory: " + memBeforeCreate);

        long[] array = new long[ARRAY_SIZE];
        long memAfterCreate = getMem();
        System.out.println("Ref size: " + (memAfterCreate - memBeforeCreate) / ARRAY_SIZE);

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return (getMem() - memBeforeCreate) / array.length;
    }

    static long measureShort() {
        System.out.println("Measure short...");

        long memBeforeCreate = getMem();
        System.out.println("Memory: " + memBeforeCreate);

        short[] array = new short[ARRAY_SIZE];
        long memAfterCreate = getMem();
        System.out.println("Ref size: " + (memAfterCreate - memBeforeCreate) / ARRAY_SIZE);

        for (int i = 0; i < array.length; i++) {
            array[i] = (short) i;
        }
        return (getMem() - memBeforeCreate) / array.length;
    }

    static <T> long measure(Supplier<T> objectGetter) {
        System.out.println("Measure " + objectGetter.get().getClass().getName() + "...");

        long memBeforeCreate = getMem();
        System.out.println("Memory: " + memBeforeCreate);

        Object[] array = new Object[ARRAY_SIZE];
        long memAfterCreate = getMem();
        System.out.println("Ref size: " + (memAfterCreate - memBeforeCreate) / ARRAY_SIZE);

        for (int i = 0; i < array.length; i++) {
            array[i] = objectGetter.get();
        }
        return (getMem() - memBeforeCreate) / array.length;
    }

    private static long getMem() {
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
