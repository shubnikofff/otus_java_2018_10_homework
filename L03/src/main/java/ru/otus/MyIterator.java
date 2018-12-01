package ru.otus;

import java.util.ListIterator;

class MyIterator<T> implements ListIterator<T> {
    private int position;

    private int lastIndex = -1;

    private Object[] array;

    public MyIterator(Object[] array) {
        this.array = array;
    }

    public boolean hasNext() {
        return position < array.length;
    }

    public T next() {
        if (hasNext()) {
            return (T) array[lastIndex = position++];
        }
        return null;
    }

    public boolean hasPrevious() {
        return position > 0;
    }

    public T previous() {
        if (hasPrevious()) {
            return (T) array[lastIndex = position--];
        }
        return null;
    }

    public int nextIndex() {
        if (hasNext()) {
            return position;
        }
        return array.length;
    }

    public int previousIndex() {
        if (position == 0) {
            return -1;
        }
        return position - 1;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void set(T t) {
        if (lastIndex != -1) {
            array[lastIndex] = t;
        }

    }

    public void add(T t) {
        throw new UnsupportedOperationException();
    }
}
