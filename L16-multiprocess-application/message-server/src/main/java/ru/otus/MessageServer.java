package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.application.Application;

@ComponentScan
public class MessageServer {
	public static void main(String[] args) {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MessageServer.class);
		final Application application = context.getBean(Application.class);
		application.start();
	}
}
