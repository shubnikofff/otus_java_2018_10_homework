package ru.otus.application.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.message.Message;
import ru.otus.message.UserListResponse;
import ru.otus.service.AbstractMessageProcessor;

@Service
public class DBService extends AbstractMessageProcessor {
	private String id;
	private Logger logger;

	public DBService(@Value("${id}") String id, @Qualifier("loggerDBService") Logger logger) {
		this.id = id;
		this.logger = logger;
	}

	@Override
	protected Message processMessage(Message message) {
		logger.info("Received a message from " + message.getFrom());
		return new UserListResponse(id, message.getFrom());
	}
}
