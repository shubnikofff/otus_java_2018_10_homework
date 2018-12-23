package ru.otus;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

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
        var value = store.remove(key);
        fireListeners(key, value.get(), ACTION_REMOVE);
    }

    public V get(K key) {
        var value = store.get(key).get();
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
        listeners.forEach(listener -> listener.notify(key, value, action));
    }
}
