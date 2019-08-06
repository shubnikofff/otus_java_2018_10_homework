package ru.otus.application.service;

import ru.otus.application.Application;
import ru.otus.service.MessageWorker;
import ru.otus.service.SocketMessageWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketListener {
	private final Logger logger = Logger.getLogger(SocketListener.class.getName());
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	public void start(Application application) {
		executorService.execute(() -> {
			try (final ServerSocket serverSocket = new ServerSocket(application.getPort())) {
				while (!executorService.isTerminated()) {
					Socket socket = serverSocket.accept();
					logger.log(Level.SEVERE, application.getId() + " accepted socket on " + socket.getPort());
					MessageWorker socketMessageWorker = new SocketMessageWorker(socket);
					application.addMessageWorker(socketMessageWorker);
					socketMessageWorker.start();
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		});
	}

	public void stop() {
		executorService.shutdown();
	}
}
