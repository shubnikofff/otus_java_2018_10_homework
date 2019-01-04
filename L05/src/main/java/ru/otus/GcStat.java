package ru.otus;

public class GcStat {
    private int runQuantity = 0;
    private long duration = 0;

    void increaseRunQuantity(int delta) {
        runQuantity += delta;
    }

    void increaseDuration(long delta) {
        duration += delta;
    }

    @Override
    public String toString() {
        return "run quantity: " + runQuantity + ", duration: " + duration + "ms";
    }
}
