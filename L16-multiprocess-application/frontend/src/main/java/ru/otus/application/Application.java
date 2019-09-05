package ru.otus.application;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.message.Message;
import ru.otus.service.MessageWorker;
import ru.otus.service.SocketWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class Application {
	private static final int INITIAL_DELAY_MS = 0;
	private static final int PERIOD_MS = 100;
	private static final int RESPONSE_QUEUE_CAPACITY = 1;
	private static final int SERVER_SOCKET_BACKLOG = 1;
	private static final AtomicInteger MESSAGE_ID_COUNTER = new AtomicInteger();

	private final String id;
	private final Logger logger;
	private final int port;
	private MessageWorker messageWorker;
	private final ExecutorService executorService;
	private final ScheduledExecutorService scheduledExecutorService;
	private final Map<Integer, BlockingQueue<Message>> responseMap;

	public Application(
			@Value("${id}") String id,
			@Qualifier("loggerApplication") Logger logger,
			@Value("${port}") int port
	) {
		this.id = id;
		this.logger = logger;
		this.port = port;
		executorService = Executors.newSingleThreadExecutor();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		responseMap = new ConcurrentHashMap<>();
	}

	public void start() {
		CompletableFuture.supplyAsync(() -> {
			try {
				final Socket socket = new ServerSocket(port, SERVER_SOCKET_BACKLOG).accept();
				logger.info("Accepted connection on port " + socket.getPort());
				return socket;
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new CompletionException(e);
			}
		}, executorService).thenAccept(this::createAndStartMessageWorker).thenRun(this::processInnerMessages);
	}

	public Message sendMessage(Message message) throws InterruptedException {
		final LinkedBlockingQueue<Message> responseQueue = new LinkedBlockingQueue<>(RESPONSE_QUEUE_CAPACITY);
		responseMap.put(message.getId(), responseQueue);
		messageWorker.putMessage(message);
		final Message response = responseQueue.take();
		responseMap.remove(message.getId());
		return response;
	}

	private void processInnerMessages() {
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			try {
				final Message message = messageWorker.getMessage();
				responseMap.get(message.getId()).put(message);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}, INITIAL_DELAY_MS, PERIOD_MS, TimeUnit.MILLISECONDS);
	}

	private void createAndStartMessageWorker(Socket socket) {
		messageWorker = new SocketWorker(socket);
		messageWorker.start();
	}

	public String getId() {
		return id;
	}

	public int generateMessageId() {
		return MESSAGE_ID_COUNTER.incrementAndGet();
	}
}
