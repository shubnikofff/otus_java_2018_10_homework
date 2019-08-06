package ru.otus.message;

public abstract class Message {
	public static final String CLASS_NAME_VARIABLE = "className";

	private final String from;
	private final String to;
	private final String className;

	public Message(String from, String to, Class<?> clazz) {
		this.from = from;
		this.to = to;
		this.className = clazz.getName();
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
}
