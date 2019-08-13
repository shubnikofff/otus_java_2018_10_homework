package ru.otus.service;

import ru.otus.message.Message;

import java.util.concurrent.ExecutionException;

public interface MessageSystemClient {
	void start(int port);

	void stop();

	void sendMessage(Message message) throws ExecutionException, InterruptedException;

	Message getMessage() throws ExecutionException, InterruptedException;
}
