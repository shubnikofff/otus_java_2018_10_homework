package ru.otus.message;

public class UnsupportedRequest extends Message{
	public UnsupportedRequest(int id, String from, String to) {
		super(id, from, to, UnsupportedRequest.class);
	}
}
