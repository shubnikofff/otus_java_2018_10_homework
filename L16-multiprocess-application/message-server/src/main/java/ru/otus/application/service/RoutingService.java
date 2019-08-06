package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.application.Application;
import ru.otus.application.service.router.DbRouter;
import ru.otus.application.service.router.DefaultRouter;
import ru.otus.application.service.router.Router;
import ru.otus.message.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RoutingService {
	private static final int THREAD_SLEEP_TIME_MS = 100;
	private static final Logger LOGGER = Logger.getLogger(RoutingService.class.getName());

	private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	public void addMessage(Message message) {
		try {
			messages.put(message);
		} catch (InterruptedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public void start(Application application) {
		Router router = new DefaultRouter();
		router.setNext(new DbRouter(application.getDbServerIdList()));

		executorService.execute(() -> {
			while (!executorService.isTerminated()) {
				Message message = messages.poll();
				if (message != null) {
					final String addressee = router.getAddressee(message);
					application.sendMessage(addressee, message);
				}
				try {
					Thread.sleep(THREAD_SLEEP_TIME_MS);
				} catch (InterruptedException e) {
					LOGGER.log(Level.SEVERE, e.getMessage());
				}
			}
		});
	}

	public void stop() {
		executorService.shutdown();
	}
}
