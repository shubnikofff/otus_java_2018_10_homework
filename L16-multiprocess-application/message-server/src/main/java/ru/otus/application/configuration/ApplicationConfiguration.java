package ru.otus.application.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.application.service.ProcessStarter;

@Configuration
public class ApplicationConfiguration {
	@Bean(name = "loggerProcessStarter")
	Logger logger() {
		return LoggerFactory.getLogger(ProcessStarter.class);
	}
}
