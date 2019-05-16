package ru.otus.app.messages;

import ru.otus.app.messageSystemContext.MessageToFrontend;
import ru.otus.messageSystem.Address;
import ru.otus.service.FrontendService;

public class AuthenticateResponseMessage extends MessageToFrontend {
	private final int id;
	private final boolean authenticated;

	AuthenticateResponseMessage(int id, Address from, Address to, boolean authenticated) {
		super(from, to);
		this.id = id;
		this.authenticated = authenticated;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.putResponseMessage(id, this);
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
}
