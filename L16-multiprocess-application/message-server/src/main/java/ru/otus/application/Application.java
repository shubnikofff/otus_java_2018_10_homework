package ru.otus.application;

import org.springframework.stereotype.Service;
import ru.otus.application.service.RoutingService;
import ru.otus.message.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Application {
	private static final int THREAD_SLEEP_TIME_MS = 100;
	private static final int CLIENT_NUMBER = 4;

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
	private final RoutingService routingService;
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
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


	public Application(RoutingService routingService) {
		this.routingService = routingService;
	}

	public void start() {
		startClient(FIRST_DB_SERVER_ID, HOST, FIRST_DB_SERVER_PORT, FIRST_DB_SERVER_COMMAND);
		startClient(SECOND_DB_SERVER_ID, HOST, SECOND_DB_SERVER_PORT, SECOND_DB_SERVER_COMMAND);
		startClient(FIRST_FRONTEND_SERVER_ID, HOST, FIRST_FRONTEND_SERVER_PORT, FIRST_FRONTEND_SERVER_COMMAND);
		startClient(SECOND_FRONTEND_SERVER_ID, HOST, SECOND_FRONTEND_SERVER_PORT, SECOND_FRONTEND_SERVER_COMMAND);

		routingService.start(this);
		executorService.execute(this::putMessagesToRoutingService);
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

	public void stop() {
		clientMap.forEach((id, client) -> client.stop());
		routingService.stop();
		executorService.shutdown();
	}
}
