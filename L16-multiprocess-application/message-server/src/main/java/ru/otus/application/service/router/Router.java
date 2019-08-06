package ru.otus.application.service.router;

import ru.otus.message.Message;

abstract class Router {
	private Router next;

	void setNext(Router next) {
		this.next = next;
	}

	String getAddressee(Message message) {
		return next != null ? next.getAddressee(message) : null;
	}
}
