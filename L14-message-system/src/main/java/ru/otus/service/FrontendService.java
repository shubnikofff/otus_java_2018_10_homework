package ru.otus.service;

import ru.otus.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FrontendService extends Service {
	boolean auth(String login, String password);

	void setAuthResult(boolean authResult);

	void createUser(HttpServletRequest request);

	void setUserList(List<User> userList);

	List<User> getUserList();
}
