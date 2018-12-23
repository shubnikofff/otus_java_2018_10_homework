package ru.otus;

public class MyCacheDemo {
    public static void main(String[] args) {
        new MyCacheDemo().demo();
    }

    private void demo() {
        Cache<Integer, Integer> cache = new MyCache<>();
        Listener<Integer, Integer> listener =
                ((key, value, action) -> System.out.println(action + ": key " + key + " value: " + value));
        cache.addListener(listener);
        cache.put(1,1);
        cache.get(1);
        cache.remove(1);
        cache.removeListener(listener);
    }
}
