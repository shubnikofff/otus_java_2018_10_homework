package ru.otus;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class Serializer {
	private static final Set<Class<?>> LEAF_TYPES = getLeafTypes();

	private static Set<Class<?>> getLeafTypes() {
		Set<Class<?>> leafTypeSet = new HashSet<>();
		leafTypeSet.add(Boolean.class);
		leafTypeSet.add(Character.class);
		leafTypeSet.add(Byte.class);
		leafTypeSet.add(Short.class);
		leafTypeSet.add(Integer.class);
		leafTypeSet.add(Long.class);
		leafTypeSet.add(Float.class);
		leafTypeSet.add(Double.class);
		leafTypeSet.add(Void.class);
		leafTypeSet.add(String.class);
		return leafTypeSet;
	}

	private boolean isLeaf(Object object) {
		if (object == null) {
			return true;
		}

		Class<?> clazz = object.getClass();
		return clazz.isPrimitive() || LEAF_TYPES.contains(clazz);
	}

	String toJson(Object src) throws IllegalAccessException {
		if (isLeaf(src)) {
			if (src == null) {
				return null;
			}
			return src.toString();
		}

		if (src.getClass().isArray()) {
			JSONArray jsonArray = new JSONArray();
			parseObject(src, jsonArray);
			return jsonArray.toJSONString();
		}

		if (src instanceof Collection) {
			JSONArray jsonArray = new JSONArray();
			parseObject(((Collection) src).toArray(), jsonArray);
			return jsonArray.toJSONString();
		}

		JSONObject jsonObject = new JSONObject();
		parseObject(src, jsonObject);
		return jsonObject.toJSONString();
	}

	private void parseObject(Object object, JSONObject root) throws IllegalAccessException {
		Field[] declaredFields = object.getClass().getDeclaredFields();

		for (Field field : declaredFields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object fieldValue = field.get(object);

			if (isLeaf(fieldValue)) {
				root.put(fieldName, fieldValue);
				continue;
			}

			if (fieldValue.getClass().isArray()) {
				JSONArray jsonArray = new JSONArray();
				parseObject(fieldValue, jsonArray);
				root.put(fieldName, jsonArray);
				continue;
			}

			if (fieldValue instanceof Collection) {
				JSONArray jsonArray = new JSONArray();
				parseObject(((Collection) fieldValue).toArray(), jsonArray);
				root.put(fieldName, jsonArray);
				continue;
			}

			JSONObject jsonObject = new JSONObject();
			root.put(fieldName, jsonObject);
			parseObject(fieldValue, jsonObject);
		}
	}

	private void parseObject(Object object, JSONArray root) throws IllegalAccessException {
		for (int i = 0; i < Array.getLength(object); i++) {
			Object arrayItem = Array.get(object, i);
			if (isLeaf(arrayItem)) {
				root.add(arrayItem);
			} else {
				JSONObject jsonObject = new JSONObject();
				parseObject(arrayItem, jsonObject);
				root.add(jsonObject);
			}
		}
	}
}
