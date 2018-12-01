package ru.otus;

import java.util.*;

public class MyArrayList<T> implements List<T> {

    private int size;

    private Object[] store;

    MyArrayList() {
        this(0);
    }

    MyArrayList(int size) {
        this.size = size;
        store = new Object[size];
    }

    private void extendStore(int newSize) {
        store = Arrays.copyOf(store, newSize);
    }

    public boolean add(T t) {
        extendStore(size + 1);
        store[size++] = t;
        return true;
    }

    public T get(int index) {
        return (T) store[index];
    }

    public T set(int index, T element) {
        if (index >= size) {
            size = index + 1;
            extendStore(size);

        }
        store[index] = element;
        return element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        return store;
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public ListIterator<T> listIterator() {
        return new MyIterator<T>(store);
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return Arrays.toString(store) + ", size: " + size;
    }
}
