package ru.otus.executor;

public interface Executor<T> {
	<T> T load(long id, Class<T> clazz);
	void save(T object);
}
