package ru.otus.service;

import ru.otus.message.Message;

public interface MessageWorker {
	void start();
	void stop();
	void sendMessage(Message message);
	Message pollMessage();
}
