package ru.otus.application.service;

import ru.otus.application.Application;
import ru.otus.service.MessageWorker;
import ru.otus.message.Message;
import ru.otus.message.UserListResponse;

public class DBService {
	private Application application;

	public DBService(Application application) {
		this.application = application;
	}

	public void processMessage(Message message, MessageWorker messageWorker) {
		System.out.println("Process message from " + message.getFrom());
		messageWorker.sendMessage(new UserListResponse(application.getId(), message.getFrom()));
	}
}
