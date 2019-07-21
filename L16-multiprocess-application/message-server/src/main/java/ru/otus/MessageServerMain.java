package ru.otus;

import com.sun.tools.javac.Main;
import org.springframework.context.annotation.*;
import ru.otus.application.Application;
import ru.otus.application.service.MessageServer;
import ru.otus.application.service.ProcessRunner;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@ComponentScan
public class MessageServerMain {
	private static final String COMMAND_START_DB_SERVER = "java -jar ../db-server/target/db-server.jar";
	private static final String COMMAND_START_FRONTEND = "java -jar ../frontend/target/frontend.jar";
	private static final int THREADS_NUMBER = 2;
	private static final int RUN_COMMAND_DELAY_SECONDS = 2;

	private static Logger logger = Logger.getLogger(MessageServerMain.class.getName());

	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);

	public static void main(String[] args) {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
		final Application application = context.getBean(Application.class);
		application.start();
	}

	private void start() {
		runCommand(COMMAND_START_DB_SERVER);
		runCommand(COMMAND_START_FRONTEND);

		try {
			new MessageServer().start();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}

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
}
