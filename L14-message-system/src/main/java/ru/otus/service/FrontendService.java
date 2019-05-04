package ru.otus.service;

import ru.otus.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FrontendService extends Service {
	void createUser(HttpServletRequest request);

	List<User> getUserList();
}
