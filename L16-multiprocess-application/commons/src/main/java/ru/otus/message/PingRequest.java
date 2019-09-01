package ru.otus.message;

public class PingRequest extends Message {
	public PingRequest(int id, String from) {
		super(id, from, null, PingRequest.class);
	}
}
