package ru.otus;

/*
As big data has been used:
    - capacity 5000
    - quantity 10000
Execution time:
    - G1 36892ms
    - Serial 32681ms
    - Parallel 32936ms

As big data has been used:
    - capacity 1000
    - quantity 5000
Execution time:
    - G1 3115ms
    - Serial 3098ms
    - Parallel 3037ms
 */

public class GcDemo {
    public static void main(String[] args) throws InterruptedException {
        int capacity = Integer.parseInt(args[0]);
        int quantity = Integer.parseInt(args[1]);
        var benchmark = new Benchmark(capacity);
        long beginTime = System.currentTimeMillis();
        benchmark.fill(quantity);
        System.out.println("Total time: " + (System.currentTimeMillis() - beginTime) + "ms");
    }
}
