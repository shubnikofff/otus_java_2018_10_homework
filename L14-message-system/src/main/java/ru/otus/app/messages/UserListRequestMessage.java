package ru.otus.app.messages;

import ru.otus.app.MessageToDB;
import ru.otus.messageSystem.Address;
import ru.otus.model.User;
import ru.otus.service.DBService;

import java.util.List;

public class UserListRequestMessage extends MessageToDB {
	public UserListRequestMessage(Address from, Address to) {
		super(from, to);
	}

	@Override
	public void exec(DBService dbService) {
		final List<User> userList = dbService.getAllUsers();
		final UserListResponseMessage message = new UserListResponseMessage(getTo(), getFrom(), userList);

		dbService.getMessageSystem().sendMessage(message);
	}
}
