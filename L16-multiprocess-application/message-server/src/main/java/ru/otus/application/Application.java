package ru.otus.application;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.application.configuration.ApplicationProperties;
import ru.otus.application.service.ProcessStarter;
import ru.otus.application.service.RoutingService;
import ru.otus.message.Message;
import ru.otus.message.ServiceUnavailable;
import ru.otus.service.MessageWorker;
import ru.otus.service.SocketWorker;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class Application {
	private static final int INITIAL_DELAY_MS = 0;
	private static final int PERIOD_MS = 100;
	private static final String HOST = "localhost";
	private static final String ID = "MessageServer";

	private final ApplicationProperties applicationProperties;
	private final Logger logger;
	private final ProcessStarter processStarter;
	private final RoutingService routingService;
	private final Map<String, MessageWorker> messageWorkerMap;
	private final ExecutorService executorService;
	private final ScheduledExecutorService scheduledExecutorService;

	public Application(
			ApplicationProperties applicationProperties,
			@Qualifier("loggerApplication") Logger logger,
			ProcessStarter processStarter,
			RoutingService routingService
	) {
		this.applicationProperties = applicationProperties;
		this.logger = logger;
		this.processStarter = processStarter;
		this.routingService = routingService;
		messageWorkerMap = new ConcurrentHashMap<>(applicationProperties.getClients().size());
		executorService = Executors.newFixedThreadPool(applicationProperties.getClients().size());
		scheduledExecutorService = Executors.newScheduledThreadPool(applicationProperties.getClients().size() + 1);
	}

	public void start() {
		routingService.start();
		applicationProperties.getClients().forEach(this::startClient);
		scheduledExecutorService.scheduleAtFixedRate(this::sendOutgoingMessage, INITIAL_DELAY_MS, PERIOD_MS, TimeUnit.MILLISECONDS);
	}

	private void startClient(ApplicationProperties.Client client) {
		CompletableFuture.supplyAsync(() -> {
			try {
				return processStarter.start(client.getRunCommand());
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new CompletionException(e);
			}
		}, executorService).thenApply((process) -> {
			try {
				if (!process.isAlive()) {
					throw new Exception(client.getId() + " is dead");
				}
				Socket socket = null;
				while (socket == null) {
					try {
						Thread.sleep(applicationProperties.getSleepTimeBeforeConnectToClient());
						socket = new Socket(HOST, client.getPort());
					} catch (ConnectException e) {
						logger.warn(client.getId() + " " + e.getMessage() + " try to reconnect");
					}
				}
				logger.info(client.getId() + " connected on port " + socket.getLocalPort());
				return socket;
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CompletionException(e);
			}
		}).thenAccept((socket -> {
			MessageWorker messageWorker = new SocketWorker(socket);
			messageWorker.start();
			messageWorkerMap.put(client.getId(), messageWorker);
			scheduledExecutorService.scheduleAtFixedRate(() -> {
				try {
					routingService.putMessage(messageWorker.getMessage());
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}, INITIAL_DELAY_MS, PERIOD_MS, TimeUnit.MILLISECONDS);
		}));
	}

	private void sendOutgoingMessage() {
		try {
			final Message message = routingService.getMessage();
			final MessageWorker messageWorker = messageWorkerMap.get(message.getTo());
			if (messageWorker == null) {
				messageWorkerMap.get(message.getFrom()).putMessage(new ServiceUnavailable(message.getId(), ID, message.getFrom()));
			} else {
				messageWorker.putMessage(message);
				logger.info("Send message " + message.getClass().getSimpleName() + " from " + message.getFrom() + " to " + message.getTo());
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	public void stop() {
		scheduledExecutorService.shutdown();
		routingService.stop();
		executorService.shutdown();
	}
}
