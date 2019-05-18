package ru.otus.app.messages;

import ru.otus.app.messageSystemContext.MessageToDB;
import ru.otus.messageSystem.Address;
import ru.otus.model.User;
import ru.otus.service.DBService;

public class SaveUserRequestMessage extends MessageToDB {
	private User user;

	public SaveUserRequestMessage(int id, Address from, Address to, User user) {
		super(id, from, to);
		this.user = user;
	}

	@Override
	public void exec(DBService dbService) {
		dbService.saveUser(user);
	}
}
