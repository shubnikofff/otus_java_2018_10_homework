package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.application.Application;

@SpringBootApplication
public class MessageServer {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MessageServer.class, args);
		context.getBean(Application.class).start();
	}
}
