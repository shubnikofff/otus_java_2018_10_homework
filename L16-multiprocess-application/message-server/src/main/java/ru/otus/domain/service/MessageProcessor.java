package ru.otus.domain.service;

import ru.otus.message.Message;

public interface MessageProcessor {
	void process(Message message, MessageWorker worker);
}
