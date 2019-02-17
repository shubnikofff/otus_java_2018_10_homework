package ru.otus;

class Serializer {
	String toJson(Object src) {
		Class<?> clazz = src.getClass();

		if (clazz.isArray()) {
			parseArray(src);
		}
		return src.toString();
	}

	private void parseArray(Object src) {
		System.out.println("That's Array!");
	}
}
