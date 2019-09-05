package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.application.service.query.AuthQuery;
import ru.otus.application.service.query.Query;
import ru.otus.application.service.query.UserListQuery;
import ru.otus.message.Message;
import ru.otus.service.AbstractMessageProcessor;

@Service
public class DBService extends AbstractMessageProcessor {
	private final Query query;

	public DBService(AuthQuery authQuery, UserListQuery userListQuery) {
		query = authQuery;
		query.setNext(userListQuery);
	}

	@Override
	protected Message processMessage(Message message) {
		return query.makeQuery(message);
	}
}
