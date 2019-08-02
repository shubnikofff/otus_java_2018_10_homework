package ru.otus.application.service.router;

import ru.otus.domain.service.MessageWorker;
import ru.otus.message.Message;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class Router {
	private Router next;
	protected final Map<String, MessageWorker> messageWorkerMap;
	private final Logger logger = Logger.getLogger(Router.class.getName());

	Router(Map<String, MessageWorker> messageWorkerMap) {
		this.messageWorkerMap = messageWorkerMap;
	}

	void setNext(Router next) {
		this.next = next;
	}

	void sendMessage(Message message) {
		if (next != null) {
			next.sendMessage(message);
		} else {
			logger.log(Level.SEVERE, "The message from " + message.getFrom() + " to " + message.getTo() + " cannot be sent");
		}
	}
}
