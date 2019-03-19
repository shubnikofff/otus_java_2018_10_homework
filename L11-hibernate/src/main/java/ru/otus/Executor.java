package ru.otus;

public interface Executor<T> {
	<T> T load(long id, Class<T> clazz);
	void save(T object);
}
