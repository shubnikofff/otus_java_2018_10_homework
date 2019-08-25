package ru.otus.application.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.application.Application;
import ru.otus.application.service.ProcessStarter;

@Configuration
public class ApplicationConfiguration {
	@Bean(name = "loggerApplication")
	Logger loggerApplication() {
		return LoggerFactory.getLogger(Application.class);
	}

	@Bean(name = "loggerProcessStarter")
	Logger loggerProcessStarter() {
		return LoggerFactory.getLogger(ProcessStarter.class);
	}
}
