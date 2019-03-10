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
	private Map<Class, BiFunction<String, ResultSet, Object>> getterMap = Map.of(
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

	boolean save(T object) {
		return Stream.of(object.getClass().getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(Id.class))
				.findFirst()
				.map((idField) -> {
					try {
						idField.setAccessible(true);
						if (idField.getLong(object) == 0) {
							insert(object);
						} else {
							update(object);
						}
						idField.setAccessible(false);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					return true;
				})
				.orElse(false);

	}

	private void insert(T object) {
		Class<?> clazz = object.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		String sql = SqlBuilder.buildInsert(clazz);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql, 1)) {
			int columnIndex = 1;
			for (Field field : declaredFields) {
				if (!field.isAnnotationPresent(Id.class)) {
					bindParameter(preparedStatement, field, columnIndex, object);
					columnIndex++;
				}
			}

			System.out.println(sql);
			preparedStatement.executeUpdate();

			Stream.of(declaredFields)
					.filter(field -> field.isAnnotationPresent(Id.class))
					.findFirst()
					.map(field -> {
						long generatedId = 0;
						try {
							ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
							if (generatedKeys.next()) {
								field.setAccessible(true);
								generatedId = generatedKeys.getLong(1);
								field.setLong(object, generatedId);
								field.setAccessible(false);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return generatedId;
					});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void update(T object) {
		Class<?> clazz = object.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		String sql = SqlBuilder.buildUpdate(clazz);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			int columnIndex = 1;
			for (Field field : declaredFields) {
				if (field.isAnnotationPresent(Id.class)) {
					bindParameter(preparedStatement, field, declaredFields.length, object);
				} else {
					bindParameter(preparedStatement, field, columnIndex, object);
					columnIndex++;
				}
			}
			System.out.println(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void bindParameter(PreparedStatement preparedStatement, Field field, int parameterIndex, Object object) {
		try {
			field.setAccessible(true);
			if (field.getType() == Integer.TYPE) {
				preparedStatement.setInt(parameterIndex, field.getInt(object));
			}
			if (field.getType() == Long.TYPE) {
				preparedStatement.setLong(parameterIndex, field.getLong(object));
			}
			if (field.getType() == String.class) {
				preparedStatement.setString(parameterIndex, (String) field.get(object));
			}
//			preparedStatement.setObject(parameterIndex, object);
			field.setAccessible(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	<T> T load(long id, Class<T> clazz) {
		String tableName = clazz.getSimpleName();
		Field[] declaredFields = clazz.getDeclaredFields();

		Function<Field, T> findAndMakeObject = idAnnotatedField -> {
			String sql = "select * from " + tableName + " where " + idAnnotatedField.getName() + "=?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
				System.out.println(sql);
				if (resultSet.next()) {
					T instance = clazz.getDeclaredConstructor().newInstance();
					Stream.of(declaredFields).forEach(field -> {
						field.setAccessible(true);
						try {
							field.set(instance, getterMap.get(field.getType()).apply(field.getName(), resultSet));
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
				.map(findAndMakeObject)
				.orElse(null);
	}
}
