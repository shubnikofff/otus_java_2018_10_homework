package ru.otus.service;

import ru.otus.message.Message;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractMessageProcessor implements MessageWorker {
	private static final int INITIAL_DELAY_MS = 0;
	private static final int PERIOD_MS = 100;
	private static final Logger LOGGER = Logger.getLogger(AbstractMessageProcessor.class.getName());

	private BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
	private BlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
	private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new LoggingThreadFactory(this.getClass().getName()));

	public void start() {
		executorService.scheduleAtFixedRate(() -> {
			try {
				Message result = processMessage(inputQueue.take());
				outputQueue.put(result);
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
		}, INITIAL_DELAY_MS, PERIOD_MS, TimeUnit.MILLISECONDS);
	}

	public void putMessage(Message message) throws InterruptedException {
		inputQueue.put(message);
	}

	public Message getMessage() throws InterruptedException {
		return outputQueue.take();
	}

	public void stop() {
		executorService.shutdown();
	}

	protected abstract Message processMessage(Message message);
}
