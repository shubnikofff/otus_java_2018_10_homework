package ru.otus.application.service.query;

import ru.otus.message.Message;

public abstract class Query {
	private Query next;

	public void setNext(Query next) {
		this.next = next;
	}

	public Message makeQuery(Message message) {
		return next != null ? next.makeQuery(message) : null;
	}
}
