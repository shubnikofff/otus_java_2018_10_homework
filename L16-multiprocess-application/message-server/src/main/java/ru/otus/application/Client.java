package ru.otus.application;

import ru.otus.application.service.ProcessRunnerImplementation;
import ru.otus.service.SocketMessageWorker;
import ru.otus.service.MessageWorker;
import ru.otus.domain.service.ProcessRunner;
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

	private final String host;
	private final int port;
	private final String command;
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private MessageWorker messageWorker;
	private ProcessRunner processRunner;

	Client(String host, int port, String command) {
		this.host = host;
		this.port = port;
		this.command = command;
		processRunner = new ProcessRunnerImplementation();
	}

	void start() {
		CompletableFuture.supplyAsync(this::startProcess, executorService).thenAccept(this::startMessageWorker);
	}

	void stop() {
		executorService.shutdown();
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
