package ru.otus;

import ru.otus.application.Application;

public class DbServerMain {
	public static void main(String[] args) {
		final Application application = new Application(args[0], Integer.parseInt(args[1]));
		application.start();
	}
}
