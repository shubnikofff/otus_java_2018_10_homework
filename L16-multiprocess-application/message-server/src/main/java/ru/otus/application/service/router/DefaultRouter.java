package ru.otus.application.service.router;

import ru.otus.domain.service.MessageWorker;
import ru.otus.message.Message;

import java.util.Map;

public class DefaultRouter extends Router {
	DefaultRouter(Map<String, MessageWorker> messageWorkerMap) {
		super(messageWorkerMap);
	}

	@Override
	void sendMessage(Message message) {
		final String addressee = message.getTo();

		if (addressee != null) {
			this.messageWorkerMap.get(addressee).sendMessage(message);
		} else {
			super.sendMessage(message);
		}
	}
}
