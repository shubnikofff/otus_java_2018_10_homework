package ru.otus.service;

public class AuthService {
	private static final String ADMIN_LOGIN = "admin";
	private static final String ADMIN_PASSWORD = "admin";

	public boolean auth(String login, String password) {
		return login.equals(ADMIN_LOGIN) && password.equals(ADMIN_PASSWORD);
	}
}
