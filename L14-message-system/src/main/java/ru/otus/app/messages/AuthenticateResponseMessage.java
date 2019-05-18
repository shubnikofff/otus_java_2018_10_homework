package ru.otus.app.messages;

import ru.otus.app.messageSystemContext.MessageToFrontend;
import ru.otus.messageSystem.Address;
import ru.otus.service.FrontendService;

public class AuthenticateResponseMessage extends MessageToFrontend {
	private final boolean authenticated;

	AuthenticateResponseMessage(int id, Address from, Address to, boolean authenticated) {
		super(id, from, to);
		this.authenticated = authenticated;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.putResponseMessage(getId(), this);
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
}
