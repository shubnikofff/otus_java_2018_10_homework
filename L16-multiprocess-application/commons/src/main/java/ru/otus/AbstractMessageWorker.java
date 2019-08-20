package ru.otus;

import ru.otus.message.Message;
import ru.otus.service.LoggingThreadFactory;
import ru.otus.service.MessageWorker;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractMessageWorker implements MessageWorker {
	private static final int INITIAL_DELAY_MS = 0;
	private static final int PERIOD_MS = 100;
	private static final Logger LOGGER = Logger.getLogger(AbstractMessageWorker.class.getName());

	private BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
	private BlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
	private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new LoggingThreadFactory(this.getClass().getName()));

	public void start() {
		this.init();
		executorService.scheduleAtFixedRate(() -> {
			Message message = null;
			try {
				message = inputQueue.take();
				processMessage(message);
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
			processMessage(message);
		}, INITIAL_DELAY_MS, PERIOD_MS, TimeUnit.MILLISECONDS);
	}

	public void putMessage(Message message) throws InterruptedException {
		inputQueue.put(message);
	}

	public Message getMessage() throws InterruptedException {
		return outputQueue.take();
	}

	public abstract void init();
	public abstract void processMessage(Message message);
}
