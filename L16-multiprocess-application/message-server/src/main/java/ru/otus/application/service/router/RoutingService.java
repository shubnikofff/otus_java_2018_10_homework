package ru.otus.application.service.router;

import org.springframework.stereotype.Service;
import ru.otus.domain.service.MessageWorker;
import ru.otus.message.Message;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RoutingService {
	private static final int THREAD_SLEEP_TIME_MS = 100;

	private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final Logger logger = Logger.getLogger(RoutingService.class.getName());

	public void addMessage(Message message) {
		try {
			messages.put(message);
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public void start(Map<String, MessageWorker> messageWorkerMap) {
		Router router = new DefaultRouter(messageWorkerMap);
		router.setNext(new DbMessageRouter(messageWorkerMap));

		executorService.execute(() -> {
			while (!executorService.isTerminated()) {
				final Message message = messages.poll();
				if (message != null) {
					router.sendMessage(message);
				}
				try {
					Thread.sleep(THREAD_SLEEP_TIME_MS);
				} catch (InterruptedException e) {
					logger.log(Level.SEVERE, e.getMessage());
				}
			}
		});
	}

	public void stop() {
		executorService.shutdown();
	}
}