package ru.otus.app.messageSystemContext;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.Message;
import ru.otus.service.AuthService;

public abstract class MessageToAuth extends Message {
	public MessageToAuth(int id, Address from, Address to) {
		super(id, from, to);
	}

	@Override
	public void exec(Addressee addressee) {
		if (addressee instanceof AuthService) {
			exec((AuthService) addressee);
		}
	}

	public abstract void exec(AuthService authService);
}
