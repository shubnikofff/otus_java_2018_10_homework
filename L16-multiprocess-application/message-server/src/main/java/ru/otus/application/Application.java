package ru.otus.application;

import org.springframework.stereotype.Service;
import ru.otus.domain.service.MessageSystem;
import ru.otus.domain.service.ProcessRunner;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Application {
	private static final String FIRST_FRONTEND_SERVER_ID = "Frontend#1";
	private static final String SECOND_FRONTEND_SERVER_ID = "Frontend#2";
	private static final String FIRST_DB_SERVER_ID = "DB#1";
	private static final String SECOND_DB_SERVER_ID = "DB#2";
	private static final int MESSAGE_SYSTEM_PORT = 5050;

	private static final int THREADS_NUMBER = 4;
	private static final int RUN_COMMAND_DELAY_SECONDS = 2;

	private final ProcessRunner processRunner;
	private final MessageSystem messageSystem;
	private final Logger logger = Logger.getLogger(Application.class.getName());
	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);

	public Application(ProcessRunner processRunner, MessageSystem messageSystem) {
		this.processRunner = processRunner;
		this.messageSystem = messageSystem;
	}

	public void start() throws IOException {
		runCommand("java -jar ../db-server/target/db-server.jar " + FIRST_DB_SERVER_ID);
		runCommand("java -jar ../db-server/target/db-server.jar " + SECOND_DB_SERVER_ID);
		runCommand("java -jar ../frontend/target/frontend.jar " + FIRST_FRONTEND_SERVER_ID);
		runCommand("java -jar ../frontend/target/frontend.jar " + SECOND_FRONTEND_SERVER_ID);

		messageSystem.start(MESSAGE_SYSTEM_PORT);

		executorService.shutdown();
	}

	private void runCommand(String command) {
		executorService.schedule(() -> {
			try {
				processRunner.start(command);
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		}, RUN_COMMAND_DELAY_SECONDS, TimeUnit.SECONDS);
	}
}
