package ru.otus.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> {
	<T> T load(Class<T> clazz, Serializable id);

	List<T> getAll(Class<T> clazz);

	Serializable save(T object);
}
