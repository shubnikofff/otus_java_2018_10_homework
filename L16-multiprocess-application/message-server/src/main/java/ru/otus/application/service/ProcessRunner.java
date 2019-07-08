package ru.otus.application.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessRunner implements ru.otus.domain.service.ProcessRunner {
	private static final String COMMAND_LINE_SEPARATOR = " ";

	private Process process;
	private final StringBuffer output = new StringBuffer();

	@Override
	public void start(String command) throws IOException {
		process = runProcess(command);
		System.out.println("Start process with PID: " + process.pid());
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
