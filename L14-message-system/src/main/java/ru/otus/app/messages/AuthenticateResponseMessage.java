package ru.otus.app.messages;

import ru.otus.app.messageSystemContext.MessageToFrontend;
import ru.otus.messageSystem.Address;
import ru.otus.service.FrontendService;

public class AuthenticateResponseMessage extends MessageToFrontend {
	private boolean authenticated;

	public AuthenticateResponseMessage(Address from, Address to, boolean authenticated) {
		super(from, to);
		this.authenticated = authenticated;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.setAuthResult(authenticated);
	}
}
