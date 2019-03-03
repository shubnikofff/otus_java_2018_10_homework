package ru.otus;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

class Executor<T> {
	private final Connection connection;
	private Map<Class, BiFunction<String, ResultSet, Object>> valueGetterMap = Map.of(
			Integer.TYPE, (columnName, resultSet) -> {
				try {
					return resultSet.getInt(columnName);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			},
			String.class, (columnName, resultSet) -> {
				try {
					return resultSet.getString(columnName);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			},
			Long.TYPE, (columnName, resultSet) -> {
				try {
					return resultSet.getLong(columnName);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
	);

	Executor(Connection connection) {
		this.connection = connection;
	}

	void save(T object) {
		Class<?> clazz = object.getClass();
		String tableName = clazz.getSimpleName();
		Field[] declaredFields = clazz.getDeclaredFields();

		Stream.of(declaredFields)
				.filter(field -> field.isAnnotationPresent(Id.class))
				.findFirst()
				.map((idField) -> {
					try {
						idField.setAccessible(true);
						String sql = "select count(*) from " + tableName + " where " + idField.getName() + " = ?";
						try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
							preparedStatement.setLong(1, idField.getLong(object));
							ResultSet resultSet = preparedStatement.executeQuery();
							resultSet.next();
							if (resultSet.getInt(1) == 0) {
								insert(object);
							} else {
								update(object);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return false;
				});

	}

	private void insert(T object) {

	}

	private void update(T object) {

	}

	<T> T load(long id, Class<T> clazz) {
		String tableName = clazz.getSimpleName();
		Field[] declaredFields = clazz.getDeclaredFields();

		Function<Field, T> findAnMakeObject = idAnnotatedField -> {
			String sql = "select * from " + tableName + " where " + idAnnotatedField.getName() + " = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					T instance = clazz.getDeclaredConstructor().newInstance();
					Stream.of(declaredFields).forEach(field -> {
						field.setAccessible(true);
						try {
							field.set(instance, valueGetterMap.get(field.getType()).apply(field.getName(), resultSet));
						} catch (IllegalAccessException e) {
							throw new RuntimeException(e);
						}
						field.setAccessible(false);
					});
					return instance;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			return null;
		};

		return Stream.of(declaredFields)
				.filter(field -> field.isAnnotationPresent(Id.class))
				.findFirst()
				.map(findAnMakeObject)
				.orElse(null);
	}
}
