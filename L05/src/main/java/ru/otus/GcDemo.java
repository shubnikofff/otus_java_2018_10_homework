package ru.otus;

/*
Program arguments:
- benchMarkCapacity: 5000
- benchMarkQuantity 10000

GC param:
    -Xms512m
    -Xmx512m

Results:
    -XX:+UseParallelGC
        PS MarkSweep: 0
        PS Scavenge: 1416
        GC has been run 157 times per minute
        Total time: 558451ms

    -XX:+UseG1GC
        G1 Young Generation: 789
        G1 Old Generation: 0
        GC has been run 87 times per minute
        Total time: 565343ms

    -XX:+UseSerialGC
        Copy: 1756
        MarkSweepCompact: 0
        GC has been run 146 times per minute
        Total time: 767173ms

Вывод:
    По скорости выполнения программы лучшими стали ParallelGC и G1GC. Но колличество запусков в минуту у G1 оказалось
    вдвое меньше чем у Parallel. Исходя из этого можно сделать вывод, что лучшим при данной конфигурации был G1.
 */

import com.sun.management.GarbageCollectionNotificationInfo;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GcDemo {
    public static void main(String[] args) throws InterruptedException {
        int benchMarkCapacity = Integer.parseInt(args[0]);
        int benchMarkQuantity = Integer.parseInt(args[1]);

        var gcRunCounter = logGcEvents();
        var benchmark = new Benchmark(benchMarkCapacity);

        long beginTime = System.currentTimeMillis();
        benchmark.fill(benchMarkQuantity);
        long totalTime = System.currentTimeMillis() - beginTime;
        long totalTimeInMinutes = TimeUnit.MILLISECONDS.toMinutes(totalTime);

        int totalRunQuantity = 0;
        for(int runQuantity: gcRunCounter.values()) {
            totalRunQuantity += runQuantity;
        }

        var gcRunQuantityPerMinute = totalTimeInMinutes != 0 ? totalRunQuantity / totalTimeInMinutes : totalRunQuantity;

        gcRunCounter.forEach((gcName, runQuantity) ->
            System.out.println(gcName + ": " + runQuantity)
        );

        System.out.println("GC has been run " + gcRunQuantityPerMinute + " times per minute");
        System.out.println("Total time: " + totalTime + "ms");
    }

    private static HashMap<String, Integer> logGcEvents() {
        HashMap<String, Integer> runCounter = new HashMap<>();

        for (GarbageCollectorMXBean gcMxBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            runCounter.put(gcMxBean.getName(), 0);

            NotificationListener listener = (notification, handback) -> {
                var info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                Integer runQuantity = runCounter.get(info.getGcName());
                runCounter.replace(info.getGcName(), runQuantity, runQuantity + 1);
            };

            NotificationFilter filter = (notification) ->
                    notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION);

            NotificationEmitter emitter = (NotificationEmitter) gcMxBean;
            emitter.addNotificationListener(listener, filter, null);
        }

        return runCounter;
    }
}
