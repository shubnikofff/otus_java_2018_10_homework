package ru.otus;

import java.lang.reflect.Field;
import java.util.StringJoiner;
import java.util.stream.Stream;

class SqlBuilder {
	private Field[] declaredFields;
	private String tableName;
	private static final Class ID_ANNOTATION = Id.class;

	public SqlBuilder(Class clazz) {
		declaredFields = clazz.getDeclaredFields();
		tableName = clazz.getSimpleName();
	}

	String buildSelect() {
		return Stream.of(declaredFields)
				.filter(field -> field.isAnnotationPresent(ID_ANNOTATION))
				.findFirst()
				.map(field -> "select * from " + tableName + " where " + field.getName() + "=?")
				.orElseThrow();
	}

	String buildInsert() {
		StringJoiner columns = new StringJoiner(",");
		StringJoiner marks = new StringJoiner(",");
		Stream.of(declaredFields)
				.forEach(field -> {
					if (!field.isAnnotationPresent(ID_ANNOTATION)) {
						columns.add(field.getName());
						marks.add("?");
					}
				});

		return "insert into " + tableName + "(" + columns + ") values (" + marks + ")";
	}

	String buildUpdate() {
		StringJoiner setExpression = new StringJoiner(",");
		String whereExpression = " where ";

		for (Field field : declaredFields) {
			try {
				field.setAccessible(true);
				if (field.isAnnotationPresent(ID_ANNOTATION)) {
					whereExpression += field.getName() + "=?";
				} else {
					setExpression.add(field.getName() + "=?");
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return "update " + tableName + " set " + setExpression + whereExpression;
	}
}
