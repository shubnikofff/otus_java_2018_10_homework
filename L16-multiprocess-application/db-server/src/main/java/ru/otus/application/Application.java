package ru.otus.application;

import ru.otus.application.service.DBService;
import ru.otus.service.LoggingThreadFactory;
import ru.otus.service.MessageSystemClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Application {
	private static final int THREAD_SLEEP_TIME_MS = 100;

	private String id;
	private DBService dbService;
	private MessageSystemClient messageSystemClient;
	private ScheduledExecutorService executorService;

	public Application(String id, DBService dbService, MessageSystemClient messageSystemClient) {
		this.id = id;
		this.dbService = dbService;
		this.messageSystemClient = messageSystemClient;
		executorService =  Executors.newSingleThreadScheduledExecutor(new LoggingThreadFactory(id));
	}

	public void start(String id, int port) {
		messageSystemClient.start(port);
	}

	private void putMessageInDbService() {

	}










	//	private final String id;
//	private final int port;
//	private final List<MessageWorker> messageWorkers = new CopyOnWriteArrayList<>();
//	private final SocketListener socketListener = new SocketListener();
//	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//	public Application(String id, int port) {
//		this.id = id;
//		this.port = port;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public int getPort() {
//		return port;
//	}
//
//	public void start() {
//		socketListener.start(this);
//		executorService.execute(this::processMessages);
//	}
//
//	public void stop() {
//		socketListener.stop();
//		executorService.shutdown();
//	}
//
//	public void addMessageWorker(MessageWorker messageWorker) {
//		messageWorkers.add(messageWorker);
//	}
//
//	private void processMessages() {
//		final DBService dbService = new DBService(this);
//		while (!executorService.isTerminated()) {
//			for (MessageWorker worker : messageWorkers) {
//				Message message = worker.pollMessage();
//				if (message != null) {
//					dbService.processMessage(message, worker);
//				}
//				try {
//					Thread.sleep(THREAD_SLEEP_TIME_MS);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}
