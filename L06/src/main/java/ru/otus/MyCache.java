package ru.otus;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class MyCache<K, V> implements Cache<K, V> {
    private static final String ACTION_PUT = "PUT";
    private static final String ACTION_REMOVE = "REMOVE";
    private static final String ACTION_GET = "GET";

    private ArrayList<Listener<K, V>> listeners;
    private HashMap<K, SoftReference<V>> store;

    MyCache() {
        this.listeners = new ArrayList<>(3);
        this.store = new HashMap<>();
    }

    public void put(K key, V value) {
        store.put(key, new SoftReference<>(value));
        fireListeners(key, value, ACTION_PUT);
    }

    public void remove(K key) {
        Optional<SoftReference<V>> softReference = Optional.ofNullable(store.remove(key));
        fireListeners(
                key,
                softReference.map(SoftReference::get).orElse(null),
                ACTION_REMOVE
        );
    }

    public V get(K key) {
        Optional<SoftReference<V>> softReference = Optional.ofNullable(store.get(key));
        V value = softReference.map(SoftReference::get).orElse(null);
        fireListeners(key, value, ACTION_GET);
        return value;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    private void fireListeners(K key, V value, String action) {
        // If some listener throws exception, program will not stop
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }
}
