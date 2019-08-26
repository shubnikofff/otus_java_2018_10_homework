package ru.otus.application.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.application.Application;
import ru.otus.application.service.DBService;

@Configuration
public class ApplicationConfiguration {
	@Bean(name = "loggerApplication")
	Logger loggerApplication() {
		return LoggerFactory.getLogger(Application.class);
	}

	@Bean(name = "loggerDBService")
	Logger loggerProcessStarter() {
		return LoggerFactory.getLogger(DBService.class);
	}
}
