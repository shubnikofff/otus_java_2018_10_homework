package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.application.Application;

@SpringBootApplication
public class FrontendServer {
	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(FrontendServer.class, args);
		context.getBean(Application.class).start();
	}
}
