package ru.otus.application;

import org.springframework.stereotype.Service;

@Service
public class Application {
	private static final String FIRST_FRONTEND_SERVER_ID = "Frontend#1";
	private static final String SECOND_FRONTEND_SERVER_ID = "Frontend#2";
	private static final String FIRST_DB_SERVER_ID = "DB#1";
	private static final String SECOND_DB_SERVER_ID = "DB#2";

	public void start() {
		System.out.println("App started");
	}
}
