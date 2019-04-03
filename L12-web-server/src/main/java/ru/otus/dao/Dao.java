package ru.otus.dao;

import java.util.List;

public interface Dao<T> {
	<T> T load(long id, Class<T> clazz);
	List<T> getAll(Class<T> clazz);
	void save(T object);
}
