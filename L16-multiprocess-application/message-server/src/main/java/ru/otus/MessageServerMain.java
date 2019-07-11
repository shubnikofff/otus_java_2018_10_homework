package ru.otus;

import ru.otus.application.MessageServer;
import ru.otus.application.service.ProcessRunner;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServerMain {
	private static final String COMMAND_START_DB_SERVER = "java -jar ../db-server/target/db-server.jar";
	private static final String COMMAND_START_FRONTEND = "java -jar ../frontend/target/frontend.jar";
	private static final int THREADS_NUMBER = 4;
	private static final int RUN_COMMAND_DELAY_SECONDS = 2;

	private static Logger logger = Logger.getLogger(MessageServerMain.class.getName());

	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);

	public static void main(String[] args) {
		new MessageServerMain().start();
	}

	private void start() {
		runCommand(COMMAND_START_DB_SERVER);
		runCommand(COMMAND_START_FRONTEND);

		new MessageServer().start();

		executorService.shutdown();
	}

	private void runCommand(String command) {
		executorService.schedule(() -> {
			try {
				new ProcessRunner().start(command);
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		}, RUN_COMMAND_DELAY_SECONDS, TimeUnit.SECONDS);
	}

//	private void startDbServer(ScheduledExecutorService executorService, ProcessRunner processRunner) {
//		executorService.schedule(() -> {
//			try {
//				processRunner.start("java -jar L16-multiprocess-application/frontend/target/frontend-1.0-SNAPSHOT.jar");
//				System.out.println(processRunner.getOutput());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}, 5, TimeUnit.SECONDS);
//	}
}
