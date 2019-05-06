package ru.otus.app.messages;

import ru.otus.app.messageSystemContext.MessageToFrontend;
import ru.otus.messageSystem.Address;
import ru.otus.model.User;
import ru.otus.service.FrontendService;

import java.util.List;

public class UserListResponseMessage extends MessageToFrontend {
	private List<User> userList;

	public UserListResponseMessage(Address from, Address to, List<User> userList) {
		super(from, to);
		this.userList = userList;
	}

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.setUserList(userList);
	}
}
