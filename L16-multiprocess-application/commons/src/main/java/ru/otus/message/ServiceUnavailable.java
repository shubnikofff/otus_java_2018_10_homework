package ru.otus.message;

public class ServiceUnavailable extends Message {
	public ServiceUnavailable(String from, String to) {
		super(from, to, ServiceUnavailable.class);
	}
}
