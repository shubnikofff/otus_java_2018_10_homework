package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.application.Application;

@ComponentScan
public class DbServerMain {
	public static void main(String[] args) {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DbServerMain.class);
		final Application application = context.getBean(Application.class);
		application.start(args[0], Integer.parseInt(args[1]));
	}
}
