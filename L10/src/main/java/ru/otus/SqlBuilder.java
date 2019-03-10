package ru.otus;

import java.lang.reflect.Field;
import java.util.StringJoiner;
import java.util.stream.Stream;

class SqlBuilder {
	static String buildInsert(Class clazz) {
		StringJoiner columns = new StringJoiner(",");
		StringJoiner marks = new StringJoiner(",");
		Stream.of(clazz.getDeclaredFields())
				.forEach(field -> {
					if (!field.isAnnotationPresent(Id.class)) {
						columns.add(field.getName());
						marks.add("?");
					}
				});

		return "insert into "
				+ clazz.getSimpleName()
				+ "(" + columns.toString()
				+ ") values ("
				+ marks.toString() + ")";
	}

	static String buildUpdate(Class clazz) {
		StringJoiner setExpression = new StringJoiner(",");
		String whereExpression = " where ";

		for (Field field : clazz.getDeclaredFields()) {
			try {
				field.setAccessible(true);
				if (field.isAnnotationPresent(Id.class)) {
					whereExpression += field.getName() + "=?";
				} else {
					setExpression.add(field.getName() + "=?");
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return "update " + clazz.getSimpleName() + " set " + setExpression + whereExpression;
	}
}
