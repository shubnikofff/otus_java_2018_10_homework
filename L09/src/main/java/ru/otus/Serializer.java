package ru.otus;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

class Serializer {
	JSONAware toJson(Object src) throws IllegalAccessException {
		JSONArray jsonArray = new JSONArray();
		parseObject(src, jsonArray);
		return jsonArray;
	}

	private void parseObject(Object object, JSONObject jsonObject) throws IllegalAccessException {
		Field[] declaredFields = object.getClass().getDeclaredFields();

		for (Field field : declaredFields) {
			field.setAccessible(true);
			Class<?> fieldType = field.getType();
			String fieldName = field.getName();
			Object fieldValue = field.get(object);

			if (fieldType.isPrimitive() || fieldType == String.class) {
				jsonObject.put(fieldName, fieldValue);
				continue;
			}

			if (fieldType.isArray()) {
				JSONArray jsonArray = new JSONArray();
				jsonObject.put(fieldName, jsonArray);
				parseObject(fieldValue, jsonArray);
				continue;
			}

			JSONObject newJsonObject = new JSONObject();
			jsonObject.put(fieldName, newJsonObject);
			parseObject(fieldValue, newJsonObject);
			field.setAccessible(false);
		}
	}

	private void parseObject(Object object, JSONArray jsonArray) throws IllegalAccessException {
		Class<?> componentType = object.getClass().getComponentType();

		for (int i = 0; i < Array.getLength(object); i++) {
			Object arrayItem = Array.get(object, i);

			if (componentType.isPrimitive() || componentType == String.class) {
				jsonArray.add(arrayItem);
				continue;
			}

			if (componentType.isArray()) {
				JSONArray newJsonArray = new JSONArray();
				jsonArray.add(newJsonArray);
				parseObject(arrayItem, newJsonArray);
				continue;
			}

			JSONObject jsonObject = new JSONObject();
			jsonArray.add(jsonObject);
			parseObject(arrayItem, jsonObject);
		}
	}
}
