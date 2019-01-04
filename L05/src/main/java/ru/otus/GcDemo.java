package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/*
Program arguments:
- benchMarkCapacity: 5000
- benchMarkQuantity 10000

GC param:
    -Xms512m
    -Xmx512m

Output:
    -XX:+UseParallelGC
        PS MarkSweep (run quantity: 0, duration: 0ms)
        PS Scavenge (run quantity: 0, duration: 0ms)
        PS MarkSweep (run quantity: 0, duration: 0ms)
        PS Scavenge (run quantity: 157, duration: 111ms)
        PS MarkSweep (run quantity: 0, duration: 0ms)
        PS Scavenge (run quantity: 156, duration: 92ms)
        PS MarkSweep (run quantity: 0, duration: 0ms)
        PS Scavenge (run quantity: 156, duration: 87ms)
        PS MarkSweep (run quantity: 0, duration: 0ms)
        PS Scavenge (run quantity: 157, duration: 90ms)
        ...

    -XX:+UseG1GC
        G1 Young Generation (run quantity: 0, duration: 0ms)
        G1 Old Generation (run quantity: 0, duration: 0ms)
        G1 Young Generation (run quantity: 87, duration: 121ms)
        G1 Old Generation (run quantity: 0, duration: 0ms)
        G1 Young Generation (run quantity: 87, duration: 83ms)
        G1 Old Generation (run quantity: 0, duration: 0ms)
        G1 Young Generation (run quantity: 86, duration: 85ms)
        G1 Old Generation (run quantity: 0, duration: 0ms)
        ...

    -XX:+UseSerialGC
        Copy (run quantity: 0, duration: 0ms)
        MarkSweepCompact (run quantity: 0, duration: 0ms)
        Copy (run quantity: 193, duration: 126ms)
        MarkSweepCompact (run quantity: 0, duration: 0ms)
        Copy (run quantity: 194, duration: 71ms)
        MarkSweepCompact (run quantity: 0, duration: 0ms)
        Copy (run quantity: 193, duration: 79ms)
        MarkSweepCompact (run quantity: 0, duration: 0ms)
        Copy (run quantity: 194, duration: 78ms)
        MarkSweepCompact (run quantity: 0, duration: 0ms)
        ...

Вывод:
    По продолжительности сборки все 3 gc показали примерно одинаковое время, немного впереди остальных оказался
    SerialGC. G1 запускался примерно в два раза меньше чем сотальные. Основываясь на последнем факте, я могу сделать
    вывод, что G1 оказался лучшим.
 */

public class GcDemo {
    public static void main(String[] args) throws InterruptedException {
        int benchMarkCapacity = Integer.parseInt(args[0]);
        int benchMarkQuantity = Integer.parseInt(args[1]);

        var gcRunCounter = logGcEvents();
        var benchmark = new Benchmark(benchMarkCapacity);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                gcRunCounter.forEach((gcName, runQuantity) -> {
                    System.out.println(gcName + " (" + runQuantity + ")");
                    gcRunCounter.replace(gcName, new GcStat());
                });
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 60 * 1000);

        benchmark.fill(benchMarkQuantity);
    }

    private static HashMap<String, GcStat> logGcEvents() {
        HashMap<String, GcStat> runCounter = new HashMap<>();

        for (GarbageCollectorMXBean gcMxBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            runCounter.put(gcMxBean.getName(), new GcStat());

            NotificationListener listener = (notification, handback) -> {
                var info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                GcStat gcStat = runCounter.get(info.getGcName());
                gcStat.increaseRunQuantity(1);
                gcStat.increaseDuration(info.getGcInfo().getDuration());
            };

            NotificationFilter filter = (notification) ->
                    notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION);

            NotificationEmitter emitter = (NotificationEmitter) gcMxBean;
            emitter.addNotificationListener(listener, filter, null);
        }

        return runCounter;
    }
}
