package ru.otus.app.messages;

import ru.otus.app.messageSystemContext.MessageToDB;
import ru.otus.messageSystem.Address;
import ru.otus.model.User;
import ru.otus.service.DBService;

import java.util.List;

public class UserListRequestMessage extends MessageToDB {
	private final int id;

	public UserListRequestMessage( int id, Address from, Address to) {
		super(from, to);
		this.id = id;
	}

	@Override
	public void exec(DBService dbService) {
		final List<User> userList = dbService.getAllUsers();
		final UserListResponseMessage message = new UserListResponseMessage(id, getTo(), getFrom(), userList);

		dbService.getMessageSystem().sendMessage(message);
	}
}
