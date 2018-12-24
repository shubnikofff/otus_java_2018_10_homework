package ru.otus;

/**
 * VM options: -Xmx256m -Xms256m
 */
public class MyCacheDemo {
    private static final int CACHE_SIZE = 12;

    public static void main(String[] args) {
        new MyCacheDemo().demo();
    }

    private void demo() {
        Cache<Integer, User> cache = new MyCache<>();
        MyListener<Integer, User> listener = new MyListener<>();
        cache.addListener(listener);

        for (int i = 0; i < CACHE_SIZE; i++) {
            var user = new User(i, "User#" + i);
            cache.put(i, user);
        }

//        System.gc();

        for (int i = 0; i < CACHE_SIZE; i++) {
            cache.get(i);
        }

        for (int i = 0; i < CACHE_SIZE; i++) {
            cache.remove(i);
        }

//      Try to get or remove not existing values
        cache.remove(500);
        cache.get(500);

        cache.removeListener(listener);
    }
}
