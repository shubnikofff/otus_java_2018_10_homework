package ru.otus.application.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.application.configuration.ApplicationProperties;

import java.io.File;
import java.io.IOException;

@Service
public class ProcessStarter {
	private final ApplicationProperties applicationProperties;
	private final Logger logger;

	public ProcessStarter(ApplicationProperties applicationProperties, @Qualifier("loggerProcessStarter") Logger logger) {
		this.applicationProperties = applicationProperties;
		this.logger = logger;
	}

	public Process start(String command) throws IOException {
		final ProcessBuilder processBuilder = new ProcessBuilder(command.split(applicationProperties.getCommandLineSeparator()));
		final File file = new File(applicationProperties.getLogDirectory() + File.separator + Thread.currentThread().getName() + ".log");

		processBuilder.redirectErrorStream(true);
		processBuilder.redirectOutput(file);

		final Process process = processBuilder.start();
		logger.info("Command " + command + " started with pid " + process.pid());
		return  process;
	}
}
