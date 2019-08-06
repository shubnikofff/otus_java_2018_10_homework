package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.application.Application;
import ru.otus.application.service.router.DbRouter;
import ru.otus.application.service.router.DefaultRouter;
import ru.otus.application.service.router.Router;
import ru.otus.message.Message;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RoutingService {
	private static final int INITIAL_DELAY_MS = 0;
	private static final int PERIOD_MS = 100;
	private static final Logger LOGGER = Logger.getLogger(RoutingService.class.getName());

	private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

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

		executorService.scheduleAtFixedRate(() -> {
			Message message = messages.poll();
			if (message != null) {
				String addressee = router.getAddressee(message);
				application.sendMessage(addressee, message);
			}
		}, INITIAL_DELAY_MS, PERIOD_MS, TimeUnit.MILLISECONDS);
	}

	public void stop() {
		executorService.shutdown();
	}
}
