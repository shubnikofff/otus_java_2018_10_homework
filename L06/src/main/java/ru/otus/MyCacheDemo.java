package ru.otus;

import java.util.Optional;

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
        Listener<Integer, User> firstListener =
                ((key, value, action) -> {
                    Optional<User> nullableValue = Optional.ofNullable(value);
                    System.out.println(nullableValue
                            .map(user -> action + ": id: " + user.getId() + " " + user.getName())
                            .orElse(action + ": User#" + key + " is dead :("));
                });

        cache.addListener(firstListener);


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

        cache.removeListener(firstListener);
    }
}
