package ru.otus;

class Benchmark {
    private Object[] payload;

    Benchmark(int capacity) {
        payload = new Object[capacity];
    }

    void fill(int quantity) throws InterruptedException {
        for (int i = 0; i < payload.length; i++) {
            for (int j = 0; j < quantity; j++) {
                payload[i] = new byte[j];
            }
            Thread.sleep(1);
        }
    }
}
