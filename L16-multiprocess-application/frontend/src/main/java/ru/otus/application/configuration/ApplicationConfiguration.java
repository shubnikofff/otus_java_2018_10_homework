package ru.otus.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.otus.application.service.FrontendService;

@Configuration
public class ApplicationConfiguration extends AnnotationConfigWebApplicationContext {
//	@Value("${id}")
//	private String id;
//
	@Bean
	FrontendService frontendService() {
		return new FrontendService();
	}
}
