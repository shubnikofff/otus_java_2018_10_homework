package ru.otus.app.service;

import ru.otus.app.MessageSystemContext;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.service.AuthService;

public class AuthServiceImplementation implements AuthService {
	private static final String ADMIN_LOGIN = "admin";
	private static final String ADMIN_PASSWORD = "admin";

	private Address address;
	private MessageSystemContext messageSystemContext;

	public AuthServiceImplementation(Address address, MessageSystemContext messageSystemContext) {
		this.address = address;
		this.messageSystemContext = messageSystemContext;
	}

	@Override
	public boolean authenticate(String login, String password) {
		return login.equals(ADMIN_LOGIN) && password.equals(ADMIN_PASSWORD);
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
}
