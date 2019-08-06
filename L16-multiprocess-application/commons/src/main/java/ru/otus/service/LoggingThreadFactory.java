package ru.otus.service;

import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingThreadFactory implements ThreadFactory {
	private static final Logger LOGGER = Logger.getLogger(LoggingThreadFactory.class.getName());

	private int counter = 1;
	private final String name;

	public LoggingThreadFactory(String name) {
		this.name = name;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, name + "-" + counter++);
		t.setUncaughtExceptionHandler((t1, e) -> LOGGER.log(Level.SEVERE, e.getMessage()));
		LOGGER.info("Created new thread " + t.getName());
		return t;
	}
}
