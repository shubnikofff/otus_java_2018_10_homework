package ru.otus.application.service;

import com.google.gson.Gson;
import ru.otus.domain.service.MessageWorker;
import ru.otus.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMessageWorker implements MessageWorker {
	private static final int THREADS_NUMBER = 2;

	private final BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
	private final BlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
	private final ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);
	private final Logger logger = Logger.getLogger(SocketMessageWorker.class.getName());
	private final Socket socket;
	private final Gson gson = new Gson();

	public SocketMessageWorker(Socket socket) {
		this.socket = socket;
	}

	public void start() {
		executorService.execute(this::readFromSocket);
		executorService.execute(this::writeToSocket);
	}

	public void stop() {
		executorService.shutdown();
	}

	@Override
	public void sendMessage(Message message) {
		outputQueue.add(message);
	}

	@Override
	public Message takeMessage() throws InterruptedException {
		return inputQueue.take();
	}

	@Override
	public Message pollMessage() {
		return inputQueue.poll();
	}

	private void readFromSocket() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			String line;
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				if (line.isEmpty()) {
					Message message = getMessageFromJson(stringBuilder.toString());
					inputQueue.add(message);
					stringBuilder = new StringBuilder();
				}
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			executorService.shutdown();
		}
	}

	private void writeToSocket() {
		try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
			while (socket.isConnected()) {
				final String json = gson.toJson(outputQueue.take());
				writer.println(json);
				writer.println();
			}
		} catch (IOException | InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	private Message getMessageFromJson(String json) {
		return gson.fromJson(json, Message.class);
	}
}
