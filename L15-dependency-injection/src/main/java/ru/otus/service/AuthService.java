package ru.otus.service;

public interface AuthService extends Service {
	boolean authenticate(String login, String password);
}
