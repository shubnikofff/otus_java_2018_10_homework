package ru.otus;

/*
-Xms512m
-Xmx512m

GC's has ben used:
-XX:+UseG1GC
-XX:+UseSerialGC
-XX:+UseParallelGC

As big data has been used:
    - capacity 5000
    - quantity 10000
Execution time:
    with 512Mb heap:
        - G1 36892ms
        - Serial 32681ms
        - Parallel 32936ms
    with 2048Mb heap:
        - G1 35721ms
        - Serial 33611ms
        - Parallel 33352ms

As small data has been used:
    - capacity 1000
    - quantity 5000
Execution time:
    - G1 3115ms
    - Serial 3098ms
    - Parallel 3037ms

Вывод:
    По скорости выполнения программы лучшими оказались Serial GC и Parallel GC. К тому же их графики (см. screenshots),
    взятые из VisualVM выглядят стабильней чем у G1. Среднее время работы одной операции у Parallel ниже чем у Serial и
    G1. Соответственно я делаю вывод, что в моей ситуации Parallel GC оказался лучшим.
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
