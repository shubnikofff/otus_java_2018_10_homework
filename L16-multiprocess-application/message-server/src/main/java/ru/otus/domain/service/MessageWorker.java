package ru.otus.domain.service;

import ru.otus.message.Message;

public interface MessageWorker {
	void start();
	void sendMessage(Message message);
	Message takeMessage() throws InterruptedException;
	Message pollMessage();
}
