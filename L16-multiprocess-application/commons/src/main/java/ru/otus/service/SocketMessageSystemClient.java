package ru.otus.service;

import ru.otus.message.Message;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
class SocketMessageSystemClient implements MessageSystemClient {
	private static final Logger LOGGER = Logger.getLogger(SocketMessageSystemClient.class.getName());
	private static final int CONNECTIONS_NUMBER = 1;

	private final ExecutorService executorService = Executors.newSingleThreadExecutor(new LoggingThreadFactory(SocketMessageSystemClient.class.getName()));
	private CompletableFuture<MessageWorker> messageWorkerCompletableFuture;

	public void start(int port) {
		messageWorkerCompletableFuture = CompletableFuture
				.supplyAsync(this.getSocketSupplier(port), executorService)
				.thenApply(this::startMessageWorker);
	}

	public void stop() {
		executorService.shutdown();
	}

	private Supplier<Socket> getSocketSupplier(int port) {
		return () -> {
			Socket socket = null;
			try (final ServerSocket serverSocket = new ServerSocket(port, CONNECTIONS_NUMBER)) {
				socket = serverSocket.accept();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
			return socket;
		};
	}

	private MessageWorker startMessageWorker(Socket socket) {
		MessageWorker messageWorker = new SocketMessageWorker(socket);
		messageWorker.start();
		return messageWorker;
	}

	public void sendMessage(Message message) throws ExecutionException, InterruptedException {
		messageWorkerCompletableFuture.get().sendMessage(message);
	}

	public Message getMessage() throws ExecutionException, InterruptedException {
		return messageWorkerCompletableFuture.get().pollMessage();
	}
}
