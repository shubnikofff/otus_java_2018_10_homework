package ru.otus.application.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServer {
	private static final int PORT = 5050;
	private static final int PROCESS_DELAY = 100;


	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Logger logger = Logger.getLogger(MessageServer.class.getName());

	public void start() throws IOException {
		executor.submit(this::processMessage);

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			logger.info("Message server started on port " + serverSocket.getLocalPort());
			while (!executor.isShutdown()) {
				Socket socket = serverSocket.accept();
				logger.info("Local port: " + socket.getLocalPort() + " port " + socket.getPort());
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
					String msg = reader.readLine();
					System.out.println(msg);
				}
			}
		}
	}

	private void processMessage() {
		while (true) {
			try {
				Thread.sleep(PROCESS_DELAY);
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		}
	}
}
