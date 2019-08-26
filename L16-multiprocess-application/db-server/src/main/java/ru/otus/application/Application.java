package ru.otus.application;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.application.service.DBService;
import ru.otus.message.Message;
import ru.otus.service.MessageWorker;
import ru.otus.service.SocketWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Service
public class Application {
	private static final int INITIAL_DELAY_MS = 0;
	private static final int PERIOD_MS = 100;
	private static final int SERVER_SOCKET_BACKLOG = 1;

	private final DBService dbService;
	private final ExecutorService executorService;
	private final int port;
	private final Logger logger;
	private MessageWorker messageWorker;
	private final ScheduledExecutorService scheduledExecutorService;

	public Application(
			DBService dbService,
			@Value("${port}") int port,
			@Qualifier("loggerApplication") Logger logger
	) {
		this.dbService = dbService;
		this.logger = logger;
		this.port = port;
		executorService = Executors.newSingleThreadExecutor();
		scheduledExecutorService = Executors.newScheduledThreadPool(2);
	}

	public void start() {
		dbService.start();

		CompletableFuture.supplyAsync(() -> {
			try {
				final Socket socket = new ServerSocket(port, SERVER_SOCKET_BACKLOG).accept();
				logger.info("Accepted connection on port " + socket.getPort());
				return socket;
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new CompletionException(e);
			}
		}, executorService)
				.thenAccept(this::createAndStartMessageWorker)
				.thenRun(this::processInnerMessages);

		scheduledExecutorService.scheduleAtFixedRate(this::sendOutgoingMessage, INITIAL_DELAY_MS, PERIOD_MS, TimeUnit.MILLISECONDS);
	}

	private void createAndStartMessageWorker(Socket socket) {
		messageWorker = new SocketWorker(socket);
		messageWorker.start();
	}

	private void processInnerMessages() {
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			try {
				final Message message = messageWorker.getMessage();
				dbService.putMessage(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, INITIAL_DELAY_MS, PERIOD_MS, TimeUnit.MILLISECONDS);
	}

	private void sendOutgoingMessage() {
		try {
			final Message message = dbService.getMessage();
			messageWorker.putMessage(message);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}

	}
}
