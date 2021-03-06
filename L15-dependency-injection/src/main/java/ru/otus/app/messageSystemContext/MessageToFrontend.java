package ru.otus.app.messageSystemContext;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.Message;
import ru.otus.service.FrontendService;

public abstract class MessageToFrontend extends Message {
	public MessageToFrontend(int id, Address from, Address to) {
		super(id, from, to);
	}

	@Override
	public void exec(Addressee addressee) {
		if (addressee instanceof FrontendService) {
			exec((FrontendService) addressee);
		}
	}

	public abstract void exec(FrontendService frontendService);
}
