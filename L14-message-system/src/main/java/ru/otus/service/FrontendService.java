package ru.otus.service;

import ru.otus.messageSystem.Message;
import ru.otus.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FrontendService extends Service {
	boolean auth(String login, String password);

	void createUser(HttpServletRequest request);

	void putResponseMessage(int id, Message message);

	List<User> getUserList();
}
