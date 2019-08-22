package ru.otus.service;

import ru.otus.message.Message;

public interface MessageWorker {
	void start();
	void stop();
	void putMessage(Message message) throws InterruptedException;
	Message getMessage() throws InterruptedException;
}
