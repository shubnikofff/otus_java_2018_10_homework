package ru.otus;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

class Serializer {
	JSONAware toJson(Object src) throws IllegalAccessException {
		if (src.getClass().isArray() || src instanceof Collection) {
			JSONArray jsonArray = new JSONArray();
			parseObject(src, jsonArray);
			return jsonArray;
		}

		throw new IllegalArgumentException();
	}

	private void parseObject(Object object, JSONObject root) throws IllegalAccessException {
		Field[] declaredFields = object.getClass().getDeclaredFields();

		for (Field field : declaredFields) {
			field.setAccessible(true);
			Class<?> fieldType = field.getType();
			String fieldName = field.getName();
			Object fieldValue = field.get(object);

			if (fieldType.isPrimitive() || fieldType == String.class) {
				root.put(fieldName, fieldValue);
				continue;
			}

			if (fieldType.isArray() || fieldValue instanceof Collection) {
				JSONArray jsonArray = new JSONArray();
				root.put(fieldName, jsonArray);
				parseObject(fieldValue, jsonArray);
				continue;
			}

			JSONObject jsonObject = new JSONObject();
			root.put(fieldName, jsonObject);
			parseObject(fieldValue, jsonObject);
			field.setAccessible(false);
		}
	}

	private void parseObject(Object object, JSONArray root) throws IllegalAccessException {
		if (object instanceof Collection) {
			parseObject(((Collection) object).toArray(), root);
			return;
		}

		Class<?> componentType = object.getClass().getComponentType();
		for (int i = 0; i < Array.getLength(object); i++) {
			Object arrayItem = Array.get(object, i);

			if (componentType.isPrimitive() || componentType == String.class) {
				root.add(arrayItem);
				continue;
			}

			if (componentType.isArray()) {
				JSONArray jsonArray = new JSONArray();
				root.add(jsonArray);
				parseObject(arrayItem, jsonArray);
				continue;
			}

			JSONObject jsonObject = new JSONObject();
			root.add(jsonObject);
			parseObject(arrayItem, jsonObject);
		}
	}
}
