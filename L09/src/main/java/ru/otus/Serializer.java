package ru.otus;

import javax.json.*;
import java.lang.reflect.Array;

class Serializer {
	JsonStructure toJson(Object src) {
		Class<?> clazz = src.getClass();

		if (clazz.isArray()) {
			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			parseObject(jsonArrayBuilder, src);
			return jsonArrayBuilder.build();
		}

		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		parseObject(jsonObjectBuilder, src);
		return jsonObjectBuilder.build();
	}

	private void parseObject(JsonObjectBuilder jsonObjectBuilder, Object object) {
		Class<?> nodeClass = object.getClass();

	}

	private void parseObject(JsonArrayBuilder jsonArrayBuilder, Object object) {
		for (int i = 0; i < Array.getLength(object); i++) {
			jsonArrayBuilder.add(i, (JsonValue) Array.get(object, i));
		}
	}
}
