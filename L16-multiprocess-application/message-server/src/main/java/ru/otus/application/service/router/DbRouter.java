package ru.otus.application.service.router;

import ru.otus.message.Message;
import ru.otus.message.SaveUserRequest;
import ru.otus.message.UserListRequest;

import java.util.List;

class DbRouter extends Router {
	private final List<String> dbServerIdList;
	private int lastIndex = 0;

	DbRouter(List<String> dbServerIdList) {
		this.dbServerIdList = dbServerIdList;
	}

	@Override
	String getAddressee(Message message) {
		if (canGetAddressee(message)) {
			final int index = lastIndex + 1 < dbServerIdList.size() ? lastIndex + 1 : 0;
			lastIndex = index;
			return dbServerIdList.get(index);
		}
		return super.getAddressee(message);
	}

	private boolean canGetAddressee(Message message) {
		return message instanceof SaveUserRequest || message instanceof UserListRequest;
	}
}
