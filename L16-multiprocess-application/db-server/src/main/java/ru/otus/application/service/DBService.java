package ru.otus.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.message.Message;
import ru.otus.message.UserListResponse;
import ru.otus.service.AbstractMessageProcessor;

@Service
public class DBService extends AbstractMessageProcessor {
	@Value("${id}")
	private String id;

	@Override
	protected Message processMessage(Message message) {
		return new UserListResponse(id, message.getFrom());
	}
}
