package ru.otus.app;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.Message;
import ru.otus.service.AuthService;

public abstract class MessageToAuth extends Message {

	public MessageToAuth(Address from, Address to) {
		super(from, to);
	}

	@Override
	public void exec(Addressee addressee) {
		if (addressee instanceof AuthService) {
			exec((AuthService) addressee);
		}
	}

	public abstract void exec(AuthService authService);
}
