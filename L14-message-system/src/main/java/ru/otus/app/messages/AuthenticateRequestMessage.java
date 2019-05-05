package ru.otus.app.messages;

import ru.otus.app.MessageToAuth;
import ru.otus.messageSystem.Address;
import ru.otus.service.AuthService;

public class AuthenticateRequestMessage extends MessageToAuth {
	private String login;
	private String password;

	public AuthenticateRequestMessage(Address from, Address to, String login, String password) {
		super(from, to);
		this.login = login;
		this.password = password;
	}

	@Override
	public void exec(AuthService authService) {
		final boolean result = authService.authenticate(login, password);
		final AuthenticateResponseMessage message = new AuthenticateResponseMessage(getTo(), getFrom(), result);

		authService.getMessageSystem().sendMessage(message);
	}
}
