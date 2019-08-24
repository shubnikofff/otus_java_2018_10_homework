package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.application.service.router.DbRouter;
import ru.otus.application.service.router.DefaultRouter;
import ru.otus.application.service.router.Router;
import ru.otus.message.Message;
import ru.otus.service.AbstractMessageProcessor;

@Service
public class RoutingService extends AbstractMessageProcessor {
	private final Router router;

	public RoutingService(DefaultRouter defaultRouter, DbRouter dbRouter) {
		router = defaultRouter;
		router.setNext(dbRouter);
	}

	@Override
	protected Message processMessage(Message message) {
		message.setTo(router.getAddressee(message));
		return message;
	}
}
