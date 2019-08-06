package ru.otus.application.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.logging.Logger;

@Service
public class ProcessRunner {
	private static final String COMMAND_LINE_SEPARATOR = " ";
	private static final String LOG_DIR = "../logs/";
	private static final Logger LOGGER = Logger.getLogger(ProcessRunner.class.getName());

	private Process process;

	public Process start(String command) throws IOException {
		process = runProcess(command);
		LOGGER.info("Command: \"" + command + "\" started with PID " + process.pid());
		return process;
	}

	public void stop() {
		process.destroy();
	}

	private Process runProcess(String command) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder(command.split(COMMAND_LINE_SEPARATOR));
		processBuilder.redirectErrorStream(true);

		final File file = new File(LOG_DIR + Thread.currentThread().getName() + ".log");
		processBuilder.redirectOutput(file);

		return processBuilder.start();
	}
}
