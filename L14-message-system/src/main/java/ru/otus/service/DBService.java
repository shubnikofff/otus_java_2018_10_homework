package ru.otus.service;

import ru.otus.model.User;

import java.util.List;

public interface DBService extends Service {
	long saveUser(User user);

	List<User> getAllUsers();
}
