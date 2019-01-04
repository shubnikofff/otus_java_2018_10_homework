package ru.otus;

class Benchmark {
    private Object[] payload;

    Benchmark(int capacity) {
        payload = new Object[capacity];
    }

    void fill(int quantity) throws InterruptedException {
        System.out.println("Filling benchmark...");
        for (int i = 0; i < payload.length; i++) {
            for (int j = 0; j < quantity; j++) {
                payload[i] = new byte[j];
            }
//            Thread.sleep(1_000);
            Thread.sleep(100);
        }
    }
}
