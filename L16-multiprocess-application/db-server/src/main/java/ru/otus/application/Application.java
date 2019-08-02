package ru.otus.application;

import ru.otus.application.service.DBService;
import ru.otus.application.service.SocketListener;
import ru.otus.domain.service.MessageWorker;
import ru.otus.message.Message;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
	private static final int THREAD_SLEEP_TIME_MS = 100;

	private final String id;
	private final int port;
	private final List<MessageWorker> messageWorkers = new CopyOnWriteArrayList<>();
	private final SocketListener socketListener = new SocketListener();
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	public Application(String id, int port) {
		this.id = id;
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public int getPort() {
		return port;
	}

	public void start() {
		socketListener.start(this);
		executorService.execute(this::processMessages);
	}

	public void stop() {
		socketListener.stop();
		executorService.shutdown();
	}

	public void addMessageWorker(MessageWorker messageWorker) {
		messageWorkers.add(messageWorker);
	}

	private void processMessages() {
		final DBService dbService = new DBService(this);
		while (!executorService.isTerminated()) {
			for (MessageWorker worker : messageWorkers) {
				Message message = worker.pollMessage();
				if (message != null) {
					dbService.processMessage(message, worker);
				}
				try {
					Thread.sleep(THREAD_SLEEP_TIME_MS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
