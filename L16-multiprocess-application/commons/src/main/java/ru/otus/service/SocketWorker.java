package ru.otus.service;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

public class SocketWorker implements MessageWorker {
	private static final int THREADS_NUMBER = 2;
	private static final Logger LOGGER = Logger.getLogger(SocketWorker.class.getName());

	private final BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
	private final BlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
	private final ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER, new LoggingThreadFactory(SocketWorker.class.getSimpleName()));
	private final Socket socket;
	private final Gson gson = new Gson();

	public SocketWorker(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void start() {
		executorService.execute(this::readFromSocket);
		executorService.execute(this::writeToSocket);
	}

	@Override
	public void stop() {
		executorService.shutdown();
	}

	@Override
	public void putMessage(Message message) throws InterruptedException {
		outputQueue.put(message);
	}

	@Override
	public Message getMessage() throws InterruptedException {
		return inputQueue.take();
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
		} catch (ParseException | ClassNotFoundException | IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} finally {
			executorService.shutdown();
		}
	}

	private void writeToSocket() {
		try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
			while (socket.isConnected()) {
				String json = gson.toJson(outputQueue.take());
				writer.println(json);
				writer.println();
			}
		} catch (IOException | InterruptedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} finally {
			executorService.shutdown();
		}
	}

	private Message getMessageFromJson(String json) throws ParseException, ClassNotFoundException {
		final JSONObject jsonObject = (JSONObject) (new JSONParser().parse(json));
		final String className = (String) jsonObject.get(Message.CLASS_NAME_VARIABLE);
		Class<?> clazz = Class.forName(className);
		return (Message) gson.fromJson(json, clazz);
	}
}
