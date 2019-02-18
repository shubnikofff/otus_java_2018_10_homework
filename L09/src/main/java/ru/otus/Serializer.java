package ru.otus;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

class Serializer {
	JsonStructure toJson(Object src) {
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

		JsonArray jsonArray = Json.createArrayBuilder().build();
		if(src instanceof Collection) {
			return parseObject(((Collection) src).toArray(), jsonArray);
		}

		return parseObject(src, jsonArray);
	}

//	private void parseObject(JsonObjectBuilder jsonObjectBuilder, Object object) {
//		Class<?> nodeClass = object.getClass();
//
//	}
//
//	private void parseObject(JsonArrayBuilder jsonArrayBuilder, Object object) {
//		for (int i = 0; i < Array.getLength(object); i++) {
//			jsonArrayBuilder.add(i, (JsonValue) Array.get(object, i));
//		}
//	}

	JsonStructure parseObject(Object object) {
		parseObject(object, null);
	}

	JsonStructure parseObject(Object object, JsonStructure jsonStructure) {
		Class<?> clazz = object.getClass();

//		if (clazz.isArray()) {
//			for (int i = 0; i < Array.getLength(object); i++) {
//
//			}
//		}

		Field[] declaredFields = clazz.getDeclaredFields();
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder(jsonStructure.asJsonObject());

		for (Field field : declaredFields) {

		}

		return objectBuilder.build();
	}
}
