package ru.otus.application;

import org.springframework.stereotype.Service;
import ru.otus.service.LoggingThreadFactory;
import ru.otus.application.service.RoutingService;
import ru.otus.message.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class Application {
	private static final int CLIENT_NUMBER = 4;
	private static final int INITIAL_DELAY_MS = 0;
	private static final int PERIOD_MS = 100;

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

	private final RoutingService routingService;
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new LoggingThreadFactory(Application.class.getSimpleName()));
	private final Map<String, Client> clientMap = new ConcurrentHashMap<>(CLIENT_NUMBER);

	public Application(RoutingService routingService) {
		this.routingService = routingService;
	}

	public void start() {
		startClient(FIRST_DB_SERVER_ID, HOST, FIRST_DB_SERVER_PORT, FIRST_DB_SERVER_COMMAND);
		startClient(SECOND_DB_SERVER_ID, HOST, SECOND_DB_SERVER_PORT, SECOND_DB_SERVER_COMMAND);
		startClient(FIRST_FRONTEND_SERVER_ID, HOST, FIRST_FRONTEND_SERVER_PORT, FIRST_FRONTEND_SERVER_COMMAND);
		startClient(SECOND_FRONTEND_SERVER_ID, HOST, SECOND_FRONTEND_SERVER_PORT, SECOND_FRONTEND_SERVER_COMMAND);

		routingService.start(this);
		executorService.scheduleAtFixedRate(this::addMessageToRoutingService, INITIAL_DELAY_MS, PERIOD_MS, TimeUnit.MILLISECONDS);
	}

	private void startClient(String id, String host, int port, String command) {
		final Client client = new Client(id, host, port, command);
		client.start();
		clientMap.put(id, client);
	}

	private void addMessageToRoutingService() {
		clientMap.forEach((id, client) -> {
			Message message = client.pullMessage();
			if (message != null) {
				routingService.addMessage(message);
			}
		});
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
