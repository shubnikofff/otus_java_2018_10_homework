package ru.otus.application.service.router;

import ru.otus.application.Application;
import ru.otus.domain.service.MessageWorker;
import ru.otus.message.Message;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class Router {
	private Router next;

	void setNext(Router next) {
		this.next = next;
	}

	String getAddressee(Message message) {
		return next != null ? next.getAddressee(message) : null;
	}

//	void sendMessage(Message message) {
//		if (next != null) {
//			next.sendMessage(message);
//		} else {
//			logger.log(Level.SEVERE, "The message from " + message.getFrom() + " to " + message.getTo() + " cannot be sent");
//		}
//	}
}
