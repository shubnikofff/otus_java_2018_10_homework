package ru.otus;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.StringJoiner;
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
							connection.commit();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return false;
				});

	}

	private void insert(T object) {
		Class<?> clazz = object.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		StringJoiner columns = new StringJoiner(",");
		StringJoiner marks = new StringJoiner(",");

		Stream.of(declaredFields)
				.forEach(field -> {
					if (!field.isAnnotationPresent(Id.class)) {
						columns.add(field.getName());
						marks.add("?");
					}
				});
		String sql = "insert into "
				+ clazz.getSimpleName()
				+ "("+ columns.toString()
				+") values ("
				+ marks.toString() + ")";

		System.out.println(sql);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			Stream.of(declaredFields).forEach(field -> bindParameter(preparedStatement, object));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void update(T object) {

	}

	private void bindParameter(PreparedStatement preparedStatement, Object object) {
		int columnIndex = 1;
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					continue;
				}
				field.setAccessible(true);
				if (field.getType() == Integer.TYPE) {
					preparedStatement.setInt(columnIndex, field.getInt(object));
				}
				if (field.getType() == Long.TYPE) {
					preparedStatement.setLong(columnIndex, field.getLong(object));
				}
				if (field.getType() == String.class) {
					preparedStatement.setString(columnIndex, (String) field.get(object));
				}
				columnIndex++;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	<T> T load(long id, Class<T> clazz) {
		String tableName = clazz.getSimpleName();
		Field[] declaredFields = clazz.getDeclaredFields();

		Function<Field, T> findAndMakeObject = idAnnotatedField -> {
			String sql = "select * from " + tableName + " where " + idAnnotatedField.getName() + " = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
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
