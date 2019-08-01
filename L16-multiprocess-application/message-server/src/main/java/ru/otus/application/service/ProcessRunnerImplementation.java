package ru.otus.application.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.service.ProcessRunner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProcessRunnerImplementation implements ProcessRunner {
	private static final String COMMAND_LINE_SEPARATOR = " ";
	private static final String LOG_DIR = "../logs/";

	private Process process;
	private final StringBuffer output = new StringBuffer();
	private final Logger logger = Logger.getLogger(ProcessRunnerImplementation.class.getName());

	@Override
	public Process start(String command) throws IOException {
		process = runProcess(command);
		logger.info("Command: \"" + command + "\" started with PID " + process.pid());
		return process;
	}

	@Override
	public void stop() {
		process.destroy();
	}

	@Override
	public String getOutput() {
		return output.toString();
	}

	private Process runProcess(String command) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder(command.split(COMMAND_LINE_SEPARATOR));
		processBuilder.redirectErrorStream(true);

		final File file = new File(LOG_DIR + Thread.currentThread().getName() + ".log");
		processBuilder.redirectOutput(file);

		Process process = processBuilder.start();

		StreamListener streamListener = new StreamListener(process.getInputStream(), "OUTPUT");
		streamListener.start();

		return process;
	}

	private class StreamListener extends Thread {
		private final Logger logger = Logger.getLogger(StreamListener.class.getName());
		private final InputStream inputStream;
		private final String type;

		private StreamListener(InputStream inputStream, String type) {
			this.inputStream = inputStream;
			this.type = type;
		}

		@Override
		public void run() {
			try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					output.append(type).append(" > ").append(line).append('\n');
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		}
	}
}
