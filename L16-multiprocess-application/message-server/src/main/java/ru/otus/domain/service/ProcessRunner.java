package ru.otus.domain.service;

public interface ProcessRunner {
	void start(String command);

	void stop();

	String getOutput();
}
