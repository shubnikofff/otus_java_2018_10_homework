package ru.otus.application.service.router;

import ru.otus.message.Message;

public abstract class Router {
	private Router next;

	public void setNext(Router next) {
		this.next = next;
	}

	public String getAddressee(Message message) {
		return next != null ? next.getAddressee(message) : null;
	}
}
