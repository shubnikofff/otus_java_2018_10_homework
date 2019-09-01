package ru.otus.message;

public class ServiceUnavailable extends Message {
	public ServiceUnavailable(int id, String from, String to) {
		super(id, from, to, ServiceUnavailable.class);
	}
}
