package ru.otus.app.messages;

import ru.otus.app.messageSystemContext.MessageToAuth;
import ru.otus.messageSystem.Address;
import ru.otus.service.AuthService;

public class AuthenticateRequestMessage extends MessageToAuth {
	private final String login;
	private final String password;
	private final int id;

	public AuthenticateRequestMessage(int id, Address from, Address to, String login, String password) {
		super(from, to);
		this.id = id;
		this.login = login;
		this.password = password;
	}

	@Override
	public void exec(AuthService authService) {
		final boolean result = authService.authenticate(login, password);
		final AuthenticateResponseMessage message = new AuthenticateResponseMessage(id, getTo(), getFrom(), result);

		authService.getMessageSystem().sendMessage(message);
	}
}
