package ru.otus.application;

import org.springframework.stereotype.Service;
import ru.otus.application.service.router.RoutingService;
import ru.otus.application.service.SocketMessageWorker;
import ru.otus.domain.service.MessageWorker;
import ru.otus.domain.service.ProcessRunner;
import ru.otus.message.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Application {
	private static final int THREAD_SLEEP_TIME_MS = 100;
	private static final int CLIENT_NUMBER = 4;
	private static final int DELAY_BEFORE_CONNECT_TO_CLIENT_MS = 2000;

	private static final String FIRST_FRONTEND_SERVER_ID = "FE#1";
	private static final String SECOND_FRONTEND_SERVER_ID = "FE#2";
	private static final String FIRST_DB_SERVER_ID = "DB#1";
	private static final String SECOND_DB_SERVER_ID = "DB#2";

	private static final String HOST = "localhost";
	private static final int FIRST_FRONTEND_SERVER_PORT = 5051;
	private static final int SECOND_FRONTEND_SERVER_PORT = 5052;
	private static final int FIRST_DB_SERVER_PORT = 5053;
	private static final int SECOND_DB_SERVER_PORT = 5054;

	private static final String FIRST_FRONTEND_SERVER_COMMAND = "java -jar ../frontend/target/frontend.jar " + FIRST_FRONTEND_SERVER_ID + " " + FIRST_FRONTEND_SERVER_PORT;
	private static final String SECOND_FRONTEND_SERVER_COMMAND = "java -jar ../frontend/target/frontend.jar " + SECOND_FRONTEND_SERVER_ID + " " + SECOND_FRONTEND_SERVER_PORT;
	private static final String FIRST_DB_SERVER_COMMAND = "java -jar ../db-server/target/db-server.jar " + FIRST_DB_SERVER_ID + " " + FIRST_DB_SERVER_PORT;
	private static final String SECOND_DB_SERVER_COMMAND = "java -jar ../db-server/target/db-server.jar " + SECOND_DB_SERVER_ID + " " + SECOND_DB_SERVER_PORT;

	private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
	//	private final ProcessRunner processRunner;
	private final RoutingService routingService;
//	private final Map<String, MessageWorker> workerMap = new ConcurrentHashMap<>(CLIENT_NUMBER);
//	private final ExecutorService executorService = Executors.newFixedThreadPool(CLIENT_NUMBER);
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	private final Map<String, Client> clientMap = new ConcurrentHashMap<>(CLIENT_NUMBER);

//	ThreadFactory threadFactory = new ThreadFactory() {
//		Thread thread;
//
//		@Override
//		public Thread newThread(Runnable r) {
//			thread.setUncaughtExceptionHandler();
//			return null;
//		}
//	};
//
//	ThreadFactory tf1 = (r) ->
//		new Thread(r).setUncaughtExceptionHandler((t, e) -> {
//				logger.log(Level.SEVERE, e.getMessage());
//			final Thread t1 = t;
//			return t1;
//			}
//		);


//	private final ExecutorService executorService = Executors.newFixedThreadPool(CLIENT_NUMBER, threadFactory);


//	public Application(ProcessRunner processRunner, RoutingService routingService) {
//		this.processRunner = processRunner;
//		this.routingService = routingService;
//	}


	public Application(RoutingService routingService) {
		this.routingService = routingService;
	}

	public void start() {
		startClient(FIRST_DB_SERVER_ID, HOST, FIRST_DB_SERVER_PORT, FIRST_DB_SERVER_COMMAND);
		startClient(SECOND_DB_SERVER_ID, HOST, SECOND_DB_SERVER_PORT, SECOND_DB_SERVER_COMMAND);
		startClient(FIRST_FRONTEND_SERVER_ID, HOST, FIRST_FRONTEND_SERVER_PORT, FIRST_FRONTEND_SERVER_COMMAND);
		startClient(SECOND_FRONTEND_SERVER_ID, HOST, SECOND_FRONTEND_SERVER_PORT, SECOND_FRONTEND_SERVER_COMMAND);
		routingService.start(this);
		executorService.schedule(this::putMessagesToRoutingService, 0, TimeUnit.SECONDS);
	}

	private void startClient(String id, String host, int port, String command) {
		final Client client = new Client(host, port, command);
		client.start();
		clientMap.put(id, client);
	}

	private void putMessagesToRoutingService() {
		while (!executorService.isTerminated()) {
			clientMap.forEach((id, client) -> {
				Message message = client.pullMessage();
				if (message != null) {
					routingService.addMessage(message);
				}
			});
			try {
				Thread.sleep(THREAD_SLEEP_TIME_MS);
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
		}
	}

	public void sendMessage(String addressee, Message message) {
		clientMap.get(addressee).pushMessage(message);
	}

	public List<String> getDbServerIdList() {
		return List.of(FIRST_DB_SERVER_ID, SECOND_DB_SERVER_ID);
	}


//	public void start() {
//		runClient("java -jar ../db-server/target/db-server.jar " + FIRST_DB_SERVER_ID + " " + FIRST_DB_SERVER_PORT, FIRST_DB_SERVER_ID, FIRST_DB_SERVER_PORT);
//		runClient("java -jar ../db-server/target/db-server.jar " + SECOND_DB_SERVER_ID + " " + SECOND_DB_SERVER_PORT, SECOND_DB_SERVER_ID, SECOND_DB_SERVER_PORT);
//		runClient("java -jar ../frontend/target/frontend.jar " + FIRST_FRONTEND_SERVER_ID + " " + FIRST_FRONTEND_SERVER_PORT, FIRST_FRONTEND_SERVER_ID, FIRST_FRONTEND_SERVER_PORT);
//		runClient("java -jar ../frontend/target/frontend.jar " + SECOND_FRONTEND_SERVER_ID + " " + SECOND_FRONTEND_SERVER_PORT, SECOND_FRONTEND_SERVER_ID, SECOND_FRONTEND_SERVER_PORT);
//		routingService.start(workerMap);
//		while (!executorService.isTerminated()) {
//			workerMap.forEach((id, messageWorker) -> {
//				final Message message = messageWorker.pollMessage();
//				if (message != null) {
//					routingService.addMessage(message);
//				}
//			});
//			try {
//				Thread.sleep(THREAD_SLEEP_TIME_MS);
//			} catch (InterruptedException e) {
//				logger.log(Level.SEVERE, e.getMessage());
//			}
//		}
//	}

//	private void runClient(String command, String id, int port) {
//		final Consumer<Process> processConsumer = (process) -> {
//			try {
//				Thread.sleep(DELAY_BEFORE_CONNECT_TO_CLIENT_MS);
//				if (!process.isAlive()) {
//					throw new Exception(id + " is dead");
//				}
//				MessageWorker messageWorker = new SocketMessageWorker(new Socket(HOST, port));
//				workerMap.put(id, messageWorker);
//				messageWorker.start();
//			} catch (Exception e) {
//				logger.log(Level.SEVERE, e.getMessage());
//			}
//		};
//
//		runProcess(command).thenAccept(processConsumer);
//	}
//
//	private CompletableFuture<Process> runProcess(String command) {
//		return CompletableFuture.supplyAsync(() -> {
//			Process process = null;
//			try {
//				process = processRunner.start(command);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return process;
//		}, executorService);
//	}
//
//	public void stop() {
//		executorService.shutdown();
//		routingService.stop();
//	}
}
