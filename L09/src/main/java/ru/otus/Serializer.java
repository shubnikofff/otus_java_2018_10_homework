package ru.otus;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

class Serializer {
	JSONAware toJson(Object src) throws IllegalAccessException {
//		Class<?> clazz = src.getClass();
//
//		if (clazz.isArray()) {
//			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
//			parseObject(jsonArrayBuilder, src);
//			return jsonArrayBuilder.build();
//		}
//
//		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
//		parseObject(jsonObjectBuilder, src);
//		return jsonObjectBuilder.build();

//		JsonArray jsonArray = Json.createArrayBuilder().build();
//		if (src instanceof Collection) {
//			return parseObject(((Collection) src).toArray(), jsonArray);
//		}
//
//		return parseObject(src, jsonArray);

//		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//		parseObject(src, arrayBuilder);
//		return arrayBuilder.build();

		JSONArray jsonArray = new JSONArray();
		parseObject(src, jsonArray);
		return jsonArray;
	}

	private void parseObject(Object o, JSONObject jsonObject) throws IllegalAccessException {
		Class<?> clazz = o.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();

		for (Field field : declaredFields) {
			field.setAccessible(true);
			Class<?> fieldType = field.getType();
			String fieldName = field.getName();
			Object fieldValue = field.get(o);

			if (fieldType.isArray()) {
//				JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
//				builder.add(fieldName, jsonArrayBuilder);
//				parseObject(fieldValue, jsonArrayBuilder);
				JSONArray jsonArray = new JSONArray();
				jsonObject.put(fieldName, jsonArray);
				parseObject(fieldValue, jsonArray);
			} else if (fieldType.isPrimitive()) {
//				if (fieldType == Byte.TYPE) {
//					builder.add(fieldName, (byte) fieldValue);
//				}
//				if (fieldType == Short.TYPE) {
//					builder.add(fieldName, (short) fieldValue);
//				}
//				if (fieldType == Integer.TYPE) {
//					builder.add(fieldName, (int) fieldValue);
//				}
//				if (fieldType == Long.TYPE) {
//					builder.add(fieldName, (long) fieldValue);
//				}
//				if (fieldType == Float.TYPE) {
//					builder.add(fieldName, (float) fieldValue);
//				}
//				if (fieldType == Double.TYPE) {
//					builder.add(fieldName, (double) fieldValue);
//				}
//				if (fieldType == Boolean.TYPE) {
//					builder.add(fieldName, (boolean) fieldValue);
//				}
//				if (fieldType == Character.TYPE) {
//					builder.add(fieldName, (char) fieldValue);
//				}
				jsonObject.put(fieldName, fieldValue);
			} else if (fieldType.getTypeName().equals("java.lang.String")) {
//				builder.add(fieldName, (String) fieldValue);
				jsonObject.put(fieldName, fieldValue);
			} else {
//				JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//				builder.add(fieldName, objectBuilder);
//				parseObject(fieldValue, objectBuilder);
				JSONObject jsonObject1 = new JSONObject();
				jsonObject.put(fieldName, jsonObject1);
				parseObject(fieldValue, jsonObject1);
			}
//			field.setAccessible(false);
		}
	}

	private void parseObject(Object o, JSONArray jsonArray) throws IllegalAccessException {
		Class<?> clazz = o.getClass();
		Class<?> componentType = clazz.getComponentType();

		for (int i = 0; i < Array.getLength(o); i++) {
			Object arrayItem = Array.get(o, i);

			if (componentType.isArray()) {
//				JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
//				builder.add(jsonArrayBuilder);
//				parseObject(arrayItem, jsonArrayBuilder);
			} else if (componentType.isPrimitive()) {
				jsonArray.add(arrayItem);
//				if (componentType == Byte.TYPE) {
//					builder.add((byte) arrayItem);
//				}
//				if (componentType == Short.TYPE) {
//					builder.add((short) arrayItem);
//				}
//				if (componentType == Integer.TYPE) {
//					builder.add((int) arrayItem);
//				}
//				if (componentType == Long.TYPE) {
//					builder.add((long) arrayItem);
//				}
//				if (componentType == Float.TYPE) {
//					builder.add((float) arrayItem);
//				}
//				if (componentType == Double.TYPE) {
//					builder.add((double) arrayItem);
//				}
//				if (componentType == Boolean.TYPE) {
//					builder.add((boolean) arrayItem);
//				}
//				if (componentType == Character.TYPE) {
//					builder.add((char) arrayItem);
//				}
			} else if (componentType.equals("java.lang.String")) {
				jsonArray.add(arrayItem);
			} else {
				JSONObject jsonObject = new JSONObject();
				jsonArray.add(jsonObject);
				parseObject(arrayItem, jsonObject);
			}
		}
	}
}
