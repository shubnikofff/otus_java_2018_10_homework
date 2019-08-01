package ru.otus.application;

import org.springframework.stereotype.Service;
import ru.otus.application.service.Router;
import ru.otus.application.service.SocketMessageWorker;
import ru.otus.domain.service.MessageWorker;
import ru.otus.domain.service.ProcessRunner;
import ru.otus.message.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Application {
	private static final int THREAD_SLEEP_TIME_MS = 100;
	private static final String HOST = "localhost";

	private static final String FIRST_FRONTEND_SERVER_ID = "FE#1";
	private static final String SECOND_FRONTEND_SERVER_ID = "FE#2";
	private static final String FIRST_DB_SERVER_ID = "DB#1";
	private static final String SECOND_DB_SERVER_ID = "DB#2";

	private static final int FIRST_FRONTEND_SERVER_PORT = 5050;
	private static final int SECOND_FRONTEND_SERVER_PORT = 5051;
	private static final int FIRST_DB_SERVER_PORT = 5052;
	private static final int SECOND_DB_SERVER_PORT = 5053;

	private static final int CLIENT_NUMBER = 4;
	private static final int DELAY_BEFORE_CONNECT_TO_CLIENT_MS = 2000;

	private final ProcessRunner processRunner;
	private final Router router;
	private final Logger logger = Logger.getLogger(Application.class.getName());
	private final Map<String, MessageWorker> workerMap = new ConcurrentHashMap<>(CLIENT_NUMBER);
	private final ExecutorService executorService = Executors.newFixedThreadPool(CLIENT_NUMBER);

//	ThreadFactory threadFactory = new ThreadFactory() {
//		Thread thread;
//
//		@Override
//		public Thread newThread(Runnable r) {
//			thread.setUncaughtExceptionHandler();
//			return null;
//		}
//	};

//	private final ExecutorService executorService = Executors.newFixedThreadPool(CLIENT_NUMBER, threadFactory);


	public Application(ProcessRunner processRunner, Router router) {
		this.processRunner = processRunner;
		this.router = router;
	}

	public void start() {
		runClient("java -jar ../db-server/target/db-server.jar " + FIRST_DB_SERVER_ID + " " + FIRST_DB_SERVER_PORT, FIRST_DB_SERVER_ID, FIRST_DB_SERVER_PORT);
		runClient("java -jar ../db-server/target/db-server.jar " + SECOND_DB_SERVER_ID + " " + SECOND_DB_SERVER_PORT, SECOND_DB_SERVER_ID, SECOND_DB_SERVER_PORT);
		runClient("java -jar ../frontend/target/frontend.jar " + FIRST_FRONTEND_SERVER_ID + " " + FIRST_FRONTEND_SERVER_PORT, FIRST_FRONTEND_SERVER_ID, FIRST_FRONTEND_SERVER_PORT);
		runClient("java -jar ../frontend/target/frontend.jar " + SECOND_FRONTEND_SERVER_ID + " " + SECOND_FRONTEND_SERVER_PORT, SECOND_FRONTEND_SERVER_ID, SECOND_FRONTEND_SERVER_PORT);
		router.start(workerMap);
		while (!executorService.isTerminated()) {
			workerMap.forEach((id, messageWorker) -> {
				final Message message = messageWorker.pollMessage();
				if (message != null) {
					router.addMessage(message);
				}
			});
			try {
				Thread.sleep(THREAD_SLEEP_TIME_MS);
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		}
	}

	private void runClient(String command, String id, int port) {
		final Supplier<Process> processSupplier = () -> {
			Process process = null;
			try {
				process = processRunner.start(command);
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
			return process;
		};

		final Consumer<Process> processConsumer = (process) -> {
			try {
				Thread.sleep(DELAY_BEFORE_CONNECT_TO_CLIENT_MS);
				if (!process.isAlive()) {
					throw new Exception(id + " is dead");
				}
				MessageWorker messageWorker = new SocketMessageWorker(new Socket(HOST, port));
				workerMap.put(id, messageWorker);
				messageWorker.start();
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		};

		CompletableFuture.supplyAsync(processSupplier, executorService).thenAccept(processConsumer);
	}

	public void stop() {
		executorService.shutdown();
		router.stop();
	}
}
