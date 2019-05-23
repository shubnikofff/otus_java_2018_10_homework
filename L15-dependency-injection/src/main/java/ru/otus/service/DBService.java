package ru.otus.service;

import ru.otus.model.User;

import java.util.List;

public interface DBService extends Service {
	void saveUser(User user);

	List<User> getAllUsers();
}
