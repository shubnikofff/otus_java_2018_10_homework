package ru.otus.app.service;

import ru.otus.app.messageSystemContext.MessageSystemContext;
import ru.otus.app.messages.AuthenticateRequestMessage;
import ru.otus.app.messages.SaveUserRequestMessage;
import ru.otus.app.messages.UserListRequestMessage;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.model.Phone;
import ru.otus.model.User;
import ru.otus.service.FrontendService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FrontendServiceImplementation implements FrontendService {
	private Address address;
	private MessageSystemContext messageSystemContext;
	private volatile Boolean auth;
	private volatile List<User> userList;

	public FrontendServiceImplementation(Address address, MessageSystemContext messageSystemContext) {
		this.address = address;
		this.messageSystemContext = messageSystemContext;
	}

	@Override
	public boolean auth(String login, String password) {
		auth = null;
		final AuthenticateRequestMessage message = new AuthenticateRequestMessage(address, messageSystemContext.getAuthAddress(), login, password);
		messageSystemContext.getMessageSystem().sendMessage(message);

		while (auth == null) {
			Thread.onSpinWait();
		}

		return auth;
	}

	@Override
	public void setAuthResult(boolean authResult) {
		auth = authResult;
	}

	@Override
	public void createUser(HttpServletRequest request) {
		final SaveUserRequestMessage message = new SaveUserRequestMessage(address, messageSystemContext.getDbAddress(), getUserFromRequest(request));
		messageSystemContext.getMessageSystem().sendMessage(message);
	}

	@Override
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public List<User> getUserList() {
		userList = null;
		final UserListRequestMessage message = new UserListRequestMessage(address, messageSystemContext.getDbAddress());
		messageSystemContext.getMessageSystem().sendMessage(message);

		while (userList == null) {
			Thread.onSpinWait();
		}

		return userList;
	}

	@Override
	public void registerInMessageSystem() {
		messageSystemContext.getMessageSystem().addAddressee(this);
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public MessageSystem getMessageSystem() {
		return messageSystemContext.getMessageSystem();
	}

	private User getUserFromRequest(HttpServletRequest request) {
		final User user = new User();

		String name = request.getParameter("name");
		if (name != null) {
			user.setName(name);
		}

		try {
			user.setAge(Integer.parseInt(request.getParameter("age")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		user.setAddress(new ru.otus.model.Address(request.getParameter("address")));

		final String[] phones = request.getParameterValues("phone");
		for (String phone : phones) {
			if (!phone.equals("")) {
				user.addPhone(new Phone(phone));
			}
		}

		return user;
	}
}
