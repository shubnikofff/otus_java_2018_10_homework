package ru.otus;

import java.util.Optional;

public class MyListener<K, V> implements Listener<K, V> {
    @Override
    public void notify(K key, V value, String action) {
        Optional<V> optionalValue = Optional.ofNullable(value);
        String message = action + ": key:" + key;
        message += optionalValue.map(v -> " value:" + v).orElse(" Value does not exist...");
        System.out.println(message);
    }
}
