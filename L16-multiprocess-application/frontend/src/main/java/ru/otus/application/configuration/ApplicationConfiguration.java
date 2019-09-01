package ru.otus.application.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.otus.application.Application;

@Configuration
public class ApplicationConfiguration extends AnnotationConfigWebApplicationContext {
	@Bean(name = "loggerApplication")
	Logger loggerApplication() {
		return LoggerFactory.getLogger(Application.class);
	}
}
