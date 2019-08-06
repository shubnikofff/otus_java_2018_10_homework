package ru.otus.application;

import ru.otus.service.LoggingThreadFactory;
import ru.otus.application.service.ProcessRunner;
import ru.otus.service.SocketMessageWorker;
import ru.otus.service.MessageWorker;
import ru.otus.message.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

class Client {
	private final static Logger LOGGER = Logger.getLogger(Client.class.getName());
	private final static int THREAD_SLEEP_TIME_MS = 2000;

	private final String id;
	private final String host;
	private final int port;
	private final String command;
	private final ExecutorService executorService;
	private final ProcessRunner processRunner = new ProcessRunner();
	private MessageWorker messageWorker;

	Client(String id, String host, int port, String command) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.command = command;
		executorService = Executors.newSingleThreadExecutor(new LoggingThreadFactory(this.id));
	}

	void start() {
		CompletableFuture.supplyAsync(this::startProcess, executorService).thenAccept(this::startMessageWorker);
	}

	void stop() {
		executorService.shutdown();
		messageWorker.stop();
	}

	private Process startProcess() {
		Process process = null;
		try {
			process = processRunner.start(command);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return process;
	}

	private void startMessageWorker(Process process) {
		try {
			Thread.sleep(THREAD_SLEEP_TIME_MS);
			if (!process.isAlive()) {
				throw new Exception("Process " + process.pid() + " is dead");
			}
			messageWorker = new SocketMessageWorker(new Socket(host, port));
			messageWorker.start();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	Message pullMessage() {
		if(messageWorker == null) {
			return null;
		}
		return messageWorker.pollMessage();
	}

	void pushMessage(Message message) {
		messageWorker.sendMessage(message);
	}
}
