package ru.otus.service;

import ru.otus.app.MessageSystemContext;
import ru.otus.app.messages.AuthenticateRequestMessage;
import ru.otus.app.messages.SaveUserRequestMessage;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FrontendServiceImplementation implements FrontendService {
	private Address address;
	private MessageSystemContext messageSystemContext;
	private volatile Boolean auth;

	public FrontendServiceImplementation(Address address, MessageSystemContext messageSystemContext) {
		this.address = address;
		this.messageSystemContext = messageSystemContext;
	}

	@Override
	public boolean auth(String login, String password) {
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
	public List<User> getUserList() {
		return null;
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

		return user;
	}
}
