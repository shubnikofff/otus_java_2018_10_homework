package ru.otus.message;

public abstract class Message {
	public static final String CLASS_NAME_VARIABLE = "className";

	private final int id;
	private final String from;
	private String to;
	private final String className;

	public Message(int id, String from, String to, Class<?> clazz) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.className = clazz.getName();
	}

	public int getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
