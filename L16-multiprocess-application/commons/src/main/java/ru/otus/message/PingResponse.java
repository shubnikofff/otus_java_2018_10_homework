package ru.otus.message;

public class PingResponse extends Message {
	private final String text;

	public PingResponse(int id, String from, String to, String text) {
		super(id, from, to, PingResponse.class);
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
