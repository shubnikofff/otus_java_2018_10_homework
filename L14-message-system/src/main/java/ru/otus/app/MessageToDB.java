package ru.otus.app;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.Message;
import ru.otus.service.DBService;

public abstract class MessageToDB extends Message {
	public MessageToDB(Address from, Address to) {
		super(from, to);
	}

	@Override
	public void exec(Addressee addressee) {
		if (addressee instanceof DBService) {
			exec((DBService) addressee);
		}
	}

	public abstract void exec(DBService dbService);
}
