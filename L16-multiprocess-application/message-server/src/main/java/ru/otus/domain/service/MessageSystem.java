package ru.otus.domain.service;

public interface MessageSystem extends Runnable {
	void addWorker(MessageWorker messageWorker);
}
